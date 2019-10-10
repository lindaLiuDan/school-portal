package com.info.pay.alipay;

import com.info.pay.weixinpay.PropertyUtil;

/**
 * 功能描述: 支付宝配置文件
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/7/9 12:45
 * @Return:
 */
public class AlipayConfig {

    //合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
//        public static String partner = "2088221943823477";
//        //商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
//        public static String private_key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKYTb4INjyBDvK+Rp14z04I6fZ0dPW9XtaoQDe9mQZOpBy7tYa4KGoqpm+IiCwu1SCVq2BEq3exnGxBkb+vaETQm8OKZq9jtZjWwdyXpx/hnoj/0lF2QFMQkbzqBXVPavvUIH6oANOF3tF/q0ojAQd7vFYVhnNUE6+ME6lHthVITAgMBAAECgYAGbdAuP6ofvJ+MSF4rRmRUsUD1mREswz90EuUHNHUrayqtBWF3pEp8va4cJec5j834LNeqandm1GL20nyGq81ooEDBtEnR2cN/foklo++w8VUFH3b1xile8jgFtcAgMBb32pPyOsmKd03aGuMy2MOuXgN+azmiZXOZdxqQpb7REQJBAPJyS/7G5X4hwi02yhqNEyiQ0XIvLQdvlR5a2v1e2GZK+vmyVpbVCnwndCZaVfdyXsyupvElb0rv9+e1gxsrLRsCQQCvXC74LhgbE3xJxVXy0Q6Dygt4mVWN4LUrhOQhOR19N1ZugqLz8Th5WVEAx/D/PzDaRIRbrJ0Dv0guDfvlRpZpAkBp4y97u8SZfQcAllBYnkrCydUgCKii+cHn+whv5o+exZqtmY0l3S/yH40j/wpSw1nSDWmOIqpj0UC1q1nycL4HAkAjYv3TdTHGHPuCw4ChnFBKhfYkowShucu1eX6zuZvazdTyPgh2I6Ja0oxHFRHr4CuSkCsJeN7Ch9IgARBo5wKpAkAFNYJAp8swFQkqz9yAufXbdc0H9kXK6NGAcFrw+ahhNZ8o2poDyb7QZ5Zfs+iTxdqz3jZuQ/Fn+dN4u+ZXCEy4";
//        //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
//        public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
//        // 签名方式
//        public static String sign_type = "RSA";
//        // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
//        //public static String log_path ="C://";
//        // 字符编码格式 目前支持 gbk 或 utf-8
//        public static String charset = "utf-8";
//        // 接收通知的接口名  回调接口
//        public static String notify_url = "http://122.114.146.160:8085/yihe/f/yihe/alipay/payCallback";
//        //public static String service = "mobile.securitypay.pay";
//        //APPID 豫货通天下的
//        public static String app_id="2017021705715795";
//        //阿里支付网关
//        public static String url="https://openapi.alipay.com/gateway.do";
//             public static String notify_url="http://www.yiheh.com/alireturn.aspx";
//        public  String sign;
//
//        public String getSign() {
//                return sign;
//        }
//
//        public void setSign(String sign) {
//                this.sign = sign;
//        }
//
    /**************************************帮采商贸的支付宝接口配置*****************************************/


    //合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String partner = "2088012690813163";
    //商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
    public static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC8QIJ7hnLFVOlQlR6F4e4V7audokPJYIqCiZQUnXG2gAh4xVg/GqjWwA425IG9EmF8EBuI9c54CApgE0SAKE9vkCp6aaVEQTT568FYTYls7H3RxeF5jhAoNkhqtbt27KqJS9tBrm4WW3v1uTOjImXd7nu0LTHVmYxatshPT0rSM3GGJMKcuzuWE3fdpFRAtM6OqEjUCZgHxk9nakaFemnEQFZZ1tzqbvdnMc15dAi9VyaURjABqZM51c2LonyaBNMAh1soKtEO7M3t8Hq0orT99prwm5uthSAth+gyAeDgJFeALt33qYfCnfN160waEAHStHieeUItwrDM+pGbyWRPAgMBAAECggEAPDZeBeiRGTD5l366zpPfNUBZ3geiquyoIOykYpuhXYBCNW7ZtQVv75Bqe4BHRxRz/dyhEgOdUKMxvl0rUdqS3do82/6ij9xTd4TCyN20ERTrgYdLgTfBTztdmlQabAVy3otWzAFg0OHeGlBshcdg0cWP56d1Ax8j13gTRdrU0bp5/u2CbkDnLhHMNQjmmcqsPSnpC6CltLtIP4zkaU8eanwZjIRRC+ZlHDxXoUENW0rxqctO8GvSq5dKvq3ZJEs1o1JqE5QumsTbDu6OpKYurd1lkXN6cfqyJVRChETdLG7n599Dv/pe+fW+LW4XeOSnRlELH9YYwB9q81kM8eY6gQKBgQDmzpOewzbQZ+ERIhu1G3lwddBOM4rfQnQJa8bInk3ZR7en0rRN7cy3ane303ADkNHdMpEnebl55Fc1J41UUekM+Kg3NAiPjbOY8DbsTIsgC6rcZP8y+z+YCYV8lf/jppWbnxkPMPJY10wjjwzytrnU2X0OPcAFwNMrh0Bwa3sIYQKBgQDQzNKn99yG8KIxG/NdMGcvl/NIpYGDrZetfJCpFZAsa+Ti40rwS0PiHRjOk4R9MeP4pcSoDhJOJfawjDr4PFTOnzWQYIFKudK4nu9+AKp7nfR0rGByTVU8Ux7gpfTUsAsBQveGcCz/oMonNRlZbMNg47fsTxLeghXz4nBWcsbqrwKBgQCIo1cFMDBtJHI7gGPCzecQys5s3VqjXWXwhduCvST9MtXpBnYzhEojc2Y4b3XqxntXZi5Enm1NWf6+uxGSD4PWaAa915IR2iOusNbzAUWDzSRzoV7rQY6eZVSfMvXwQZhkLsXZFnMCYULz8iYKMl7FQZQlMC1TupnJNSLS6u5FQQKBgGi6AwK6gEKyWTd2hHb+OxMVDLIoSTCplgUvEEOujBSrvS7LwB+MMK0t2O5r8Sz2JBjn1B5SiH3n7raYb09ou0SzeCNFNDQtxfJRzV+Op/O+wwjuGKXcAd9y9W7J7mXrMmnck9g7VWaV2C8YI/HFaq2jKfH9q0ItTyN3NM83XGwDAoGAREgGYcRGziCynDg0g8C/2K25K74vzCcOm1KOleK3iBgtJZrO3RFeF5oiDbVzjsR1C5w8haGJW1IosWs+ZFOPErgWK/L3lILMAbvBQ1uBUtKqPGDkXN6vo09qiSq8318w7yPNEWIZ4jYwkl9hFymSONnFSpV6eplv+YOJEzi0Gto=";
    //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    public static String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    // 签名方式
    public static String sign_type = "RSA";
    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
    //public static String log_path ="C://";
    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String charset = "utf-8";
    // 接收通知的接口名  回调接口
    public static String notify_url = "https://webapp.bang-cai.com/f/yihe/alipay/payCallback";
    //public static String service = "mobile.securitypay.pay";
    //APPID 帮采商贸appID
    public static String app_id = "2017080908115699";
    //阿里支付网关 https://openapi.alipay.com/gateway.do
    public static String url = "https://openapi.alipay.com/gateway.do";

    //public static String notify_url="http://www.yiheh.com/alireturn.aspx";
    public String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


}
