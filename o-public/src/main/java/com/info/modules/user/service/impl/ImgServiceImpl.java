package com.info.modules.user.service.impl;

import com.info.img.DictionaryTemplate;
import com.info.img.StorageUtils;
import com.info.modules.user.service.ImgService;
import com.info.redis.RedisKeyUtils;
import com.info.redis.RedisUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ImgServiceImpl
 * @Description TODO 图片上传
 * @Date 2019/3/21 19:47
 * @Created by yml
 */
@Service("imgService")
public class ImgServiceImpl implements ImgService {


    private static final Logger logger = LoggerFactory.getLogger(ImgServiceImpl.class);

    @Autowired
    private StorageUtils storageUtils;

    @Autowired
    private RedisUtils redisUtils;


//    @Value("${logoImg}")
//    private String logoImg;

    
    @Override
    public String getToken() {
        try {
            String s = redisUtils.get(RedisKeyUtils.Img.IMG_TOKEN);
            if (StringUtils.isNotBlank(s)) {
                return s;
            }
        } catch (Exception e) {
            logger.error("从redis中获取快云存储token异常:", e);
        }
        String token = storageUtils.getToken();
        try {
            redisUtils.set(RedisKeyUtils.Img.IMG_TOKEN, token, 24 * 60 * 60);
        } catch (Exception e) {
            logger.error("从redis中存入快云存储token异常:", e);
        }
        return token;
    }

    @Override
    public Map<String, Object> uploadFile(Map<String, Object> stringObjectMap) {
        Map<String, Object> map = new HashMap<>();
        String fileParentPath = DictionaryTemplate.getFileParentPath();
        String[] smallImgUrlsplit = stringObjectMap.get("smallImgUrl").toString().replace("\\", "/").split(fileParentPath);
        String smallImgUrlfileName = smallImgUrlsplit[1];
        String token = getToken();
        String res = storageUtils.uploadFile(token, smallImgUrlfileName);
        logger.info("图片上传云服务器小图返回结果:", res);
        if ("-3".equalsIgnoreCase(JSONObject.fromObject(res).get("code").toString())) {
            try {
                redisUtils.delete(RedisKeyUtils.Img.IMG_TOKEN);
            } catch (Exception e) {
                logger.error("从redis中删除快云存储token异常:", e);
            }
            token = getToken();
            res = storageUtils.uploadFile(token, smallImgUrlfileName);
        }
        String smallImgUrl = storageUtils.getFileUrl(token, smallImgUrlfileName);
        map.put("smallImgUrl", JSONObject.fromObject(smallImgUrl).get("message").toString());
        String[] bigImgUrlSplit = stringObjectMap.get("bigImgUrl").toString().replace("\\", "/").split(fileParentPath);
        String bigImgUrlFileName = bigImgUrlSplit[1];
        String s = storageUtils.uploadFile(token, bigImgUrlFileName);
        logger.info("图片上传云服务器大图返回结果:", s);
        String bigImgUrl = storageUtils.getFileUrl(token, bigImgUrlFileName);
        map.put("bigImgUrl", JSONObject.fromObject(bigImgUrl).get("message").toString());
        logger.info("上传的小图路径:" + fileParentPath + smallImgUrlfileName);
        logger.info("上传的大图路径:" + fileParentPath + bigImgUrlFileName);
        return map;
    }

    @Override
    public void deleteFile(String img, String smallImg) {
        String token = getToken();
        if (img != null) {
            String[] imgsplit = img.split("/");
            String imgFileName = imgsplit[imgsplit.length - 1];
            String resImg = storageUtils.delFile(token, imgFileName);
            logger.info("向服务器删除文件,图片路径img{},返回信息为:{}", imgFileName, resImg);
        }
        if (smallImg != null) {
            String[] split = smallImg.split("/");
            String FileName = split[split.length - 1];
            String res = storageUtils.delFile(token, FileName);
            logger.info("向服务器删除文件,图片路径img{},返回信息为:{}", FileName, res);
        }
    }

//    @Override
//    public String saveQRCode(String inviteCode) throws Exception {
//        String content = "https://www.goldkeng.com/static/iosShare.html" + "?" + "regCode=" + inviteCode;
//        String token = getToken();
//        String imgUrl = QRCodeUtil.qrEncode(content, logoImg);
//        String fileParentPath = DictionaryTemplate.getFileParentPath();
//        String[] smallImgUrlsplit = imgUrl.replace("\\", "/").split(fileParentPath);
//        String imgUrlfileName = smallImgUrlsplit[0];
//        String res = storageUtils.uploadFile(token, imgUrlfileName);
//        logger.info("二维码图片上传云服务器返回结果:", res);
//        if ("-3".equalsIgnoreCase(JSONObject.fromObject(res).get("code").toString())) {
//            try {
//                redisUtils.delete(RedisKeyUtils.Img.IMG_TOKEN);
//            } catch (Exception e) {
//                logger.error("从redis中删除快云存储token异常:", e);
//            }
//            token = getToken();
//                                                                          res = storageUtils.uploadFile(token, imgUrlfileName);
//        }
//        String smallImgUrl = storageUtils.getFileUrl(token, imgUrlfileName);
//        String qRCodeImg = JSONObject.fromObject(smallImgUrl).get("message").toString();
////        activeProducer.sendQueue(MQQueue.DEL_LOCAL_FILE, fileParentPath + imgUrlfileName);
//        return qRCodeImg;
//    }

    @Override
    public Map<String, Object> upload(Map<String, Object> map) {
        Map<String, Object> map1 = new HashMap<>();
        String fileParentPath = DictionaryTemplate.getFileParentPath();
        String[] smallImgUrlsplit = map.get("file").toString().replace("\\", "/").split(fileParentPath);
        String smallImgUrlfileName = smallImgUrlsplit[1];
        String token = getToken();
        String res = storageUtils.uploadFile(token, smallImgUrlfileName);
        String smallImgUrl = storageUtils.getFileUrl(token, smallImgUrlfileName);
        map1.put("file", JSONObject.fromObject(smallImgUrl).get("message").toString());
//        String[] bigImgUrlSplit = stringObjectMap.get("bigImgUrl").toString().replace("\\", "/").split(fileParentPath);
//        String bigImgUrlFileName = bigImgUrlSplit[1];
//        String s = storageUtils.uploadFile(token, bigImgUrlFileName);
//        logger.info("图片上传云服务器大图返回结果:", s);
//        String bigImgUrl = storageUtils.getFileUrl(token, bigImgUrlFileName);
//        map.put("bigImgUrl", JSONObject.fromObject(bigImgUrl).get("message").toString());
        System.out.println(res);
        return map1;
    }
}
