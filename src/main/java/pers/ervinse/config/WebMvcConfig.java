package pers.ervinse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pers.ervinse.common.JacksonObjectMapper;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    /**
     * 添加静态资源映射
     *
     * @param registry 资源处理程序
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 扩展mvc框架的消息转换器
     *
     * @param converters mvc框架的转换器集合
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置自定义对象转换器(用于将java对象转换为json字符串,方式long型数值精度损失)
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将装有自定义对象转换器的消息转换器对象追加到mvc框架的转换器集合中,并设置序列号为0,即优先使用此消息转换器
        converters.add(0, messageConverter);
    }

    /**
     * 添加视图控制器,用于将请求转化为跳转页面
     * 相当于在controller中添加@RequestMapping注解的方法,返回需要跳转的页面,和视图解析器配合使用
     * @param registry 视图控制器
     */
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/data").setViewName("simulationData");

        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    /**
     * 创建内部资源视图解析器
     *
     * InternalResourceViewResolver会把返回的视图名称都解析为InternalResourceView对象，
     * InternalResourceView会把Controller处理器方法返回的模型属性都存放到对应的request属性中，
     * 然后通过RequestDispatcher在服务器端把请求forward重定向到目标UR
     *
     * @return 内部资源视图解析器
     */
    @Bean
    public InternalResourceViewResolver resourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        //请求视图文件的前缀地址(此处不需要是因为addResourceHandlers()方法中已经将"/"映射为"/static"了)
//        internalResourceViewResolver.setPrefix("/static");
        //请求视图文件的后缀
        internalResourceViewResolver.setSuffix(".html");
        return internalResourceViewResolver;
    }

}
