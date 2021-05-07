package com.itmuch.contentcenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: TestBaiduFeignClient
 * @Description:
 * @Author ye21st ye21st@gmail.com
 * @Date 2021/5/7 11:21 上午:32
 */
@FeignClient(name = "baidu", url = "http://www.baidu.com")
public interface TestBaiduFeignClient {

	@GetMapping("")
	String index();

}
