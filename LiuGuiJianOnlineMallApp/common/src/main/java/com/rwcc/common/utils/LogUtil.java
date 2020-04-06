package com.rwcc.common.utils;

import android.util.Log;

import java.util.Hashtable;

/**
 *@Title:LogUtil
 *@Desc: log辅助类
 *@Author: Pengwc
 *@Date: 2019-6-26 10:10
 */
public class LogUtil {
	private static boolean DEGUG = true;
	private static String TAG = "pwc";
	private static LogUtil logUtil = null;

	private LogUtil(String name) {
		if (!name.equals(""))
			setTAG(name);
	}

	private LogUtil() {

	}

	public static LogUtil getInstance() {
		if (logUtil == null) {
			logUtil = new LogUtil();
		}
		return logUtil;
	}

	private String printFunctionName(String msg) {
		final StackTraceElement[] stack = new Throwable().getStackTrace();
		final int i = 1;
		final StackTraceElement ste = stack[1];
		return String.format("[%s:%s]:%s--%s", ste.getClassName(),
				ste.getMethodName(), ste.getLineNumber(), msg);
	}
	private static String getFunctionName(String msg) {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(getInstance().getClass().getName())) {
				continue;
			}
			return String.format("[%s : %s() : %s]--%s", st.getFileName(),
					st.getMethodName(), st.getLineNumber(), msg);

		}
		return null;
	}
	public static void i(String msg) {
		if (DEGUG) {
			Log.i(TAG, getFunctionName(msg));
			// Log.i(TAG, printFunctionName(msg));

		}
	}

	/**
	 * Log.w
	 * 
	 * @param msg
	 */
	public static void w(String msg) {
		if (DEGUG) {
			Log.w(TAG, getFunctionName(msg));
			// Log.w(TAG, printFunctionName(msg));
		}
	}

	/**
	 * Log.e
	 * 
	 * @param msg
	 */
	public static void e(String msg) {
		if (DEGUG) {
			Log.e(TAG, getFunctionName(msg));
			// Log.e(TAG, printFunctionName(msg));
		}
	}

	/**
	 * Log.d
	 * 
	 * @param msg
	 */
	public static void d(String msg) {
		if (DEGUG) {
			Log.d(TAG, getFunctionName(msg));
			// Log.d(TAG, printFunctionName(msg));
		}
	}

	/**
	 * Log.v
	 * 
	 * @param msg
	 */
	public static void v(String msg) {
		if (DEGUG) {
			Log.v(TAG, getFunctionName(msg));
			// Log.v(TAG, printFunctionName(msg));

		}
	}

	/**
	 * Log.error
	 * 
	 * @param e
	 */
	public static void error(Exception e, String msg) {
		if (DEGUG) {
			StringBuffer sb = new StringBuffer();
			String name = getFunctionName(msg);
			StackTraceElement[] sts = e.getStackTrace();

			if (name != null) {
				sb.append(name + " - " + e + "\r\n");
			} else {
				sb.append(e + "\r\n");
			}
			if (sts != null && sts.length > 0) {
				for (StackTraceElement st : sts) {
					if (st != null) {
						sb.append("[ " + st.getFileName() + ":"
								+ st.getLineNumber() + " ]\r\n");
					}
				}
			}
			Log.e(TAG, sb.toString());
		}

	}

	static Hashtable<String, Long> startTimeHashtable = new Hashtable<String, Long>();

	/**
	 * ��ӡ��ʼ��name
	 * 
	 * @param name
	 */
	public static void startTime(String name) {
		if (!startTimeHashtable.containsKey(name)) {
			startTimeHashtable.put(name, System.currentTimeMillis());
		}
	}

	/**
	 * @param name
	 *            ��ӡ�ı�ʶ��
	 */
	public static void endTime(String name) {
		if (startTimeHashtable.containsKey(name)) {
			float wasteTime = ((float) (System.currentTimeMillis() - startTimeHashtable
					.get(name)) / 1000);
			startTimeHashtable.remove(name);
		}
	}

	public static String getTAG() {
		return TAG;
	}

	public static void setTAG(String tAG) {
		TAG = tAG;
	}

	public static void setDebug(boolean debug) {
		DEGUG = debug;
	}

	public static boolean getDebug() {
		return DEGUG;
	}
}
