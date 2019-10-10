package com.info.zxing;

/**
 * @Classname QrCodeTest
 * @Description TODO
 * @Date 2019/6/3 15:14
 * @Created by yml
 */
public class QrCodeTest {
    public static void main(String[] args) throws Exception {
        // 存放在二维码中的内容
        String text = "https://www.goldkeng.com/static/iosShare.html?regCode=12345689";
        // 嵌入二维码的图片路径
        String imgPath = "D:\\upload\\log.png";
        // 生成的二维码的路径及名称
        String destPath = "D:\\upload\\login.png";
        //生成二维码
        QRCodeUtil.encode(text, imgPath, destPath, true);
        // 解析二维码
        String str = QRCodeUtil.decode(destPath);
        // 打印出解析出的内容
        System.out.println(str);

    }
}
