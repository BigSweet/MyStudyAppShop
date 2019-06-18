package activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.demo.swt.mystudyappshop.R
import com.demo.swt.mystudyappshop.Util.LogUtils
import kotlinx.android.synthetic.main.web_view_activity.*




/**
 * 介绍：这里写介绍
 * 作者：sunwentao
 * 邮箱：wentao.sun@yintech.cn
 * 时间: 10/7/18
 */

class WebViewActivity : AppCompatActivity() {


    private var mWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view_activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mWebView = WebView(applicationContext)
        mWebView?.layoutParams = params
        mLayout.addView(mWebView)
        initWebView()
        mWebView?.loadUrl("http://www.wanandroid.com")

    }

    private fun initWebView() {
        //声明WebSettings子类
        val webSettings = mWebView?.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings?.let {
            it.javaScriptEnabled = true
            //设置自适应屏幕，两者合用
            it.useWideViewPort = true //将图片调整到适合webview的大小
            it.loadWithOverviewMode = true // 缩放至屏幕的大小
            //缩放操作
            it.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            it.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
            it.displayZoomControls = false //隐藏原生的缩放控件
            //其他细节操作
//            it.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webview中缓存
            it.allowFileAccess = true //设置可以访问文件
            it.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
            it.loadsImagesAutomatically = true //支持自动加载图片
            it.defaultTextEncodingName = "utf-8"//设置编码格式
        }

        mWebView?.webViewClient = object : WebViewClient() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }
        }

        mWebView?.webChromeClient = object : WebChromeClient() {

            /* 获得网页的加载进度 */
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                LogUtils.d(newProgress.toString()+"swt")
                if (newProgress == 100) {
                    progressBar.visibility = View.INVISIBLE
                    // view.removeView(loadingLayout); /* 若只想加载一次 progressBar 则开启这句 */
                } else {
                    if (View.INVISIBLE === progressBar.getVisibility()) {
                        progressBar.visibility = View.VISIBLE
                    }
                    progressBar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
            override fun onReceivedTitle(view: WebView, title: String) {
                titletv.text = title
            }
        }


    }

    //点击返回上一页面而不是退出浏览器
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView?.canGoBack() == true) {
            mWebView?.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        super.onResume()
        mWebView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mWebView?.onPause()
    }

    //销毁Webview
    override fun onDestroy() {
        mWebView?.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        mWebView?.clearHistory()

        (mWebView?.parent as ViewGroup).removeView(mWebView)
        mWebView?.destroy()
        mWebView = null
        super.onDestroy()
    }
}