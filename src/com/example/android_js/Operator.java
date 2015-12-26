package com.example.android_js;

import android.webkit.JavascriptInterface;


public class Operator {
	private onJSClick mCallBack;

	public Operator() {
	}

	public interface onJSClick {
		void onClick(String valueFromJS);
	}
	public Operator(onJSClick callBack) {
		mCallBack = callBack;
	}

	/**
	 * 得到js返回值
	 * 
	 * @param valueFromJS
	 * @return
	 */
	@JavascriptInterface
	public void getJsReturn(String valueFromJS) {
		mCallBack.onClick(valueFromJS);
	}

}
