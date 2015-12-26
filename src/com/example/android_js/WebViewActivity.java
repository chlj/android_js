package com.example.android_js;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android_js.Operator.onJSClick;

@SuppressLint("JavascriptInterface")
public class WebViewActivity extends Activity {
	private ProgressLayoutView progressLayoutView = null;
	private String url = "file:///android_asset/android.html";
	private WebView webview;
	private RelativeLayout relweb;// 根布局

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camerwebview_activity);

		initView();
	}

	private void initView() {
		progressLayoutView = (ProgressLayoutView) findViewById(R.id.progress_layout);

		webview = (WebView) findViewById(R.id.webview);
		relweb = (RelativeLayout) findViewById(R.id.title_bar);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDefaultTextEncodingName("utf-8");

		this.webview.loadUrl(url);

		webview.addJavascriptInterface(new Operator(new onJSClick() {
			@Override
			public void onClick(final String valueFromJS) {
				Message msg = Message.obtain();
				msg.what = 1;
				msg.obj = (String) valueFromJS;
				mHandler.sendMessage(msg);

			}
		}), "IAndroid");

	}

	private Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(getApplicationContext(), String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		if (webview != null) {
			relweb.removeView(webview);
			webview.removeAllViews();
			webview.destroy();
			webview = null;
		}
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (webview.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event); // 网页不能后退了，就退出activity
	}

}