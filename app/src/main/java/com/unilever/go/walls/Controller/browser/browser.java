package com.unilever.go.walls.Controller.browser;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.unilever.go.walls.R;

public class browser extends DialogFragment {
    private EditText mEditText;
    public static LinearLayout layoutprogress;
    public static String value;
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
        return inflater.inflate(R.layout.browser, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
        String url = "";
        //Utility
        if(value == "Lighting"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=LIG.svg";
        }else if(value == "AHU"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=AHU.svg";
        }else if(value == "HWG"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=HWG.svg";
        }else if(value == "Water Supply"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=WTS.svg";
        }else if(value == "Air Compressor"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=AIR.svg";
        }else if(value == "WTP"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=WTP.svg";
        }
        //Ammonia
        if(value == "NH3 Sensor"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=NH3.svg";
        }else if(value == "Refrigerant System"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=REF.svg";
        }else if(value == "Windsock"){
            url = "http://202.152.49.162:8004";
        }
        //Electric
        if(value == "ATS"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=ATS.svg";
        }else if(value == "Cotopaxy"){
            url = "https://unilever.strata-login.com/dashboard/view/17156";
        }else if(value == "Powermeter"){
            url = "https://unilever.strata-login.com/dashboard/view/17156";
        }
        //Coldstore
        if(value == "Coldstore Plant"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=CDS.svg";
        }else if(value == "Evaporator"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=EVA.svg";
        }
        //Mixing
        if(value == "Mixing Plant"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=MIX.svg";
        }else if(value == "Glycol"){
            url = "http://202.152.49.165:7131/GoWalls/_loadsvg.htm?src=GLY.svg";
        }
        //CCTV
        if(value == "Group 1"){
            url = "http://202.152.49.163:8086";
        }else if(value == "Group 2"){
            url = "http://202.152.49.163:8085";
        }else if(value == "Group 3"){
            url = "http://202.152.49.163:8084";
        }else if(value == "Group 4"){
            url = "http://202.152.49.163:8083";
        }else if(value == "Group 5"){
            url = "http://202.152.49.163:8082";
        }else if(value == "Group 6"){
            url = "http://202.152.49.163:8081";
        }else if(value == "Group 7"){
            url = "http://202.152.49.163:8080";
        }else if(value == "Group 8"){
            url = "http://202.152.49.162:8001";
        }else if(value == "Group 9"){
            url = "http://202.152.49.162:8002";
        }else if(value == "Group 10"){
            url = "http://202.152.49.162:8080";
        }else if(value == "Group 11"){
            url = "http://202.152.49.162:8008";
        }else if(value == "Group 12"){
            url = "http://202.152.49.162:8003";
        }

        Log.e(browser.class.getSimpleName(),url);
        WebView webview1 = view.findViewById(R.id.webView1);
        ProgressBar PB = view.findViewById(R.id.pbProcessing);
        webview1.getSettings().setJavaScriptEnabled(true) ;
        webview1.getSettings().setUseWideViewPort(true);
        webview1.getSettings().setLoadWithOverviewMode(true);
        webview1.getSettings().setDomStorageEnabled(true);
        webview1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview1.getSettings().setBuiltInZoomControls(true);
        webview1.getSettings().setDisplayZoomControls(false);
        webview1.loadUrl(url);

        webview1.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
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

        webview1.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                PB.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });



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
}