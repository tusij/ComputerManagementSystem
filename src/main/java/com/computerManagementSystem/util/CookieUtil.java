package com.computerManagementSystem.util;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

/**
 * @author 赖志宇
 * @date 2020/6/16 23:42
 */
public class CookieUtil {
    private final static int MaxAge = 60*60*24*7;
    /**
     * 根据name获取cookie
     * @param httpServletRequest
     * @param name
     * @return
     */
    public static Cookie getCookie(HttpServletRequest httpServletRequest, String name) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies==null)
            return null;
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }

    /**
     * 根据名字获取值
     * @param httpServletRequest
     * @param name
     * @return
     */
    public static String getCookieValue(HttpServletRequest httpServletRequest,String name){
        Cookie cookie = getCookie(httpServletRequest,name);
        if(cookie==null)
            return null;
        return cookie.getValue();
    }

    public static String getCookieValueNamedToken(HttpServletRequest request){
        return getCookieValue(request,"auth_token");
    }

    public static String getCookieValueNamedRoleToken(HttpServletRequest request){
        return getCookieValue(request,"role_token");
    }
    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param path
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response,String name,String value,String path,int maxAge){
       if(StringUtils.isBlank(name))
           return;
       else if(StringUtils.isBlank(path))
           return;
       else if(maxAge ==0)
           return;
       Cookie cookie = new Cookie(name,value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 将用户登录凭证放入cookie
     * @param response
     * @param value
     */
    public static void setUserCookie(HttpServletResponse response,String value){
        setCookie(response,"author_token",value,"repair",MaxAge);
    }

    /**
     * 将成员登录凭证放入cookie
     * @param response
     * @param value
     */
    public static void setMemberCookie(HttpServletResponse response,String value){
        setCookie(response,"author_token",value,"member",MaxAge);
    }

    /**
     * 将成员权限凭证放入cookie
     * @param response
     * @param value
     */
    public static void setRoleCookie(HttpServletResponse response,String value){
        setCookie(response,"role_token",value,"member",60*60);
    }

    /**
     * 根据名字和路径移除cookie
     * @param response
     * @param name
     * @param path
     */
    public static void removeCookie(HttpServletResponse response,String name,String path){
        if(name ==null)
            return;
        Cookie cookie = new Cookie(name,"null");
        if(cookie!=null){
            cookie.setPath(path);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
    }

    public static  void removeMemberCookie(HttpServletResponse response,String name){
        removeCookie(response,name,"member");
    }

    public static  void removeUserCookie(HttpServletResponse response,String name){
        removeCookie(response,name,"user");
    }
}
