package com.iqilu.config.local;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 客户端token对应信息
 *
 * @author zhangyc
 * @date 2022/04/03
 */
@Data
public class MemberTokenMsg {

    private Integer id;

    private String openid;

    private Integer orgid;

    private String nickname;

    private String phone;

    private String avatar;

    private Integer totalScore;

    private Integer total;
}
