package com.itmuch.contentcenter;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.nacos.common.utils.StringUtils;
import com.itmuch.contentcenter.dao.content.ShareMapper;
import com.itmuch.contentcenter.domain.dto.user.UserDto;
import com.itmuch.contentcenter.domain.entity.content.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private DiscoveryClient discoveryClient;

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

    @GetMapping("/test-hot")
    @SentinelResource("hot")
    public String testHot(@RequestParam(required = false) String a, @RequestParam(required = false) String b){
        return a + " " + b;
    }

    @GetMapping("/test-add-flow-rule")
    @SentinelResource("hot")
    public String testAddFlowRule(@RequestParam(required = false) String a, @RequestParam(required = false) String b){
        this.initFlowQpsRule();
        return "success";
    }

    private void initFlowQpsRule(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule("/shares/1");
        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @GetMapping("/test-sentinel-api")
    public String testSentinelAPI(@RequestParam(required = false) String a){
        String resourceName = "test-sentinel-api";
        ContextUtil.enter(resourceName, "test-wfw");


        Entry entry = null;
        try {
            // 定义一个sentinel保护的资源，名称是 test-sentinel-api

            entry = SphU.entry(resourceName);
            // 被保护的业务逻辑
            if (StringUtils.isBlank(a)){
                throw new IllegalArgumentException("a不能为空");
            }
            return a;
            // 如果被保护的资源被限流或者降级了，就会抛出 BlockException
        } catch (BlockException e) {
            log.warn("限流或者降级了", e);
            return "限流或者降级了";
        } catch (IllegalArgumentException e2) {
            // 统计 IllegalArgumentException [发生的次数、发生占比。。。]
            Tracer.trace(e2);
            return "参数非法";
        } finally {
            if (entry != null){
                entry.exit();
            }
            ContextUtil.exit();
        }
    }

    @GetMapping("/test-sentinel-resource")
    @SentinelResource(
            value = "test-sentinel-api",
            blockHandler = "block",
            fallback = "fallback"
    )
    public String testSentinelResource(@RequestParam(required = false) String a){
        // 被保护的业务逻辑
        if (StringUtils.isBlank(a)){
            throw new IllegalArgumentException("a不能为空");
        }
        return a;
    }

    public String block(String a, BlockException e) {
        log.warn("限流或者降级了", e);
        return "限流或者降级了";
    }

    public String fallback(String a) {
        return "限流或者降级了 fallback";
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test-rest-template-sentinel/{userId}")
    public UserDto test(@PathVariable Integer userId){
        return this.restTemplate.getForObject(
                "http://user-center/users/{userId}", UserDto.class, userId
        );
    }

}
