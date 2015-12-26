package com.example.android_js;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android_js.Operator.onJSClick;

@SuppressLint("JavascriptInterface")
public class NormalWebViewActivity extends Activity {
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

	
	
	
//	@JavascriptInterface
//	private void addJSInterface() {
//		Operator ajsi = new Operator(new onJSClick() {
//			
//			@Override
//			public void onClick(String valueFromJS) {
//				Log.i("xx", "点击了我2，" + valueFromJS);
//				Message msg = Message.obtain();
//				msg.what = 1;
//				msg.obj = (String) valueFromJS;
//				mHandler.sendMessage(msg);
//				
//			}
//		});
//		webview.addJavascriptInterface(ajsi, "IAndroid");
//	}
	
//	@JavascriptInterface
//	@SuppressLint({ "NewApi", "SetJavaScriptEnabled", "JavascriptInterface" })
	private void initView() {
		progressLayoutView = (ProgressLayoutView) findViewById(R.id.progress_layout);

		webview = (WebView) findViewById(R.id.webview);
		relweb = (RelativeLayout) findViewById(R.id.title_bar);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDefaultTextEncodingName("utf-8");
//		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically( true);
//		 
//		webview.getSettings().setSupportZoom(true);
//		webview.getSettings().setBuiltInZoomControls(true);
//		webview.getSettings().setUseWideViewPort(true);
//		webview.getSettings().setLoadWithOverviewMode(true);
//
//		webview.getSettings().setUseWideViewPort(true);// 可任意比例缩放
//
//		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS); // 自动缩放
		this.webview.loadUrl(url);
		
		
		
//		webview.evaluateJavascript("test()", new ValueCallback<String>() {
//		
//		@Override
//		public void onReceiveValue(String value) {
//			
//			Log.i("xx", "点击了我2，" + value);
//		}
//	});
		
		//testEvaluateJavascript(webview);
		

//		webview.setWebViewClient(new WebViewClient() {
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				progressLayoutView.increaseProgressRef();
//			}
//
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//				return true;
//			}
//
//			@Override
//			public void onPageFinished(WebView view, String pageurl) {
//				progressLayoutView.decreaseProgressRef();
//				if (webview != null) {
//					if (!webview.getSettings().getLoadsImagesAutomatically()) {
//						webview.getSettings().setLoadsImagesAutomatically(true);
//					}
//					webview.loadUrl(String.format("javascript:test()")); 
//																		
//				}
//
//			}
//
//			@Override
//			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//				super.onReceivedError(view, errorCode, description, failingUrl);
//				if (webview != null) {
//					webview.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//					webview.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
//				}
//			}
//
//		});
	
		webview.addJavascriptInterface(new Operator(new onJSClick() {
			//ok
			@Override
			public void onClick(final String valueFromJS) {
				Log.i("xx", "点击了我1，" + valueFromJS);
				Message msg = Message.obtain();
				msg.what = 1;
				msg.obj = (String) valueFromJS;
				mHandler.sendMessage(msg);

			}
		}), "IAndroid");

		
		
//		webview.evaluateJavascript("test()", new ValueCallback<String>() {
//			
//			@Override
//			public void onReceiveValue(String value) {
//				
//				Log.i("xx", "点击了我2，" + value);
//			}
//		});
//		
//		Log.i("xx", "加载了");
//		this.webview.loadUrl(url);
		

	}

	
//	@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
//	@SuppressLint("NewApi")
//	private void testEvaluateJavascript(WebView webView) {
//		webView.evaluateJavascript("test()",
//				new ValueCallback<String>() {
//					@Override
//					public void onReceiveValue(String value) {
//						//webview.loadUrl("javascript:android_call_js_parameter('"+ value + "')");
//						
//						Message msg=Message.obtain();
//						msg.what=1;
//						msg.obj=value;
//						mHandler.sendMessage(msg);
//					}
//				});
//	}
	
	
	
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

	public void getChore() {
		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				if (message != null) {
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
				}
				result.cancel();
				return true;
			}

			@Override
			public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsConfirm(view, url, message, result);
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
				// TODO Auto-generated method stub
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}

		});
	}
}