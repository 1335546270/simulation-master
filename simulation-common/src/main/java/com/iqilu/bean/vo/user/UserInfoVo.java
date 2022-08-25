package com.iqilu.bean.vo.user;

import lombok.Data;
import java.io.Serializable;

/**
 * 第三方 - 用户信息
 *
 * @author zhangyicheng
 * @date 2020/07/28
 */
@Data
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方平台用户电话
     */
    private String phone;

    /**
     * 第三方平台用户名称
     */
    private String nickname;

    /**
     * 第三方平台用户头像
     */
    private String avatar;

    /**
     * 第三方平台用户ID
     */
    private String id;

}
