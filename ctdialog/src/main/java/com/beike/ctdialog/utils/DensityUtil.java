package com.beike.ctdialog.utils;

import android.content.Context;

public class DensityUtil {

	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值
	 */
	public static int px2sp(Context context, float pxValue) {
		float scale = context.getResources()
				.getDisplayMetrics().scaledDensity;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将sp值转换为px值
	 */
	public static int sp2px(Context context, float spValue) {
		float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * scale + 0.5f);
	}
}
