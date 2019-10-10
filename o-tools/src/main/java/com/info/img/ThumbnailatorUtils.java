package com.info.img;

import com.info.string.UUIDUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: 压缩文件使用工具类
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param:
 * @date: 2019/1/9 20:31
 */
@Component
public class ThumbnailatorUtils {

    private final Logger logger = LoggerFactory.getLogger(ThumbnailatorUtils.class);

    /**
     * 图片起始压缩值
     */
    @Value("${imgSize}")
    private Integer imgSize;

    /*@Autowired
    private FastDFSClient fastDFSClient;*/

    public static void main(String[] args) {
        // ImgThumb("C:\\Users\\mayn\\Desktop\\123\\22083103.jpg", "C:\\Users\\mayn\\Desktop\\123\\12321321.jpg", 200, 200);
    }

    /**
     * 指定大小进行缩放
     * 若图片横比width小，高比height小，不变 若图片横比width小，高比height大，高缩小到height，图片比例不变
     * 若图片横比width大，高比height小，横缩小到width，图片比例不变
     * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    public void ImgThumb(String source, String output, int width, int height) {
        try {
            Thumbnails.of(source).size(width, height).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定大小进行缩放
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    public void ImgThumb(File source, String output, int width, int height) {
        try {
            Thumbnails.of(source).size(width, height).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照比例进行缩放
     *
     * @param source 输入源
     * @param output 输出源
     * @param scale
     */
    public void ImgScale(String source, String output, double scale) {
        try {
            Thumbnails.of(source).scale(scale).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgScale(File source, String output, double scale) {
        try {
            Thumbnails.of(source).scale(scale).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不按照比例，指定大小进行缩放
     *
     * @param source          输入源
     * @param output          输出源
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public void ImgNoScale(String source, String output, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgNoScale(File source, String output, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转 ,正数：顺时针 负数：逆时针
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param rotate 角度
     */
    public void ImgRotate(String source, String output, int width, int height, double rotate) {
        try {
            Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgRotate(File source, String output, int width, int height, double rotate) {
        try {
            Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 水印
     *
     * @param source       输入源
     * @param output       输入源
     * @param width        宽
     * @param height       高
     * @param position     水印位置 Positions.BOTTOM_RIGHT o.5f
     * @param watermark    水印图片地址
     * @param transparency 透明度 0.5f
     * @param quality      图片质量 0.8f
     */
    public void ImgWatermark(String source, String output, int width, int height, Position position, String watermark, float transparency, float quality) {
        try {
            Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgWatermark(File source, String output, int width, int height, Position position, String watermark, float transparency, float quality) {
        try {
            Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片
     *
     * @param source          输入源
     * @param output          输出源
     * @param position        裁剪位置
     * @param x               裁剪区域x
     * @param y               裁剪区域y
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public void ImgSourceRegion(String source, String output, Position position, int x, int y, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgSourceRegion(File source, String output, Position position, int x, int y, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按坐标裁剪
     *
     * @param source          输入源
     * @param output          输出源
     * @param x               起始x坐标
     * @param y               起始y坐标
     * @param x1              结束x坐标
     * @param y1              结束y坐标
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public void ImgSourceRegion(String source, String output, int x, int y, int x1, int y1, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgSourceRegion(File source, String output, int x, int y, int x1, int y1, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转化图像格式
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     */
    public void ImgFormat(String source, String output, int width, int height, String format) {
        try {
            Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImgFormat(File source, String output, int width, int height, String format) {
        try {
            Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出到OutputStream
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @return toOutputStream(流对象)
     */
    public OutputStream ImgOutputStream(String source, String output, int width, int height) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(output);
            Thumbnails.of(source).size(width, height).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    public OutputStream ImgOutputStream(File source, String output, int width, int height) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(output);
            Thumbnails.of(source).size(width, height).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    /**
     * 输出到BufferedImage
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     * @return BufferedImage
     */
    public BufferedImage ImgBufferedImage(String source, String output, int width, int height, String format) {
        BufferedImage buf = null;
        try {
            buf = Thumbnails.of(source).size(width, height).asBufferedImage();
            ImageIO.write(buf, format, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    public BufferedImage ImgBufferedImage(File source, String output, int width, int height, String format) {
        BufferedImage buf = null;
        try {
            buf = Thumbnails.of(source).size(width, height).asBufferedImage();
            ImageIO.write(buf, format, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    /**
     * @param imageFile 图片文件
     * @return
     * @Description:保存图片并且生成缩略图
     */
    public Map<String, Object> uploadFileAndCreateThumbnail(MultipartFile imageFile) throws Exception {
        logger.info("进入保存图片程序----");
        long start0 = System.currentTimeMillis();
        File descFile = multipartToFile(imageFile);
        // 获取扩展名
        String filename = descFile.getName();
        String extName = filename.substring(filename.lastIndexOf(".") + 1);
        String bigImgUrl = "";
        File file = new File(DictionaryTemplate.getFileParentPath(), UUIDUtils.getUUID32() + "." + extName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        Map<String, Object> map = new HashMap<String, Object>(0);
        try {
            // 如果源文件大于100KB, 就进行压缩, 压缩成100, 如果小于100, 则不进行处理
            if (descFile.length() > imgSize) {
                logger.info("文件大于" + (imgSize / 1024) + "k, 进行压缩操作");
                // 递归压缩，直到目标文件大小小于desFileSize
                long end2 = System.currentTimeMillis();
                commpressPicCycle(descFile, imgSize, 0.8);
                long end3 = System.currentTimeMillis();
                logger.info("压缩文件时间: " + (end3 - end2));
                logger.info("压缩后文件大小为: " + (descFile.length() / 1024) + "k");
                bigImgUrl = descFile.getAbsolutePath();
            } else {
                bigImgUrl = descFile.getAbsolutePath();
            }
            long start = System.currentTimeMillis();
            try {
                Thumbnails.of(descFile).size(120, 120).toFile(file);
                String smallImgUrl = file.getAbsolutePath();
                smallImgUrl.replace("\\", "/");
                map.put("smallImg", smallImgUrl);
            } catch (Exception e1) {
//                map.put("smallImg", bigImgUrl);
                logger.error("压缩图片工具异常: ", e1);
            }
            bigImgUrl.replace("\\", "/");
            long end = System.currentTimeMillis();
            logger.info("生成缩略图时间: " + (end - start));
            map.put("img", bigImgUrl);
            long end0 = System.currentTimeMillis();
            logger.info("方法执行总时间: " + (end0 - start0));
        } catch (Exception e) {
            logger.error("图片压缩失败");
        }
        return map;
    }

    /**
     * @param imageFile 图片文件
     * @return
     * @Description:保存图片并且生成缩略图
     */
    public Map<String, Object> uploadFile(MultipartFile imageFile) throws Exception {
        logger.info("进入保存图片程序----");
        long start0 = System.currentTimeMillis();
        File descFile = multipartToFile(imageFile);
        // 获取扩展名
        String filename = descFile.getName();
        String extName = filename.substring(filename.lastIndexOf(".") + 1);
        File file = new File(DictionaryTemplate.getFileParentPath(), UUIDUtils.getUUID32() + "." + extName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        Map<String, Object> map = new HashMap<String, Object>(0);
        try {
            // 如果源文件大于100KB, 就进行压缩, 压缩成100, 如果小于100, 则不进行处理
            if (descFile.length() > imgSize) {
                logger.info("文件大于" + (imgSize / 1024) + "k, 进行压缩操作");
                // 递归压缩，直到目标文件大小小于desFileSize
                long end2 = System.currentTimeMillis();
                commpressPicCycle(descFile, imgSize, 0.8);
                long end3 = System.currentTimeMillis();
                logger.info("压缩文件时间: " + (end3 - end2));
                logger.info("压缩后文件大小为: " + (descFile.length() / 1024) + "k");
            }
            long start = System.currentTimeMillis();
            try {
                Thumbnails.of(descFile).size(305 * 4, 120 * 2).toFile(descFile);
                String bigImgUrl = descFile.getAbsolutePath();
                bigImgUrl.replace("\\", "/");
                map.put("bigImgUrl", bigImgUrl);
            } catch (Exception e) {
                logger.error("error: ", e);
            }
            try {
                Thumbnails.of(descFile).size(102 * 2, 120 * 2).toFile(file);
                String smallImgUrl = file.getAbsolutePath();
                smallImgUrl.replace("\\", "/");
                map.put("smallImgUrl", smallImgUrl);
            } catch (Exception e) {
                logger.error("error: ", e);
            }
            long end = System.currentTimeMillis();
            logger.info("生成缩略图时间: " + (end - start));
            long end0 = System.currentTimeMillis();
            logger.info("方法执行总时间: " + (end0 - start0));
        } catch (Exception e) {
            logger.error("图片压缩失败");
        }
        return map;
    }


    /**
     * 上传文件
     *
     * @param imageFile
     * @return
     * @throws Exception
     */
    public Map<String, Object> upload(MultipartFile imageFile) throws Exception {
        logger.info("进入保存文件程序----");
        long start0 = System.currentTimeMillis();
        File descFile = multipartToFile(imageFile);
        // 获取扩展名
        String filename = descFile.getName();
//        String extName = filename.substring(filename.lastIndexOf(".") + 1);
//        File file = new File(DictionaryTemplate.getFileParentPath(), UUIDUtils.getUUID32() + "." + extName);
//        if (!file.exists()) {
//            file.getParentFile().mkdirs();
//            file.createNewFile();
//        }
        Map<String, Object> map = new HashMap<String, Object>(0);
        String files = descFile.getAbsolutePath();
        files.replace("\\", "/");
        map.put("file", files);
        return map;
    }

    /**
     * 递归压缩图片到指定大小
     *
     * @param descFile    图片
     * @param desFileSize 指定大小
     * @param accuracy    压缩精度, 建议小于0.9
     * @throws IOException
     */
    public void commpressPicCycle(File descFile, long desFileSize,
                                  double accuracy) throws IOException {
        long srcFileSizeJPG = descFile.length();
        // 2、判断大小，如果小于500kb，不压缩；如果大于等于500kb，压缩
        if (srcFileSizeJPG <= desFileSize) {
            return;
        }
        // 计算宽高
        BufferedImage bim = ImageIO.read(descFile);
        int srcWdith = bim.getWidth();
        int srcHeigth = bim.getHeight();
        int desWidth = new BigDecimal(srcWdith).multiply(
                new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(srcHeigth).multiply(
                new BigDecimal(accuracy)).intValue();

        Thumbnails.of(descFile).size(desWidth, desHeight)
                .outputQuality(accuracy).toFile(descFile);
        commpressPicCycle(descFile, desFileSize, accuracy);
    }

    /**
     * MultipartFile 转换成File
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    public File multipartToFile(MultipartFile multfile) throws IOException {
        String extName = multfile.getOriginalFilename().substring(multfile.getOriginalFilename().lastIndexOf("."));

        File file = new File(DictionaryTemplate.getFileParentPath(), UUIDUtils.getUUID32() + extName);
        file.getParentFile().mkdirs();
        file = file.getAbsoluteFile();
        multfile.transferTo(file);
        return file;
    }

    /**
     * 只修改源文件的图片质量, 返回新图片文件
     *
     * @param source  原图文件
     * @param width   图片宽度
     * @param height  图片高度
     * @param quality 新图质量
     * @return
     */
    public File outputWithQuality(File source, Integer width, Integer height, float quality) throws IOException {
        final String extName = source.getAbsolutePath().substring(source.getAbsolutePath().lastIndexOf("."));
        final File file = new File(DictionaryTemplate.getFileParentPath(), UUIDUtils.getUUID32() + extName);

        Thumbnails.of(source).size(width, height).outputQuality(quality).toFile(file);

        return file;
    }

    public File thumbnail(File source, Integer width, Integer height) throws IOException {
        final String extName = source.getAbsolutePath().substring(source.getAbsolutePath().lastIndexOf("."));
        final File file = new File(DictionaryTemplate.getFileParentPath(), UUIDUtils.getUUID32() + extName);

        Thumbnails.of(source).size(width, height).toFile(file);

        return file;
    }
}
