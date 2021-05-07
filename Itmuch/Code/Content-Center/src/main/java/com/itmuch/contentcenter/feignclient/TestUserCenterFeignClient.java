package com.itmuch.contentcenter.feignclient;

import com.itmuch.contentcenter.domain.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: TestUserCenterFeignClient
 * @Description:
 * @Author ye21st ye21st@gmail.com
 * @Date 2021/5/7 11:00 上午:06
 */
@FeignClient(name = "user-center")
public interface TestUserCenterFeignClient {

	@GetMapping("/q")
	public UserDto query(@SpringQueryMap UserDto userDto);

}
