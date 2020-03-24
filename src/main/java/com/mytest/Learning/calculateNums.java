package com.mytest.Learning;

import java.util.HashMap;

/**
 * @Author wanwei
 * @Date 2020/3/24 17:19
 * @Description
 * @Reviewer
 */
public class calculateNums {
    public static void main(String[] args){
        cal1();
        cal2();
    }

    public static void cal1(){
        int array[] = new int[]{1,3,8,9,15,12,15,3,3,9};

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0; i< array.length; i++){
            if (map.containsKey(array[i])){
                map.put(array[i], map.get(array[i]) + 1);
            }
            else{
                map.put(array[i], 1);
            }
        }
        for(HashMap.Entry<Integer, Integer> entry: map.entrySet()){
            if(entry.getValue() == 2){
                System.out.println("方法一：\n" + "Key:" + entry.getKey() + ",Value:" + entry.getValue());
            }
        }
    }

    public static void cal2(){
        int array[] = new int[]{1,3,8,9,15,12,15,3,3,9};

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0; i< array.length; i++){
            if (map.get(array[i]) == null){
                map.put(array[i], 1);
            }
            else{
                map.put(array[i], map.get(array[i]) + 1);
            }
        }
        for(HashMap.Entry<Integer, Integer> entry: map.entrySet()){
            if(entry.getValue() == 2){
                System.out.println("方法二：\n" + "Key:" + entry.getKey() + ",Value:" + entry.getValue());
            }
        }
    }
}
