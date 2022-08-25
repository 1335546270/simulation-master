package com.iqilu.bean.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 基类 - 子类实现类上加注解: @EqualsAndHashCode(callSuper = true)
 *
 * @author zhangyicheng
 * @date 2021/02/08
 */
@Getter
@Setter
public class BaseDto implements Serializable {

    @TableField("org_id")
    private Integer orgId;

    @TableField(exist = false)
    private Integer tokenUserId;

    @TableField(exist = false)
    private Integer pageNum = 1;

    @TableField(exist = false)
    private Integer pageSize = 10;

    public BaseDto() {
    }

    public BaseDto(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}
