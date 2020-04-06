package com.rwcc.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SharedHelper {
    /**
     * 保存在手机里面的文件名
     */
    private static final String DEFAULT_FILE_NAME = "sharedData";
    private static   SharedPreferences sp;
    /**
     * @Function: 在application的时候初始化调用下
     * @Author: Pengwc
     * @Date: 2019-6-28 16:53
     * @Param:fileName 保存文件名
     * @Return:
     */
    public static void init(Context context,String saveFileName){
        if(sp==null){
            if(saveFileName!=null && !"".equals(saveFileName)){
                sp = context.getSharedPreferences(saveFileName, Context.MODE_PRIVATE);
            }else{
                sp = context.getSharedPreferences(DEFAULT_FILE_NAME, Context.MODE_PRIVATE);
            }

        }
    }
    public static SharedPreferences.Editor getEditor(){
        return sp.edit();
    }
      /** putString 方法封装 */
    public static void putString(String key,String value){
        sp.edit().putString(key, value).commit();
    }
    /** putString 方法封装 */
    public static void putInt(String key,int value){
        sp.edit().putInt(key, value).commit();
    }
    /** putBoolean 方法封装 */
    public static void putBoolean(String key,boolean value){
        sp.edit().putBoolean(key, value).commit();
    }
    /** putLong 方法封装 */
    public static void putLong(String key,long value){
        sp.edit().putLong(key, value).commit();
    }
    /** putFloat 方法封装 */
    public static void putFloat(String key,float value){
        sp.edit().putFloat(key, value).commit();
    }
    /** putStringSet 方法封装 */
    public static void putStringSet(String key, Set<String> value){
        sp.edit().putStringSet(key, value).commit();
    }
    /** getString 方法封装*/
    public static String getString(String key,String defaultValue){
        return sp.getString(key, defaultValue);
    }
    /** getString 方法封装*/
    public static boolean getBoolean(String key,boolean defaultValue){
        return sp.getBoolean(key, defaultValue);
    }
    /** getFloat 方法封装*/
    public static float getFloat(String key,float defaultValue){
        return sp.getFloat(key, defaultValue);
    }
    /** getLong 方法封装*/
    public static long getLong(String key,long defaultValue){
        return sp.getLong(key, defaultValue);
    }
    /** getString 方法封装*/
    public static Set<String> get(String key,Set<String> defaultValue){
        return sp.getStringSet(key, defaultValue);
    }
    /**
     * @Function: 保存一个Map数据
     * @Author: Pengwc
     * @Date: 2019-6-28 16:28
     * @Param:[dataMap]
     * @Return: void
     */
    public static void putMap(Map<String,Object> dataMap){
        if(dataMap==null ||dataMap.size()<0){
            return;
        }
         for(String key : dataMap.keySet()){
             Object object = dataMap.get(key);
             String type =object.getClass().getSimpleName();
             if("String".equals(type)){
                 sp.edit().putString(key, (String)object);
             }
             else if("Integer".equals(type)){
                 sp.edit().putInt(key, (Integer)object);
             }
             else if("Boolean".equals(type)){
                 sp.edit().putBoolean(key, (Boolean)object);
             }
             else if("Float".equals(type)){
                 sp.edit().putFloat(key, (Float)object);
             }
             else if("Long".equals(type)){
                 sp.edit().putLong(key, (Long)object);
             }
         }
        sp.edit().commit();
    }


    /**
     * 得到保存数据的方法
     * @return
     */
    public static Map<String,?> getMap(Map<String,Object> paramsMap){

        if(paramsMap==null ||paramsMap.size()<0){
            return null;
        }
       Map<String,Object>  dataMap =new HashMap<>();
        for(String key : paramsMap.keySet()){
            Object  defaultObject =paramsMap.get(key);
            String type = defaultObject.getClass().getSimpleName();
            if("String".equals(type)){
                dataMap.put(key,sp.getString(key, (String)defaultObject));
            }
            else if("Integer".equals(type)){
                dataMap.put(key,sp.getInt(key, (Integer)defaultObject));
            }
            else if("Boolean".equals(type)){
                dataMap.put(key,sp.getBoolean(key, (Boolean)defaultObject));
            }
            else if("Float".equals(type)){
                dataMap.put(key,sp.getFloat(key, (Float)defaultObject));
            }
            else if("Long".equals(type)){
                dataMap.put(key,sp.getLong(key, (Long)defaultObject));
            }
        }
        return dataMap;
    }
}