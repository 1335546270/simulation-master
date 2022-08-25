package com.iqilu.bean.vo.user;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 第三方 - 获取平台用户信息
 *
 * @author zhangyicheng
 * @date 2020/07/28
 */
@Data
public class HttpResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 用户数据集合
     */
    private List<UserInfoVo> data;

    /**
     * 错误信息
     */
    private String errmsg;

}
