/**
 * 
 */
package com.ruiwcc.okhttpPlus.response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.internal.$Gson$Types;

/**
 *@Title:ResultCallback
 *@Desc:
 *@Author: Pengwc
 *@Date: 2019-6-26 17:31
 */
public abstract class ResultCallback<T> {
	Type mType;
	public ResultCallback() {
		mType = getSuperclassTypeParameter(getClass());
	}
   
	static Type getSuperclassTypeParameter(Class<?> subclass) {
		Type superclass = subclass.getGenericSuperclass();
		if (superclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		ParameterizedType parameterized = (ParameterizedType) superclass;
		return $Gson$Types
				.canonicalize(parameterized.getActualTypeArguments()[0]);
	}
     
	public abstract void onError(Exception e);
        
	public abstract void onResponse(T response);
	
	
	
}