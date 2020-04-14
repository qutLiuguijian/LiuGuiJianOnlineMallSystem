package com.rwcc.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

/**
 *@Title:GsonUtil
 *@Desc: gson解析工具类
 *@Author: Pengwc
 *@Date: 2019-6-26 18:09
 */
public class GsonUtil {

	public static final String DATE_FORMAT = "yyyy-MM-module_popupwindow_decrease_normal HH:mm:ss";

	public static Gson getGson() {
		return new GsonBuilder().serializeNulls().setDateFormat(DATE_FORMAT)
				.create();
	}


	public String object2Json(Object obj) {
		return getGson().toJson(obj);
	}


	public static <T> String t2Json(T t) {
		return getGson().toJson(t);
	}

	/**
	 * json 转 实体
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T json2T(String jsonString, Class<T> clazz) {
		return getGson().fromJson(jsonString, clazz);
	}

	/**
	 *json 转集合类
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static <T> List<T> json2Collection(String jsonStr, Type type) {
		return getGson().fromJson(jsonStr, type);
	}

}
