package com.iqilu.utils;

import com.iqilu.bean.vo.user.UserInfoVo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户处理工具类
 *
 * @author zhangyicheng
 * @date 2020/11/05
 */
public class UserUtils<T> {

    private static UserUtils instance;

    public static synchronized UserUtils getInstance(){
        if (instance == null) {
            instance = new UserUtils();
        }
        return instance;
    }

    /**
     * 去除拼合UserInfo字符串中最后的逗号
     *
     * @author zhangyicheng
     * @date 2020/09/30
     */
    private static String userInfoList(StringBuilder stringBuilder) {
        String idList = "";
        if (stringBuilder.toString().length() > 0) {
            idList = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
        } else {
            idList = "";
        }
        return idList;
    }

    public String concatUserInfo(List<T> t, String clazzMethod) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (T user : t) {
                Class<?> clazz = user.getClass();
                Method[] methods = clazz.getMethods();
                Map<String, Method> map = new HashMap<>(16);
                for (Method method : methods) {
                    map.put(method.getName(), method);
                }
                // 获取
                String userInfo = (String) map.get(clazzMethod).invoke(user);
                if (userInfo != null) {
                    stringBuilder.append(userInfo);
                    stringBuilder.append(",");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoList(stringBuilder);
    }


    /**
     * 测试
     *
     * @author zhangyicheng
     * @date 2021/02/19
     */
    public static void main(String[] args) {
        List<UserInfoVo> list = new ArrayList<>();
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId("1");
        userInfoVo.setNickname("张宜成");
        userInfoVo.setPhone("13181699498");
        list.add(userInfoVo);

        UserUtils<UserInfoVo> userUtils = UserUtils.getInstance();
        String result = userUtils.concatUserInfo(list, "getPhone");
        System.out.println(result);

    }

}
