package com.itmuch.contentcenter.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: UserCenterFeignConfiguration
 * @Description: Feign的配置类 这个类别加 @Configuration注解，否则有父子上下文问题
 * @Author ye21st ye21st@gmail.com
 * @Date 2021/5/6 6:05 下午:44
 */
public class GlobalFeignConfiguration {

	@Bean
	public Logger.Level level(){
		// 让 Feign打印所有日志
		return Logger.Level.FULL;
	}

}
