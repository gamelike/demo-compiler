package com.topsec.judge.service

import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

interface ResourceService {
    /**
     * 上传文件到静态资源目录
     *
     * @param file 上传文件
     */
    fun uploadFileToResource(file: MultipartFile): String

    /**
     * 从静态资源目录下载文件
     *
     * @param fileName 文件名称
     * @return [InputStream]
     */
    fun downloadFileToResource(fileName: String): InputStream
}