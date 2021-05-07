package com.itmuch.contentcenter;

import com.itmuch.contentcenter.dao.content.ShareMapper;
import com.itmuch.contentcenter.domain.dto.user.UserDto;
import com.itmuch.contentcenter.domain.entity.content.Share;
import com.itmuch.contentcenter.feignclient.TestBaiduFeignClient;
import com.itmuch.contentcenter.feignclient.TestUserCenterFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private TestUserCenterFeignClient testUserCenterFeignClient;

    @Autowired
    private TestBaiduFeignClient testBaiduFeignClient;

    @GetMapping("/test")
    public List<Share> testInsert(){
        Share share = new Share();
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setTitle("xxx");
        share.setCover("xxx");
        share.setAuthor("ye21st");
        share.setBuyCount(1);
        this.shareMapper.insertSelective(share);
        return this.shareMapper.selectAll();
    }

    /**
     * 测试： 服务发现，证明内容中心总能找到用户中心
     * @return 用户中心所有的实例信息
     */
    @GetMapping("/test2")
    public List<ServiceInstance> getInstances(){
        // 查询指定服务的所有实例的信息
        return this.discoveryClient.getInstances("user-center");
    }

    @GetMapping("/test-get")
    public UserDto query(UserDto userDto){
        return testUserCenterFeignClient.query(userDto);
    }

    @GetMapping("baidu")
    public String baiduIndex(){
        return testBaiduFeignClient.index();
    }

    @Autowired
    private TestService testService;

    @GetMapping("/test-a")
    public String testA(){
        this.testService.common();
        return "test-a";
    }

    @GetMapping("/test-b")
    public String testB(){
        this.testService.common();
        return "test-b";
    }

}
