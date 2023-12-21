package org.example.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.example.constant.FileType;
import org.example.exception.ArgsException;
import org.example.exception.JudgeException;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.URLEncoder;
import org.apache.commons.io.IOUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * description: 文件工具类
 *
 * @author zhao_yifan
 * @since 2022/4/27
 */
@Slf4j
public class FileUtils {

    public static final String SPILT = "/";

    @Getter
    public static class FileMessage {

        final String fileName; // 文件名称
        @NonNull
        final String alarmIndexingId; // 告警id
        final String filePath; // 文件路径
        @NonNull
        final FileType fileType; // 文件类型

        @JsonCreator
        public FileMessage(
                @JsonProperty("fileName") String fileName,
                @JsonProperty("alarmIndexingId") @NonNull String alarmIndexingId,
                @JsonProperty("filePath") String filePath,
                @JsonProperty("fileType") @NonNull FileType fileType) {
            this.fileName = fileName;
            this.alarmIndexingId = alarmIndexingId;
            this.filePath = filePath;
            this.fileType = fileType;
        }

        public String getFilePath() {
            return fileType.getType() + FileUtils.SPILT + alarmIndexingId;
        }
    }

    public static String getProjectPath() throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        if (System.getProperty("os.name").contains("Windows")) {
            path = path.substring(3);
        }
        return path;
    }

    public static Path getPath(String fileName, String filePath) throws IOException {
        String projectPath = getProjectPath() + filePath;
        Path path = Paths.get(projectPath, fileName);
        if (validFilePath(path)) throw new JudgeException("文件路径非法:" + path);
        return path;
    }

    @SneakyThrows
    public static void downloadAppendTimeStamp(HttpServletResponse response, FileMessage fileMessage) {
        downloadAppendTimeStamp(response, fileMessage.getFileName(), fileMessage.getFilePath());
    }

    public static void downloadFileByAlarmIndexingId(HttpServletResponse response, FileMessage fileMessage) {
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.DEFAULT.encode(fileMessage.getFileName(),
                        StandardCharsets.UTF_8));
        try {
            String projectPath = getProjectPath();
            Path path = Paths.get(projectPath, fileMessage.getFilePath());
            copyToStream(response, path);
        } catch (IOException e) {
            log.error("拷贝文件到response错误", e);
        }
    }

    @SneakyThrows
    public static void downloadFile(HttpServletResponse response, String fileName, String filePath) {
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.DEFAULT.encode(fileName,
                        StandardCharsets.UTF_8));
        try {
            Path path = getPath(fileName, filePath);
            copyToStream(response, path);
        } catch (IOException e) {
            log.error("拷贝文件流到response失败");
            throw new JudgeException(e.getMessage());
        }
    }


    @SneakyThrows
    public static void downloadAppendTimeStamp(HttpServletResponse response, String fileName, String filePath) {
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.DEFAULT.encode(fileNameRemoveTime(fileName),
                        StandardCharsets.UTF_8));
        try {
            Path path = getPath(fileName, filePath);
            copyToStream(response, path);
        } catch (IOException e) {
            log.error("拷贝文件流到response失败");
        }
    }


    @Nullable
    public static File upload(MultipartFile multipartFile, FileMessage fileMessage) {
        return upload(multipartFile, fileMessage.getFilePath());
    }

    @Nullable
    public static File upload(MultipartFile multipartFile,
                              String filePath) {
        try {
            String realPath = getProjectPath() + filePath;
            File file = new File(realPath);
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.mkdirs();
            }
            Path dest = Paths.get(realPath, fileNameAppendTime(Objects.requireNonNull(multipartFile.getOriginalFilename())));
            if (validFilePath(dest)) {
                throw new JudgeException("文件路径非法");
            }
            transferToDest(multipartFile, dest);
            return dest.toFile(); // maybe isn't exists
        } catch (IOException e) {
            // FIXME: here the method should fail
            log.warn("failed to upload file {}", multipartFile.getOriginalFilename(), e);
            return null;
        }
    }

    public static boolean delete(String filePath, String fileName) {
        try {
            File file = getPath(fileName, filePath).toFile();
            if (validFilePath(file.toPath())) throw new JudgeException("文件路径非法");
            return file.delete();
        } catch (Exception e) {
            log.error("删除文件错误", e);
            return false;
        }
    }

    private static void copyToStream(HttpServletResponse response, Path path) {
        try (InputStream fis = Files.newInputStream(path)) {
            if (validFilePath(path)) {
                throw new JudgeException("文件路径非法");
            }
            IOUtils.copy(fis, response.getOutputStream());
        } catch (FileNotFoundException e) {
            log.error("不存在该文件", e);
        } catch (IOException e) {
            log.error("拷贝流失败", e);
        }
    }

    private static void transferToDest(MultipartFile file, Path dest) {
        try {
            if (validFilePath(dest)) {
                throw new JudgeException("文件路径非法");
            }
            file.transferTo(dest);
        } catch (FileNotFoundException e) {
            log.error("不存在该文件", e);
        } catch (IOException e) {
            log.error("文件拷贝失败", e);
        }
    }

    public static boolean validFilePath(Path path) throws FileNotFoundException {
        for (FileType value : FileType.values()) {
            if (path.startsWith(getProjectPath() + File.separator + value.getType())) {
                return false;
            }
        }
        return true;
    }

    public static String fileNameAppendTime(@NonNull String originFileName) {
        return originFileName + "." + UserUtils.getUserName() + "." + new Date().getTime();
    }

    public static String fileNameGetUser(@NonNull String originFIleName) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (int length = originFIleName.length() - 1; length > 0; length--) {
            if (originFIleName.charAt(length) == '.') {
                if (index > 0) {
                    builder.append(originFIleName, length + 1, index);
                    break;
                }
                index = length;
            }
        }
        return builder.toString();
    }

    public static String fileNameRemoveTime(@NonNull String originFileName) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (int length = originFileName.length() - 1; length > 0; length--) {
            if (originFileName.charAt(length) == '.') {
                if (index > 0) {
                    builder.append(originFileName, 0, length);
                    break;
                }
                index = length;
            }
        }
        return builder.toString();
    }

    /**
     * 仅支持POSIX文件系统
     *
     * @param file       文件
     * @param permission 权限 使用 rwxr--r-- 这种模式
     * @throws IOException 操作执行失败抛出异常
     */
    public static void chmod(File file, String permission) throws IOException {
        Set<PosixFilePermission> posixFilePermissions = PosixFilePermissions.fromString(permission);
        Files.setPosixFilePermissions(file.toPath(), posixFilePermissions);
    }

    /**
     * 校验文件类型后缀
     */
    public static void validFileSuffix(String originName) throws ArgsException {
        List<String> fileSuffix = Lists.newArrayList("log", "txt", "pdf", "xlxs", "xls", "pcap", "json");
        for (String suffix : fileSuffix) {
            if (originName.endsWith(suffix)) {
                return;
            }
        }
        throw new ArgsException("文件类型校验失败!, 不支持" + originName.substring(originName.lastIndexOf(".")) + "类型");
    }

}
