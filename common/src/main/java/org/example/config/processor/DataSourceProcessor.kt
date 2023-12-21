package com.topsec.judge.config.processor

import com.topsec.judge.utils.EncryptUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.stereotype.Component


/**
 * @author violet
 * @since 2023/6/7
 */
@Component
class DataSourceProcessor : BeanPostProcessor {
    private val log = LoggerFactory.getLogger(DataSourceProcessor::class.java)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        if (bean is DataSourceProperties) {
            bean.username = EncryptUtil.decrypt(bean.username)
            bean.password = EncryptUtil.decrypt(bean.password)
            log.debug("dataSource解密后的票据为-${bean.username}:${bean.password}")
        }
        if (bean is RedisProperties) {
            if (bean.password != null) {
                bean.password = EncryptUtil.decrypt(bean.password)
                log.debug("redis解密后的票据为-${bean.password}")
            }
        }
        return bean
    }

}