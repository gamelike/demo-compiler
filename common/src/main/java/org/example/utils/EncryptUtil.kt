package com.topsec.judge.utils

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptUtil {
    private const val KEY = "MDEyMzQ1Njc4OTEyMzQ1Ng=="
    private const val IV = "MDEyMzQ1Njc4OTEyMzQ1Ng=="
    private const val AES = "AES"
    private const val AES_TYPE = "AES/GCM/PKCS5Padding"
    private const val AUTH = "VGFsZW50QDEyMw=="

    /**
     * 加密方法 : 用于测试生成配置文件密码
     */
    fun encrypt(keyword: String): String {
        val instance = Cipher.getInstance(AES_TYPE)
        instance.init(
            Cipher.ENCRYPT_MODE,
            SecretKeySpec(Base64.getDecoder().decode(KEY.toByteArray()), AES),
            GCMParameterSpec(128, Base64.getDecoder().decode(IV.toByteArray()))
        )
        instance.updateAAD(AUTH.toByteArray())
        val encrypt = instance.doFinal(keyword.toByteArray())
        return Base64.getEncoder().encodeToString(encrypt)
    }

    /**
     * 解密方法: 用于beanPostProcessor处理
     */
    fun decrypt(keyword: String): String {
        val instance = Cipher.getInstance(AES_TYPE)
        instance.init(
            Cipher.DECRYPT_MODE,
            SecretKeySpec(Base64.getDecoder().decode(KEY), AES),
            GCMParameterSpec(128, Base64.getDecoder().decode(IV))
        )
        instance.updateAAD(AUTH.toByteArray())
        val decode = Base64.getDecoder().decode(keyword)
        return String(instance.doFinal(decode), Charsets.UTF_8)
    }
}