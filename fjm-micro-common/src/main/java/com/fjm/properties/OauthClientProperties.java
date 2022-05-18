package com.fjm.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-12-09 11:34
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OauthClientProperties {

    private String clientId;

    private String clientSecret;

    private String scope;

    private String redirectUri;
}
