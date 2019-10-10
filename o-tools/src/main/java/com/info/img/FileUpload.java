package com.info.img;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;

/**
 * 上传文件
 * 创建人：Gaosx
 * 创建时间：2018年12月23日
 */
@Controller
public class FileUpload {

//    @Autowired
//    private FastDFSClient fastDFSClient;


    /**
     * 上传文件
     *
     * @param file     //文件对象
     * @param filePath //上传路径
     * @param fileName //文件名
     * @return 文件名
     */
    public static String fileUp(MultipartFile file, String filePath, String fileName) {
        String extName = ""; // 扩展名格式：
        try {
            if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//                System.out.println(file.getOriginalFilename());
                extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            copyFile(file.getInputStream(), filePath, fileName + extName).replaceAll("-", "");
        } catch (IOException e) {
            e.getStackTrace();
        }
        return fileName + extName;
    }

    /**
     * 上传文件到fastdfs
     *
     * @param file
     * @return
     * @throws Exception
     */
//    @AuthIgnore
//    @PostMapping("/order/fileupload")
//    @ResponseBody
//    public String fileUpload(MultipartFile file) throws Exception {
//        return fastDFSClient.uploadFile(file.getBytes(), file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1), null);
//    }

    /**
     * 写文件到当前目录的upload目录中
     *
     * @param in
     * @param dir
     * @param realName
     * @throws IOException
     */
    private static String copyFile(InputStream in, String dir, String realName)
            throws IOException {
        File file = mkdirsmy(dir, realName);
        FileUtils.copyInputStreamToFile(in, file);
        return realName;
    }


    /**
     * 判断路径是否存在，否：创建此路径
     *
     * @param dir      文件路径
     * @param realName 文件名
     * @throws IOException
     */
    public static File mkdirsmy(String dir, String realName) throws IOException {
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }


    /**
     * 下载网络图片上传到服务器上
     *
     * @param httpUrl    图片网络地址
     * @param filePath   图片保存路径
     * @param myFileName 图片文件名(null时用网络图片原名)
     * @return 返回图片名称
     */
    public static String getHtmlPicture(String httpUrl, String filePath, String myFileName) {

        URL url;                        //定义URL对象url
        BufferedInputStream in;            //定义输入字节缓冲流对象in
        FileOutputStream file;            //定义文件输出流对象file
        try {
            String fileName = null == myFileName ? httpUrl.substring(httpUrl.lastIndexOf("/")).replace("/", "") : myFileName; //图片文件名(null时用网络图片原名)
            url = new URL(httpUrl);        //初始化url对象
            in = new BufferedInputStream(url.openStream());                                    //初始化in对象，也就是获得url字节流
            //file = new FileOutputStream(new File(filePath +"\\"+ fileName));
            file = new FileOutputStream(mkdirsmy(filePath, fileName));
            int t;
            while ((t = in.read()) != -1) {
                file.write(t);
            }
            file.close();
            in.close();
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 视频压缩 测试
     *
     * @return
     */
    public static MultipartFile compressVedio() {
        //视频压缩的代码,这个是利用转码进行压缩,但是mp4压缩之后会造成无法播放.
     /*   File source = new File("C:\\Users\\Administrator\\Desktop\\mmcf.mp4");
        File target = new File("C:\\Users\\Administrator\\Desktop\\mmcf1.mp4");*/
/*        try {

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(new Integer(56000));
            audio.setChannels(new Integer(1));
            audio.setSamplingRate(new Integer(22050));
            VideoAttributes video = new VideoAttributes();
            video.setCodec("mpeg4");
            video.setBitRate(new Integer(800000));
            video.setFrameRate(new Integer(15));
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp4");
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);
            Encoder encoder = new Encoder();
            encoder.encode(source, target, attrs);
        }
        catch (EncoderException e) {
            e.printStackTrace();
        }*/

        return null;
    }

}
