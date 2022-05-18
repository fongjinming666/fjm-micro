package com.fjm.properties.aliyun;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-06-08 下午9:39
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliyunPushProperties {

    public String androidAppKey;

    public String androidMagicAppKey;

    public String iosAppKey;

    public String env;

    public String region;
}
