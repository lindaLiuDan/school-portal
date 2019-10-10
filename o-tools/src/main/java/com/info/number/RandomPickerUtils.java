package com.info.number;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * 用于从给定列表中随机选取某一个并返回
 *
 * @author mayn
 */
public class RandomPickerUtils {

    /**
     * 从一个列表中随机选择一个值并返回
     *
     * @param store 可选值列表
     * @param <T>   返回值类型
     * @return
     */
    public static <T> T pickOne(final List<T> store) {
        if (store == null || store.size() == 0) {
            throw new IllegalArgumentException("Store cannot be null or empty");
        }
        if (store.size() == 1) {
            return store.get(0);
        }
        final int total = store.size();
        final Random random = new Random();
        // generate random int value from 0 (included) to total (excluded)
        final int randomIdx = random.nextInt(total);
        return store.get(randomIdx);
    }

    /**
     * 生成随机数 自定义的长度和个数
     *
     * @param length 随机数长度
     * @param num    个数
     * @return
     */
    public static List<String> genCodes(int length, long num) {
        List<String> results = new ArrayList<String>();
        for (int j = 0; j < num; j++) {
            String val = "";
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

                if ("char".equalsIgnoreCase(charOrNum)) // 字符串
                {
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                    val += (char) (choice + random.nextInt(26));
                } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
                {
                    val += String.valueOf(random.nextInt(10));
                }
            }
            val = val.toLowerCase();
            if (results.contains(val)) {
                continue;
            } else {
                results.add(val);
            }
        }
        return results;
    }

    /**
     * 简单的生成随机生成6为随机数
     *
     * @return
     */
    public static int getNumber() {
        return (int) (Math.random() * 9000 + 1000);
    }

    /**
     * 随机生成0-999999的随机数
     *
     * @return
     */
    public static int getRandomNum() {
        Random random = new Random();
        return random.nextInt(999999);
    }

    /**
     * 随机指定范围内N个不重复的数 利用HashSet的特征，只能存放不同的值
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     * @param: HashSet<Integer> set 随机数结果集
     */
    public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
        if (n > (max - min + 1) || max < min) {
            return;
        }
        for (int i = 0; i < n; i++) {
            // 调用Math.random()方法
            int num = (int) (Math.random() * (max - min)) + min;
            set.add(num);// 将不同的数存入HashSet中
        }
        int setSize = set.size();
        // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
        if (setSize < n) {
            /**
             * 下面这一行为什么被注释掉了，说实话万能的度娘到处都是这段代码
             * 但是如果那样的话并不一定能确定生成对应的数值
             * 大佬们还是先仔细看一下，n-setSize只是剩下没有生成的数量，如果下次还没生成不同的数值那还怎么玩
             */
            randomSet(min, max, n - setSize, set);// 递归
//            randomSet(min, max, n, set);// 递归
        }
    }

    /**
     * 功能描述:
     *
     * @auther: Gaosx 960889426@qq.com By User
     * @param:
     * @date: 2018/11/28 10:26
     */
    public static void main(String[] args) {
//        System.out.println(genCodes(8, 1).get(0));
//        System.out.println(getNumber());
//        System.out.println(UUID.randomUUID().toString().replace("-", "").length());

        /**
         * 那就这样吧，根据你的需求去生成最合适的数值
         * 示例默认生成4位的随机数，从0到999当然不算了，所以就是8999位喽
         * 不过生成数量可别超过你给的范围，jvm会炸的
         */
        int max = 99999;//随机数范围的最大值
        int min = 1;//随机数范围的最小值
        int n = 1;//生成数量
        HashSet<Integer> numbers = new HashSet<Integer>();
        randomSet(min, max, n, numbers);
        System.out.println(numbers);//随意打印一下看看效果

        System.out.println("9个随机数：" + genCodes(9, 999999));

    }


}
