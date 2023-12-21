package org.example.service.impl

import com.topsec.judge.service.ResourceService
import lombok.extern.slf4j.Slf4j
import org.apache.commons.io.IOUtils
import org.example.exception.ArgsException
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.Instant

@Service
@Slf4j
class ResourceServiceImpl : ResourceService {
    private val rootPath = ResourceUtils.getURL("classpath:").path + File.separator + "resources" + File.separator
    override fun uploadFileToResource(file: MultipartFile): String {
        val fileName = (file.originalFilename?.plus(".") ?: "unknown.") + Instant.now().epochSecond
        val filePath = rootPath + fileName
        FileOutputStream(File(filePath)).use {
            IOUtils.copy(file.inputStream, it)
        }
        return fileName
    }

    override fun downloadFileToResource(fileName: String): FileInputStream {
        val file = File(rootPath + fileName)
        if (!file.exists() || file.isDirectory) {
            throw ArgsException("文件路径指定错误,不存在该文件")
        }
        return FileInputStream(file)
    }
}