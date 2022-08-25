package com.iqilu.config.local;

import lombok.Data;

/**
 * 后台token信息对应
 *
 * @author zhangyc
 * @date 2022/04/03
 */
@Data
public class UserTokenMsg {

    private Integer id;

    private Integer orgid;

    private Integer roleid;

    private String nickname;

    private String avatar;
}
