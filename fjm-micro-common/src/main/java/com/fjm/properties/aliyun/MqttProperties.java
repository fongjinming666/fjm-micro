package com.fjm.properties.aliyun;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-06-08 下午9:21
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MqttProperties {

    private String instanceId;

    private String clientId;

    private String endPoint;

}
