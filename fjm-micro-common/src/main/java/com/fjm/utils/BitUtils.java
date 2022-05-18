package com.fjm.utils;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-08-10 上午11:00
 * @Description:
 */

/**
 * Java 位运算的常用方法封装<br>
 */
public class BitUtils {
    /**
     * 获取运算数指定位置的值<br>
     * 例如： 0000 1011 获取其第 0 位的值为 1, 第 2 位 的值为 0<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=31)
     * @return 指定位置的值(0 or 1)
     */
    public static int getBitValue(int source, int pos) {
        return ((source >> pos) & 1);
    }

    /**
     * 将运算数指定位置的值置为指定值<br>
     * 例: 0000 1011 需要更新为 0000 1111, 即第 2 位的值需要置为 1<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=31)
     * @param value  只能取值为 0, 或 1, 所有大于0的值作为1处理, 所有小于0的值作为0处理
     * @return 运算后的结果数
     */
    public static int setBitValue(int source, int pos, int value) {

        int mask = (1 << pos);
        if (value > 0) {
            source |= mask;
        } else {
            source &= (~mask);
        }

        return source;
    }

    /**
     * 将运算数指定位置取反值<br>
     * 例： 0000 1011 指定第 3 位取反, 结果为 0000 0011; 指定第2位取反, 结果为 0000 1111<br>
     *
     * @param source
     * @param pos    指定位置 (0<=pos<=31)
     * @return 运算后的结果数
     */
    public static int reverseBitValue(int source, int pos) {
        int mask = (1 << pos);
        return (source ^ mask);
    }

    /**
     * 检查运算数的指定位置是否为1<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=31)
     * @return true 表示指定位置值为1, false 表示指定位置值为 0
     */
    public static boolean checkBitValue(int source, int pos) {

        source = (source >>> pos);

        return (source & 1) == 1;
    }

    /**
     * 16进制转10进制
     *
     * @param hex
     * @return
     */
    public static int hex2decimal(String hex) {
        return Integer.parseInt(hex, 16);
    }

    /**
     * 10进制转16进制
     *
     * @param i
     * @return
     */
    public static String demical2Hex(int i) {
        String s = Integer.toHexString(i);
        return s;
    }

    /**
     * 16进制转2进制
     *
     * @param hex
     * @return
     */
    public static String hexStringToByte(String hex) {
        int i = Integer.parseInt(hex, 16);
        String str2 = Integer.toBinaryString(i);
        return str2;
    }

    /**
     * 2进制转10进制
     *
     * @param bytes
     * @return
     */
    public static int ByteToDecimal(String bytes) {
        return Integer.valueOf(bytes, 2);
    }

    /**
     * 10进制转2进制
     *
     * @param n
     * @return
     */
    public static String Demical2Byte(int n) {
        String result = Integer.toBinaryString(n);
        return result;
    }

    /**
     * 入口函数做测试<br>
     *
     * @param args
     */
    public static void main(String[] args) {
        // 取十进制 11 (二级制 0000 1011) 为例子
        int source = 11;
        // 取第2位值并输出, 结果应为 0000 1011
        for (int i = 7; i >= 0; i--) {
            System.out.printf("%d ", getBitValue(source, i));
        }
        // 将第6位置为1并输出 , 结果为 75 (0100 1011)
        System.out.println("\n" + setBitValue(source, 6, 1));
        // 将第6位取反并输出, 结果应为75(0100 1011)
        System.out.println(reverseBitValue(source, 6));
        // 检查第6位是否为1，结果应为false
        System.out.println(checkBitValue(source, 6));
        // 输出为1的位, 结果应为 0 1 3
        for (int i = 0; i < 8; i++) {
            if (checkBitValue(source, i)) {
                System.out.printf("%d ", i);
            }
        }
    }
}
