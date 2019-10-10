package com.info.number;

import com.info.date.DateUtils;
import com.info.string.UUIDUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: 生成子订单No的Class
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param:
 * @date: 2018/12/31 12:03
 */
public class GenerateOrderNo {


    /**
     * 功能描述: 生成商品编号的方法
     *
     * @auther: Gaosx 960889426@qq.com By User
     * @param:
     * @date: 2018/11/23 21:53
     */
    public static String getNumberNo() {
//        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUIDUtils.getUUID32().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型 return machineId + String.format("%015d", hashCodeV);
        return String.format("%d", hashCodeV);
    }

    /**
     * 功能描述: 生成子订单No方法
     *
     * @auther: Gaosx 960889426@qq.com By User
     * @param:
     * @date: 2018/11/23 21:53
     */
    public static String getOrderIdByUUId() {
//        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUIDUtils.getUUID32().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型 return machineId + String.format("%015d", hashCodeV);
        return DateUtils.newDate() + String.format("%d", hashCodeV);
    }

    /**
     * @Author: Gaosx 960889426@qq.com
     * @Description: 生成父订单的NO
     * @Date: 13:09 2018/10/21
     */
    public static String getParentOrderIdByUUId() {
//        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUIDUtils.getUUID32().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型 return machineId + String.format("%015d", hashCodeV);
        return String.format("%05d", hashCodeV);
    }

    /**
     * 功能描述: 用户编号，投资顾问编号，团队编号的生成方法
     * str 是生成的编号前面的前缀英文字母
     *
     * @Author: Gaosx 960889426@qq.com
     * @Description: 生成父订单的NO
     * @Date: 13:09 2018/10/21
     */
    public static String randomNumbers(String str) {
//        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUIDUtils.getUUID32().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        String numbers = String.format("%05d", hashCodeV);
//        System.out.println("CCCCC:：" + numbers);
        numbers = numbers.substring(0, 6);
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型 return machineId + String.format("%015d", hashCodeV);
        if (str.trim() != null) {
            numbers = str + numbers;
        }
        return numbers;
    }


    public static void main(String[] args) {
//        System.out.println(getOrderIdByUUId());
//        System.out.println("长度：" + getOrderIdByUUId());
//        System.out.println("长度：" + getParentOrderIdByUUId());
        List list = new ArrayList();
        for (int i = 0; i < 1000000; i++) {
            String numbers = getParentOrderIdByUUId();
            System.out.println("截取后的单号：" + numbers);
//            System.out.println("长度：" + numbers.length());
            list.add(numbers);
        }
        System.out.println("List长度：" + list.size());
        long count = list.stream().distinct().count();
        boolean isRepeat = count < list.size();
        System.out.println("A:" + count);//输出2
        System.out.println("B:" + isRepeat);//输出true
    }


}




