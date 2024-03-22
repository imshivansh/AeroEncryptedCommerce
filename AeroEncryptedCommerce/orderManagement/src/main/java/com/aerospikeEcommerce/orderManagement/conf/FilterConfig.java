package com.aerospikeEcommerce.orderManagement.conf;
import org.apache.catalina.filters.RequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
        public FilterRegistrationBean<RequestFilterConfig> decryptFilter() {
            FilterRegistrationBean<RequestFilterConfig> registrationBean = new FilterRegistrationBean<>();
            registrationBean.setFilter(new RequestFilterConfig());
            registrationBean.addUrlPatterns("/order/**"); // Replace with the URL pattern you want to intercept
            return registrationBean;
        }
    }


