package com.itmuch.contentcenter;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName: TestService
 * @Description:
 * @Author ye21st ye21st@gmail.com
 * @Date 2021/5/7 5:41 下午:46
 */
@Service
@Slf4j
public class TestService {

	@SentinelResource("common")
	public String common(){
		log.info("common...");
		return "common";
	}

}
