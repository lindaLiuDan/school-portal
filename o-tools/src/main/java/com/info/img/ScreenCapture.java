package com.info.img;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: gs-info
 * @ClasssName: ScreenCapture
 * @Description: 该类是图片截屏的类只用调用该方法即可生成一个截屏文件 用于快照等东西
 * @Author: Gaosx 960889426@qq.com By Email
 * @Date: 2018/9/17 11:46
 * @Version: 1.0.0
 **/
public class ScreenCapture {


    public static void captureScreen(String fileName, String folder) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        // 截图保存的路径
        File screenFile = new File(fileName);
        // 如果路径不存在,则创建
        if (!screenFile.getParentFile().exists()) {
            screenFile.getParentFile().mkdirs();
        }
        //判断文件是否存在，不存在就创建文件
        if (!screenFile.exists() && !screenFile.isDirectory()) {
            screenFile.mkdir();
        }
        File f = new File(screenFile, folder);
        ImageIO.write(image, "png", f);
        //自动打开
        /*if (Desktop.isDesktopSupported()
                 && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
                    Desktop.getDesktop().open(f);*/
    }

    public static void main(String[] args) {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String data = sdf.format(dt);
        String rd = sdf1.format(dt);
        try {
            captureScreen("D:\\image\\" + data, rd + ".png");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void test() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String data = sdf.format(dt);
        String rd = sdf1.format(dt);
        try {
            captureScreen("D:\\image\\" + data, rd + ".png");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
