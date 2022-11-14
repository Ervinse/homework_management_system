package pers.ervinse.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import pers.ervinse.Dto.LoginUser;
import pers.ervinse.common.R;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户登录状态
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器，用于检测含有通配符的路径是否匹配
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //向下转型,便于获取数据
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取本次请求的URI
        String requestURI = request.getRequestURI();

        //定义不需要处理的请求路径
        String[] permitUrls = new String[]{
                //登录模块
                "/login/**",
                //登录时获取班级列表
                "/clase/list",
                //发送验证码
                "/user/sendMsg",
                //静态资源
                "/page/**",
                "/framework/**",
                "/js/**",
                "/api/**",
                "/images/**",
                "/imageItem/**",
                "/index.html",
                "/",
                //测试数据
                "/data",
                "/common/fullSimulatedData",
                "/common/basicSimulatedData"
        };

        //检查本次请求地址是否需要处理
        boolean permit = checkUrl(permitUrls, requestURI);

        //请求不需要处理
        if (permit){
            log.info("请求:{}不需要处理",requestURI);
            filterChain.doFilter(request, response);
        } else {        //请求需要处理

            //从session中获取当前登录的用户
            LoginUser loginUser = (LoginUser) request.getSession().getAttribute("user");

            if (loginUser != null){      //用户已登录
                log.info("用户已登录,请求:{}放行",requestURI);
                //放行
                filterChain.doFilter(request,response);
            } else {    //未登录
                log.info("用户未登录,请求:{}拦截",requestURI);
                //使用输出流向前端响应信息
                response.getWriter().write(JSON.toJSONString(R.getErrorInstance("NOTLOGIN")));
            }


        }

    }

    /**
     * 检查请求路径是否在放行地址中
     * @param permitUrls 放行地址
     * @param requestURI 需要检查的地址
     * @return 如果在放行地址中返回true,否则返回false
     */
    public boolean checkUrl(String[] permitUrls, String requestURI) {
        for (String url : permitUrls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }

}
