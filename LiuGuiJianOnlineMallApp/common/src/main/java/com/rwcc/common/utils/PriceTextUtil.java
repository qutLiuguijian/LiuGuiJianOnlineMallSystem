package com.rwcc.common.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

/**
 *@Title:PriceTextUtil
 *@Desc: 价格辅助类
 *@Author: Pengwc
 *@Date: 2019-7-5 0:29
 */
public class PriceTextUtil {

    /**
     * 小数点后数字缩小更改
     *
     * @param value
     * @return
     */
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.8f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
}
