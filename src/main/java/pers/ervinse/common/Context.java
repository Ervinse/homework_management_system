package pers.ervinse.common;

/**
 * 保存项目全局信息的上下文类
 */
public class Context {

    //为每个请求(线程)保存当前登录的id
    private static final ThreadLocal<Long> loginThreadLocal = new ThreadLocal<>();

    /**
     * 将当前登录的id保存到ThreadLocal中
     * @param loginId 当前登录的id
     */
    public static void setLoginId(Long loginId) {
        loginThreadLocal.set(loginId);
    }

    /**
     * 从ThreadLocal中获取当前登录的id
     * @return 当前登录的id
     */
    public static Long getLoginId() {
        return loginThreadLocal.get();
    }
}
