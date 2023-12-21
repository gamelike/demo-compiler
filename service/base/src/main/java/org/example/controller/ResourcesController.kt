package org.example.controller

import com.topsec.judge.service.ResourceService
import lombok.extern.slf4j.Slf4j
import org.apache.commons.io.IOUtils
import org.example.exception.ArgsException
import org.example.exception.JudgeException
import org.example.exception.model.ResponseResult
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/resources")
@Slf4j
open class ResourcesController(private val resourceService: ResourceService) {

    @PostMapping("upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    open fun resourceUpload(@RequestPart multipartFiles: List<MultipartFile>): ResponseResult {
        try {
            if (multipartFiles.size > 5) throw ArgsException("超过文件上传个数,最大为5个, 当前为: " + multipartFiles.size)
            if (multipartFiles.any { it.size > 1024 * 1024 * 10 }) {
                throw ArgsException("超过文件最大限制, 最大为10M")
            }
            val resMap = HashMap<String, String>()
            multipartFiles.forEach {
                val realName = resourceService.uploadFileToResource(it)
                resMap[it.originalFilename as String] = realName
            }
            return ResponseResult.success(resMap)
        } catch (e: ArgsException) {
            throw e
        } catch (e: Exception) {
            throw JudgeException(e.message)
        }
    }

    @GetMapping("download/{fileName}")
    open fun resourceDownload(
        @PathVariable fileName: String, httpServletResponse: HttpServletResponse
    ) {
        try {
            val rename = fileName.substringBeforeLast(".")
            resourceService.downloadFileToResource(fileName).use {
                httpServletResponse.setHeader("Content-Disposition", "attachment;filename=$rename")
                IOUtils.copy(it, httpServletResponse.outputStream)
            }
        } catch (e: Exception) {
            throw JudgeException(e.message)
        }
    }
}