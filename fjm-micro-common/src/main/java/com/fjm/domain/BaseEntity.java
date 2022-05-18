package com.fjm.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-03-16 上午9:49
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    public static final String ID = "id";
    @Id
    @Fixed
    @ApiModelProperty(value = "主键")
    protected String id;
}
