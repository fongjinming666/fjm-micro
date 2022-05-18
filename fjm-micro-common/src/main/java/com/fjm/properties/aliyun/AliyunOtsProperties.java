package com.fjm.properties.aliyun;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-06-08 下午9:46
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliyunOtsProperties {

    private String endPoint;

    private String instanceName;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AliOtsTableProperties {

        private String tableName;

        private String tableIndex;
    }
}
