package com.iqilu.enums.token;

/**
 * token信息
 *
 * @author zhangyicheng
 * @date 2020/08/28
 */
public enum TokenEnum {

    /**
     * token信息
     */
    ID("id"),
    OPEN_ID("openid"),
    USER_ID("userid"),
    ADMIN_ID("adminid"),
    AVATAR("avatar"),
    NICK_NAME("nickname"),
    PRIV("priv"),
    DEPARTMENT_ID("departmentid"),
    ORG_ID("orgid"),
    ORG_NAME("orgname"),
    PLATFORM("platform"),
    TIME("time"),
    EXP("exp");


    private String type;

    TokenEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
