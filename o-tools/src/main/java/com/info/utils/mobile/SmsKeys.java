package com.info.utils.mobile;

/**
 * 短信签名模板
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/11 18:20
 * -------------------------------------------------------------- <br>
 */
public class SmsKeys {

    public static final String APP_NAME = "Facecast";


    /**
     * 国内版短信正文模板ID
     */
    public static class Domestic {

        /**
         * 签名
         */
        public static final String FACCECAST = "河南国超";

        /**
         * 信息变更验证码
         * 验证码{1}，您正在尝试变更{2}重要信息，请妥善保管账户信息。
         */
        public static final int ACCOUNT_UPGRADES = 106564;

        /**
         * 修改密码验证码
         * 验证码{1}，您正在尝试修改{2}登录密码，请妥善保管账户信息。
         */
        public static final int PASSWORD_RESET = 106562;

        /**
         * 注冊
         * {1}为Facecast的验证码,仅用于注册,请于{2}分钟内填写,请勿告知他人,如有疑问联系客服。
         */
        public static final int SIGN_UP = 105895;


    }

    /**
     * 国外版短信正文内容
     */
    public static class Foreign {

        /**
         * 签名
         */
        public static final String FACCECAST = "Facecast";

        /**
         * AccountUpgrades
         * Account Upgrades Verification code {1}
         */
        public static final int ACCOUNT_UPGRADES = 153706;

        /**
         * PasswordReset
         * {1} is password Reset Verification code.
         */
        public static final int PASSWORD_RESET = 153707;

        /**
         * SignUp
         * Register verification code {1}.
         */
        public static final int SIGN_UP = 153703;
    }

}
