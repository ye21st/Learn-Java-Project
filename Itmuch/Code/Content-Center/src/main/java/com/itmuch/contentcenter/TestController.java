package com.itmuch.contentcenter;

import com.itmuch.contentcenter.dao.content.ShareMapper;
import com.itmuch.contentcenter.domain.entity.content.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ShareMapper shareMapper;

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

}
