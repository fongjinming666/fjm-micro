package com.fjm.ineterface;

import com.fasterxml.jackson.annotation.JsonView;
import com.fjm.dto.PageResult;
import com.fjm.emun.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collections;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommonPage<List> extends CommonResult<List> {
    public interface pageLevel extends CommonResult.common {
    }

    ;

    @JsonView(pageLevel.class)
    private Integer pageNum;

    @JsonView(pageLevel.class)
    private Integer pageSize;

    @JsonView(pageLevel.class)
    private Integer totalPage;

    @JsonView(pageLevel.class)
    private Long total;

    public CommonPage(Integer result, List data) {
        super(result, data);
    }

    /**
     * 构造器
     *
     * @param result
     * @param pageNum
     * @param pageSize
     * @param data
     */
    public CommonPage(Integer result, Long pageNum, Long pageSize, Long totalNum, List data) {
        super(result, data);
        this.pageNum = pageNum == null ? 0 : pageNum.intValue();
        this.pageSize = pageSize == null ? 0 : pageSize.intValue();
        this.total = totalNum == null ? 0 : totalNum.longValue();
        if (this.pageSize > 0) {
            this.totalPage = (int) ((this.total / this.pageSize) + (this.total % this.pageSize > 0 ? 1 : 0));
        }
    }

    /**
     *
     */
    public static <List> CommonPage<List> successEmptyPage() {
        return new CommonPage((int) ResultCode.SUCCESS.getCode(), 1L, (long) 10L, 0L, Collections.emptyList());
    }

    /**
     * @param list
     * @param <List>
     * @return
     */
    public static <List> CommonPage<List> successPage(java.util.List list) {
        return new CommonPage((int) ResultCode.SUCCESS.getCode(), 1L, (long) list.size(), (long) list.size(), list);
    }

    /**
     * ota recipe success page
     * 将SpringData分页后的list转为分页信息
     */
    public static <List> CommonPage<List> successPage(PageResult result) {
        return new CommonPage((int) ResultCode.SUCCESS.getCode(), (long) result.getPageNum(), (long) result.getPageSize(), result.getTotal(), result.getList());
    }

    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <List> CommonPage<List> successPage(Page<List> pageInfo) {
        return new CommonPage((int) ResultCode.SUCCESS.getCode(), (long) pageInfo.getNumber(), (long) pageInfo.getSize(), pageInfo.getTotalElements(), pageInfo.getContent());
    }
}
