package com.suning.jc.config;


import com.suning.jc.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 13120094
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new AuthFilter());
        bean.addUrlPatterns("/user/*","/student/*","/class/*","/clastu/*","/updatePass");
        bean.addInitParameter("excludedUrls","/test/*");
        bean.setName("authFilter");
        bean.setOrder(1);
        return bean;
    }
}