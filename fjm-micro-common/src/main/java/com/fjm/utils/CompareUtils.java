package com.fjm.utils;

import com.aliyun.openservices.shade.org.apache.commons.lang3.StringUtils;


/**
 * @Author: jinmingfong
 * @CreateTime: 2020-05-24 18:59
 * @Description:
 */
public class CompareUtils {

    /**
     * 确认更新版本是否比当前版本高
     *
     * @param currentVersion
     * @param upgradeVersion
     * @return
     */
    public static boolean compareUpgradeVersionIsLager(String currentVersion, String upgradeVersion) {
        if (StringUtils.isBlank(currentVersion) || StringUtils.isBlank(upgradeVersion)) {
            return false;
        }
        String[] currentVersionArray = currentVersion.split("\\.");
        String[] upgradeVersionArray = upgradeVersion.split("\\.");
        if (currentVersionArray.length < 0 || upgradeVersionArray.length < 0) {
            return false;
        }
        int sameArrayCount = 0;
        if (currentVersionArray.length > upgradeVersionArray.length) {
            sameArrayCount = upgradeVersionArray.length;
        } else {
            sameArrayCount = currentVersionArray.length;
        }
        for (int i = 0; i < sameArrayCount; i++) {
            if (Long.parseLong(currentVersionArray[i].replaceAll("[^\\d]", ""))
                    < Long.parseLong(upgradeVersionArray[i].replaceAll("[^\\d]", ""))) {
                return true;
            } else if (Long.parseLong(currentVersionArray[i].replaceAll("[^\\d]", ""))
                    > Long.parseLong(upgradeVersionArray[i].replaceAll("[^\\d]", ""))) {
                return false;
            }
        }

        if (currentVersionArray.length > upgradeVersionArray.length) {
            if (currentVersionArray.length >= sameArrayCount) {
                if (Long.parseLong(currentVersionArray[sameArrayCount].replaceAll("[^\\d]", "")) > 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else if (upgradeVersionArray.length > currentVersionArray.length) {
            if (upgradeVersionArray.length >= sameArrayCount) {
                if (Long.parseLong(upgradeVersionArray[sameArrayCount].replaceAll("[^\\d]", "")) > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
    * @Author      hw
    * @Description 比较WiFi固件版本,比较更新版本是否比当前版本高
    * @param      currentVersion  当前版本（旧）
    * @param      upgradeVersion  更新版本（新）
    * @return      boolean
    * @date        2021-5-14 11:24
    */
    public static boolean compareFirmwareVersion(String currentVersion,String upgradeVersion){
//        svn://119.23.241.105/VCoo/01-VCOO产品/01-VCOO技术白皮书/VeeLink/Veelink通用OTA文件头协议
//        WiFi固件版本号定义 AABBBBCCCCCCYYYYMMDD
//        wifi模组 固件版本：01000101010120200410  配置文件和WiFi绑定
//        AA：类别
//            wifi 	    01
//            wifi + ble  02
//        BBBB:模组型号
//            8710B   0001
//            8711    0002
//            esp32   0003
//            esp8266 0004
//        CCCCCC:版本号
//            010101   V1.1.1
//        YYYYMMDD：年月日
//            20200410
        return compareUpgradeVersionIsLager(currentVersion.substring(6,currentVersion.length()), upgradeVersion.substring(6,upgradeVersion.length()));
    }
    /**
     * @Author      hw
     * @Description 比较更新版本是否比当前版本高或者相等
     * @param      currentVersion  当前版本（旧）
     * @param      upgradeVersion  更新版本（新）
     * @return      boolean
     * @date        2021-5-14 11:24
     */
    public static boolean comareFirmwareVersionWithEqual(String currentVersion,String upgradeVersion){

        boolean isNew = compareFirmwareVersion(currentVersion, upgradeVersion);
        boolean isEqual = currentVersion.substring(6,currentVersion.length()).equals(upgradeVersion.substring(6,upgradeVersion.length()));
        return isNew || isEqual;
    }

    public static void main(String[] args) {
//        String currentVersion = "02000101010120200410";
        String currentVersion = "01000101020120201127";
        String upgradeVersion = "01000101020120201127";

        boolean b = compareUpgradeVersionIsLager(currentVersion, upgradeVersion);
        System.out.println(b);
        boolean c = compareUpgradeVersionIsLager(currentVersion.substring(6,currentVersion.length()), upgradeVersion.substring(6,upgradeVersion.length()));
        System.out.println(c);
        System.out.println(compareFirmwareVersion(currentVersion,upgradeVersion));
    }

}
