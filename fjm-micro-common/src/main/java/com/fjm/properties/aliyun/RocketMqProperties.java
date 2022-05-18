package com.fjm.properties.aliyun;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-06-08 下午9:40
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RocketMqProperties {

    private String topic;

    private String group;

    private String nameSrvAddr;

    private boolean active;

    private String topicType;

    private String tag;

    private String consumeThreadNums;
}
