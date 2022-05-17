package com.fjm.bo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author: jinmingfong
 * @CreateTime: 2021-03-16 下午4:50
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery {

    @ApiModelProperty(value = "当前页")
    protected Integer currentPage = 1;

    @ApiModelProperty(value = "每页数量")
    protected Integer pageSize = 20;
}
