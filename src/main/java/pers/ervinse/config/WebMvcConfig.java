package pers.ervinse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pers.ervinse.common.JacksonObjectMapper;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    /**
     * 添加静态资源映射
     * @param registry 资源处理程序
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 扩展mvc框架的消息转换器
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
}
