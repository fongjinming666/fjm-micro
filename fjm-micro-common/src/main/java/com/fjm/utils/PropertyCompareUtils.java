package com.fjm.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * @Author: jinmingfong
 * @CreateTime: 2022-05-18 09:13
 * @Description: 比较实际的参数及值
 */
@Slf4j
public class PropertyCompareUtils {


    /**
     * @param compareType  比较类型  ==，>,<  等等
     * @param targetValue  目标值(答案）
     * @param currentValue 当前值
     *                     * @return      boolean
     * @Author jinmingfong
     * @Description 比较方法
     * @date 2021-4-19 17:49
     */
    public static boolean comparePropertyValue(String compareType, String targetValue, String currentValue) {
        log.info("compareType:{},targetValue:{},currentValue:{}", compareType, targetValue, currentValue);
        switch (compareType) {
            case "==":
                if (Objects.equals(currentValue, targetValue)) {
                    return true;
                }
                break;
            case "in":
                String[] splitTargetValue = targetValue.replace("[", "").replace("]", "").trim().split(",");
                List<String> targetValueList = Arrays.asList(splitTargetValue);
                if (targetValueList.contains(currentValue)) {
                    return true;
                }
                break;
            case ">":
                if (Integer.valueOf(currentValue) > Integer.valueOf(targetValue)) {
                    return true;
                }
                break;
            case "<":
                if (Integer.valueOf(currentValue) < Integer.valueOf(targetValue)) {
                    return true;
                }
                break;
            case ">=":
                if (Integer.valueOf(currentValue) >= Integer.valueOf(targetValue)) {
                    return true;
                }
                break;
            case "<=":
                if (Integer.valueOf(currentValue) <= Integer.valueOf(targetValue)) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }


//    public static void main(String[] args) {
//        String compareType = "in";
//        String targetValue = "1,2,3,5";
//        String currentValue = "4";
//        boolean b = comparePropertyValue(compareType, targetValue, currentValue);
//        System.out.println(b);
//    }

}
