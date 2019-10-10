package com.info.img;

import com.info.string.UUIDUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.Constant;
import com.info.utils.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述: 文件上传工具类
 *
 * @auther: Gaosx By User
 * @param:
 * @date: 2019/3/13 19:27
 */
@RestController
@RequestMapping("api")
public class FileUploadUtil {

    //文件最大100M
    private static long upload_maxsize = 1 * 1024 * 1024;
    //文件允许格式
    private static String[] allowFiles = {"gif", "jpg", "jpeg", "png", "bmp"};

    private final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    @Value("${uploadPath}")
    private String uploadPath;

    /**
     * 功能描述: 图片文件压缩的工具类
     *
     * @param:
     * @return:
     * @auther: 高山溪 By User
     * @date: 2018/10/27 19:09
     */
    @Autowired
    private ThumbnailatorUtils thumbnailatorUtils;

    /**
     * 图片起始压缩值
     */
    @Value("${imgSize}")
    private Integer imgSize;


    /**
     * 功能描述: 单个图片上传方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/24 14:03
     * @Return:
     */
    @RequestMapping("/upload")
    public ResultMessage upload(MultipartFile file) {
        // 判断文件不为空
        if (file.isEmpty()) {
            return ResultMessage.error(ConfigConstant.ERROR, "文件为空");
        }
        // 保存文件的
        File saveFile = new File(uploadPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        Map<String, Object> map = null;
        try {
            map = thumbnailatorUtils.uploadFileAndCreateThumbnail(file);
        } catch (Exception e) {
            logger.info("单个图片上传方法异常:" + e);
        }
        String img = (String) map.get("img");
        String smallImg = (String) map.get("smallImg");
        img = img.substring(img.indexOf("upload") + 7);
        smallImg = smallImg.substring(smallImg.indexOf("upload") + 7);
//        img = "/" + img;
//        smallImg = "/" + smallImg;
//        return ResultMessage.ok(1, "文件上传成功", "").put("img", img).put("smallImg", smallImg);
        return ResultMessage.ok(map);
    }

    /**
     * 功能描述: 多图片上传且生成压缩图的大小图
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/24 14:07
     * @Return:
     */
    @RequestMapping("/uploadImgList")
    public List<FileImgEntity> uploadImgList(MultipartFile[] imgFile) {
        // 判断文件不为空
        if (imgFile == null) {
            return null;
        }
        // 保存文件的
        File saveFile = new File(uploadPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        Map<String, Object> map = null;
        List<FileImgEntity> fileImgList = new ArrayList<FileImgEntity>(ConfigConstant.INIT_LIST_SIZE);
        // 遍历目录取的文件信息
        for (MultipartFile file : imgFile) {
            try {
                map = thumbnailatorUtils.uploadFileAndCreateThumbnail(file);
                FileImgEntity entity=new FileImgEntity();
                entity.setImg(map.get("img").toString());
                entity.setSmallImg(map.get("smallImg").toString());
                fileImgList.add(entity);
            } catch (Exception e) {
                logger.info("多图片上传且生成压缩图的大小图异常:" + e);
            }
        }
        return fileImgList;
    }

    /**
     * 功能描述:
     *
     * @param:多图片上传且生成压缩图的大小图---------------------废弃方法
     * @return:
     * @auther: 高山溪 By User
     * @date: 2018/11/8 19:45
     */
    @RequestMapping("/saveMany")
    public Map<String, Object> saveMany(MultipartFile[] imgFile) {
        // 保存文件的
        File saveFile = new File(uploadPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        // 遍历目录取的文件信息
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        for (MultipartFile file : imgFile) {
            Map<String, Object> hash = new HashMap<String, Object>();
            String fileName = file.getOriginalFilename();
            String name = fileName.substring(0, fileName.lastIndexOf("."));
//            System.out.println("文件名称：" + name);
            // 新的文件名
            String newFileName = this.getName(fileName);
            // 文件扩展名
            String fileEnd = this.getFileExt(fileName);
            // 绝对路径
            String fileNamedirs = uploadPath + File.separator + newFileName + fileEnd;
//            System.out.println("保存的路径：" + fileNamedirs);
            File filedirs = new File(fileNamedirs);
            // 转入文件
            try {
                file.transferTo(filedirs);
                String url = "/" + newFileName + fileEnd;
                hash.put("is_dir", false);
                hash.put("has_file", false);
                hash.put("filesize", file.getSize());
                hash.put("is_photo", Arrays.asList(allowFiles).contains(fileEnd));
                hash.put("filetype", fileEnd);
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                fileList.add(hash);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("moveup_dir_path", "");
        result.put("current_dir_path", uploadPath);
        result.put("current_url", "http://127.0.0.1:8080/");
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);
        return result;
    }

    /**
     * 功能描述: 单个图片上传的方法带压缩生成大小图
     *
     * @param:
     * @return:
     * @auther: 高山溪 By User
     * @date: 2018/10/27 19:05
     */
    @RequestMapping("/save")
    public ResultMessage save(MultipartFile imgFile) throws Exception {
//        String fileName = imgFile.getOriginalFilename().toString();
        // 判断文件不为空
        if (imgFile.getSize() == 0 || imgFile.isEmpty()) {
//            System.out.println(
// "文件为空");
            return ResultMessage.error("文件为空");
        }

        Map<String, Object> map = thumbnailatorUtils.uploadFileAndCreateThumbnail(imgFile);
        String bigImgUrl = (String) map.get("bigImgUrl");
        String smallImgUrl = (String) map.get("smallImgUrl");
        bigImgUrl = bigImgUrl.substring(bigImgUrl.indexOf("upload") + 7);
        smallImgUrl = smallImgUrl.substring(smallImgUrl.indexOf("upload") + 7);
       /* // 文件类型判断
        if (!this.checkFileType(fileName)) {
            System.out.println("文件类型不允许");
            return ResultMessage.error("文件类型不允许");
        }
       *//* *//*
        // 保存文件的
        File saveFile = new File(uploadPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        System.out.println("文件名称：" + name);
        // 新的文件名
        String newFileName = this.getName(fileName);
        // 文件扩展名
        String fileEnd = this.getFileExt(fileName);
        // 绝对路径
        String fileNamedirs = uploadPath + File.separator + newFileName + fileEnd;
        System.out.println("保存的路径：" + fileNamedirs);
        File filedirs = new File(fileNamedirs);*/
        // 转入文件
        try {
           /* imgFile.transferTo(filedirs);
            if (imgFile.getSize()> imgSize) {
                logger.info("文件大于" + (imgSize / 1024) + "k, 进行压缩操作");
                // 递归压缩，直到目标文件大小小于desFileSize
                long end2 = System.currentTimeMillis();
                thumbnailatorUtils.commpressPicCycle(filedirs, imgSize, 0.9);
                long end3 = System.currentTimeMillis();
                logger.info("压缩文件时间: " + (end3 - end2));
                logger.info("压缩后文件大小为: " + (filedirs.length() / 1024) + "k");
            }
            String url = "/" + newFileName + fileEnd;*/
            bigImgUrl = "/" + bigImgUrl;
            smallImgUrl = "/" + smallImgUrl;
//            String da=bigImgUrl.replace("\","\");
            return ResultMessage.ok(ConfigConstant.SUCCESS, "文件上传成功", "").put("img", bigImgUrl).put("smallImg", smallImgUrl);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return ResultMessage.error("文件上传失败");
    }

    /**
     * 上传视频
     *
     * @param file
     * @return
     */
    @RequestMapping("saveFile")
    public ResultMessage saveFile(MultipartFile file) {
        if (file.getSize() > 1024 * 1024 * 10) {
            return ResultMessage.error(ConfigConstant.ERROR, "视频过大");
        }
        String uuid32 = UUIDUtils.getUUID32();
        String f = FileUpload.fileUp(file, uploadPath, uuid32);
//        System.out.println(f);
        return ResultMessage.ok("/" + f);

    }

    /**
     * 功能描述: 富文本上传图片的方法
     *
     * @param:
     * @return:
     * @auther: 高山溪 By User
     * @date: 2018/10/27 19:04
     */
    @RequestMapping("/uploadEditor")
    public ResultMessage uploadEditor(MultipartFile imgFile) throws Exception {
        String fileName = imgFile.getOriginalFilename();
        // 判断文件不为空
        if (imgFile.getSize() == 0 || imgFile.isEmpty()) {
//            System.out.println("文件为空");
            return ResultMessage.error("文件为空");
        }
        // 判断文件大小
        if (imgFile.getSize() > upload_maxsize) {
//            System.out.println("文件大小超范围");
            return ResultMessage.error("文件大小超范围");
        }

        // 文件类型判断
        if (!this.checkFileType(fileName)) {
//            System.out.println("文件类型不允许");
            return ResultMessage.error("文件类型不允许");
        }

        // 保存文件的
        File saveFile = new File(uploadPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        //  String name = fileName.substring(0, fileName.lastIndexOf("."));
        //        System.out.println("文件名称：" + name);
        // 新的文件名
        String newFileName = this.getName(fileName);
        // 文件扩展名
        String fileEnd = this.getFileExt(fileName);
        // 绝对路径
        String fileNamedirs = uploadPath + File.separator + newFileName + fileEnd;
//        System.out.println("保存的路径：" + fileNamedirs);
        File filedirs = new File(fileNamedirs);
        // 转入文件
        try {
            imgFile.transferTo(filedirs);
            String url = "/" + newFileName + fileEnd;
            return ResultMessage.ok("文件上传成功").put("url", url).put("error", 0);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultMessage.error("文件上传失败");
    }

    /**
     * 文件类型判断
     *
     * @param fileName
     * @return
     */
    private boolean checkFileType(String fileName) {
        Iterator<String> type = Arrays.asList(allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 依据原始文件名生成新文件名
     *
     * @return
     */
    private String getName(String fileName) {
        Random random = new Random();
        return "" + random.nextInt(1000000) + System.currentTimeMillis();
    }


}
