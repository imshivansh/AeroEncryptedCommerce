package com.aerospikeEcommerce.inventoryManagement.conf;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean<RequestFilterConfig> decryptFilter() {
        FilterRegistrationBean<RequestFilterConfig> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestFilterConfig());
        registrationBean.addUrlPatterns("/inventory/products/**"); // Replace with the URL pattern you want to intercept
        return registrationBean;
    }
}
