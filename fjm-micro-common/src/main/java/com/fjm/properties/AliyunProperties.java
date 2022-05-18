package com.fjm.properties;

import com.fjm.properties.aliyun.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-03-29 下午5:55
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@RefreshScope
@ConfigurationProperties(prefix = "spring.data.ali")
public class AliyunProperties {

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * sms
     */
    private AliyunSmsProperties aliyunSmsProperties = new AliyunSmsProperties();

    /**
     * push
     */
    private AliyunPushProperties aliyunPushProperties = new AliyunPushProperties();

    /**
     * oss
     */
    private AliyunOssProperties aliyunOssProperties = new AliyunOssProperties();

    /**
     * ots
     */
    private AliyunOtsProperties aliyunOtsProperties = new AliyunOtsProperties();

    /**
     * iot mqtt
     */
    private AliyunIotMqttProperties aliyunIotMqttProperties = new AliyunIotMqttProperties();

    /**
     * 设备上下线
     */
    private RocketMqProperties deviceOnlineReport = new RocketMqProperties();

    /**
     * 设备属性点
     */
    private RocketMqProperties devicePropertyReport = new RocketMqProperties();

    /**
     * 设备事件信息
     */
    private RocketMqProperties deviceEventInfoReport = new RocketMqProperties();

    /**
     * 设备故障报警
     */
    private RocketMqProperties deviceErrorAlarmReport = new RocketMqProperties();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AliyunIotMqttProperties extends MqttProperties {

        private String userActiveDeviceLogTopic;
    }
}
