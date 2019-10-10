package com.info.modules.user.service;

import java.util.Map;

/**
 * @Classname ImgService
 * @Description TODO 图片上传
 * @Date 2019/3/21 19:45
 * @Created by yml
 */
public interface ImgService {
    /**
     * @desciption: TODO 获取token
     * @author yml
     * @date 2019/3/21 19:48
     */
    String getToken();

    /**
     * @desription: TODO 向云服务器上传图片
     * @author yml
     * @date 2019/3/21 20:19
     */
    Map<String, Object> uploadFile(Map<String, Object> stringObjectMap);

    /**
     * @param img      大图
     * @param smallImg 小图
     * @desription: TODO 向云服务器删除图片
     * @author yml
     * @date 2019/3/21 20:19
     */
    void deleteFile(String img, String smallImg);

//    String saveQRCode(String inviteCode) throws Exception;


    /**
     * @param
     * @return
     * @description: 上传附件
     * @author liudan
     * @date 2019/6/14 14:39
     */
    Map<String, Object> upload(Map<String, Object> map);
}
