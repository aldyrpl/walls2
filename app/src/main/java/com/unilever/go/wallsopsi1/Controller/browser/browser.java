package com.unilever.go.wallsopsi1.Controller.browser;


import android.app.Dialog;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.unilever.go.wallsopsi1.R;

//import org.mozilla.geckoview.GeckoRuntime;
//import org.mozilla.geckoview.GeckoSession;

//import org.xwalk.core.XWalkPreferences;
//import org.xwalk.core.XWalkView;
public class browser extends DialogFragment {
    private EditText mEditText;
    public static LinearLayout layoutprogress;
    public static String value;
    WebView webview1;
//    DialogFragment is_this;
    public browser() {
        // Constructor kosong diperlukan untuk DialogFragment.
        // Pastikan tidak memberikan argument/parameter apapun ke 
        // constructor ini.
    }

    public static browser newInstance(String title) {
        browser frag = new browser();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.browser2, container);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                webview1.destroy();
                webview1 = null;
                dismiss();
            }
        };
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        is_this = this;
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
        String url = "";
        //Utility
        //URL Lighting http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=LIG.svg
        if(value == "Lighting"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//LIG/";
        }else if(value == "AHU"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//AHU/";
        }else if(value == "HWG"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//HWG/";
        }else if(value == "Water Supply"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//WTS/";
        }else if(value == "Air Compressor"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//AIR/";
        }else if(value == "WTP"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//WTP/";
        }
        //Ammonia
        if(value == "NH3 Sensor"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//NH3/";
        }else if(value == "Refrigerant System"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//REF/";
        }else if(value == "Windsock"){
            url = "http://202.152.49.162:8004";
        }
        //Electric
        if(value == "ATS"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//ATS/";
        }else if(value == "Cotopaxy"){
            url = "https://unilever.strata-login.com/dashboard/view/17156";
        }else if(value == "Powermeter"){
            url = "https://unilever.strata-login.com/dashboard/view/17156";
        }
        //Coldstore
        if(value == "Coldstore Plant"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//CDS/";
        }else if(value == "Evaporator"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//EVA/";
        }
        //Mixing
        if(value == "Mixing Plant"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//MIX/";
        }else if(value == "Glycol"){
            url = "http://103.136.25.83:8088/FTVP/m/#/display//GLY/";
        }
        //CCTV
        if(value == "CS3 AREA"){
            url = "http://202.152.49.163:8086";
        }else if(value == "Area Parking truck"){
            url = "http://202.152.49.163:8085";
        }else if(value == "CS3 Gudang"){
            url = "http://202.152.49.163:8084";
        }else if(value == "CS3 Despatch"){
            url = "http://202.152.49.163:8083";
        }else if(value == "Ageing Mixing"){
            url = "http://202.152.49.163:8082";
        }else if(value == "CS2 & Eng PH"){
            url = "http://202.152.49.163:8081";
        }else if(value == "Loading CS"){
            url = "http://202.152.49.163:8080";
        }else if(value == "Utility & Mixing"){
            url = "http://202.152.49.162:8001";
        }else if(value == "PH & RMS"){
            url = "http://202.152.49.162:8002";
        }else if(value == "Loading Luar CS"){
            url = "http://202.152.49.162:8080";
        }else if(value == "Packing Hall"){
            url = "http://202.152.49.162:8008";
        }else if(value == "Perimeter Walls"){
            url = "http://202.152.49.162:8003";
        }

        Log.e(browser.class.getSimpleName(),url);
        webview1 = view.findViewById(R.id.webView1);
        ProgressBar PB = view.findViewById(R.id.pbProcessing);
//        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        webview1.getSettings().setJavaScriptEnabled(true) ;
        webview1.getSettings().setUseWideViewPort(true);
        webview1.getSettings().setLoadWithOverviewMode(true);
        webview1.getSettings().setDomStorageEnabled(true);
        webview1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview1.getSettings().setBuiltInZoomControls(true);
        webview1.getSettings().setDisplayZoomControls(false);
//        webview1.clearCache(true);
//        webview1.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

//        webview1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webview1.getSettings().setPluginState(1);
//        webview1.getSettings().setAllowFileAccess(true);
//        webview1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webview1.loadUrl(url);
        webview1.setVerticalScrollBarEnabled(false);
        webview1.setHorizontalScrollBarEnabled(false);
//        webview1.getSettings().setLoadWithOverviewMode(true);
//        webview1.getSettings().setSupportMultipleWindows(true);
//        webview1.requestFocus(View.FOCUS_DOWN);
//        webview1.isHardwareAccelerated();
//        if (Build.VERSION.SDK_INT >= 19) {
//            webview1.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        }
//        else {
//            webview1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }



        webview1.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                getActivity().getWindow().setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                PB.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return false;
            }

        });
//
        webview1.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webview1, url);
                try
                {
                    PB.setVisibility(View.GONE);
                    webview1.setVisibility(View.VISIBLE);
                }
                catch(Exception e)
                {

                }
            }

        });
//
//        webview1.requestFocus(View.FOCUS_DOWN);
//        webview1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                    case MotionEvent.ACTION_UP:
//                        if (!v.hasFocus()) {
//                            v.requestFocus();
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
//        Toast.makeText(getActivity(),"user agent : " + webview1.getSettings().getUserAgentString().toString(), Toast.LENGTH_LONG).show();


//        try
//        {
//            ProgressBar progressDialog = view.findViewById(R.id.progress);
//            layoutprogress = view.findViewById(R.id.layoutprogress);
//            progressDialog.setVisibility(View.VISIBLE);
//        }
//        catch(Exception e)
//        {
//        }
    }

    @Override
    public void onDestroy() {
        webview1.destroy();
        webview1 = null;
        super.onDestroy();
    }
}