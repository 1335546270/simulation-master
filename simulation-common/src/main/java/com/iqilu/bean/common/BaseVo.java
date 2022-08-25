package com.iqilu.bean.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类 - 子类实现类上加注解: @EqualsAndHashCode(callSuper = true)
 *
 * @author zhangyicheng
 * @date 2021/02/08
 */
@Data
public class BaseVo implements Serializable {

    /**
     * 机构id
     */
    @TableField("org_id")
    private Integer orgId;

    @TableField(exist = false)
    private Integer tokenUserId;

    /**
     * 更新时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
