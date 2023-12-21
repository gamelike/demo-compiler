package com.topsec.judge.utils

import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory


/**
 * @author violet
 * @since 2023/6/8
 */
object SSLUtils {
    fun trustContext(caPath: String, trustPath: String, passwd: String): SSLSocketFactory? {
        // java 信任仓库 ca
        val trustStore = KeyStore.getInstance("JKS")
        trustStore.load(FileInputStream(caPath), EncryptUtil.decrypt(passwd).toCharArray())
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(trustStore)
        // key
        val keyStore = KeyStore.getInstance("JKS")
        keyStore.load(FileInputStream(trustPath), EncryptUtil.decrypt(passwd).toCharArray())
        val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        kmf.init(keyStore, EncryptUtil.decrypt(passwd).toCharArray())
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(kmf.keyManagers, tmf.trustManagers, SecureRandom())
        return sslContext.socketFactory
    }

    fun trustCISContext(pemPath: String): SSLSocketFactory? {
        val cf = CertificateFactory.getInstance("X.509")
        val certificate = cf.generateCertificate(File(pemPath).inputStream())
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null)
        keyStore.setCertificateEntry("crt", certificate)
        tmf.init(keyStore)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, tmf.trustManagers, null)
        return sslContext.socketFactory
    }

}