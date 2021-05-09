package com.itmuch.contentcenter.service.content;

import com.itmuch.contentcenter.dao.content.ShareMapper;
import com.itmuch.contentcenter.domain.dto.content.ShareDto;
import com.itmuch.contentcenter.domain.dto.user.UserDto;
import com.itmuch.contentcenter.domain.entity.content.Share;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private UserCenterFeignClient userCenterFeignClient;

    public ShareDto findById(Integer id){
        // 获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        // 发布人ID
        Integer userId = share.getUserId();

        // 用HTTP get方法去请求，并且返回一个对象
        UserDto userDto = this.userCenterFeignClient.findById(userId);

        ShareDto shareDto = new ShareDto();
        BeanUtils.copyProperties(share, shareDto);
        shareDto.setWxNickname(userDto.getWxNickname());
        return shareDto;
    }

}
