package com.fjm.properties.aliyun;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-06-08 下午9:38
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliyunSmsProperties {

    public String signName;

    public String loginTemplateCode;

    public String glogalSignName;

    public String globalLoginTemplateCode;
}
