package com.itmuch.contentcenter;

import com.itmuch.contentcenter.configuration.GlobalFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.itmuch")
@SpringBootApplication
@EnableFeignClients(defaultConfiguration = GlobalFeignConfiguration.class)
public class ContentCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }

    /**
     * @LoadBalanced 为 RestTemplate 整合 Ribbon
     * @return
     */
    @Bean
    @LoadBalanced
//    @SentinelRestTemplate
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
