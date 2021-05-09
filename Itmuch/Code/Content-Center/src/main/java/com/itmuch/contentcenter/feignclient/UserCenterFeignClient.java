package com.itmuch.contentcenter.feignclient;

import com.itmuch.contentcenter.configuration.GlobalFeignConfiguration;
import com.itmuch.contentcenter.domain.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName: UserCenterFeignClient
 * @Description:
 * @Author ye21st ye21st@gmail.com
 * @Date 2021/5/6 4:45 下午:54
 */
@FeignClient(name = "user-center", configuration = GlobalFeignConfiguration.class)
//@FeignClient(name = "user-center")
public interface UserCenterFeignClient {

	@GetMapping("/users/{id}")
	UserDto findById(@PathVariable Integer id);

}
