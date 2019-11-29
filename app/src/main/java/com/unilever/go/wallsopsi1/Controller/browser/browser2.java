package com.unilever.go.wallsopsi1.Controller.browser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.unilever.go.wallsopsi1.R;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;


public class browser2 extends AppCompatActivity {

    public static LinearLayout layoutprogress;
    public static String value;
    XWalkView webview1;
    ProgressBar PB;
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser);
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
        }else if(value == "Group 9"){
            url = "http://202.152.49.162:8002";
        }else if(value == "Loading Luar CS"){
            url = "http://202.152.49.162:8080";
        }else if(value == "Packing Hall"){
            url = "http://202.152.49.162:8008";
        }else if(value == "Perimeter Walls"){
            url = "http://202.152.49.162:8003";
        }
        try {
            webview1 = findViewById(R.id.webView1);
        }catch (Exception e){

        }
        try{
            webview1.setResourceClient(new ResourceClient(webview1));
        }catch (Exception e){

        }
        try{
            PB = findViewById(R.id.pbProcessing);
        }catch (Exception e){

        }
        try{
            webview1.clearCache(true);
        }catch (Exception e){

        }
        try{
            webview1.post(new Runnable() {
                @Override
                public void run() {
                    try{
                        webview1.loadUrl(url);
                    }catch (Exception e){

                    }
                }
            });
        }catch (Exception e){

        }
    }

    class ResourceClient extends XWalkResourceClient {

        public ResourceClient(XWalkView xwalkView) {
            super(xwalkView);
        }

        public void onLoadStarted(XWalkView view, String url) {
            super.onLoadStarted(view, url);
//            Log.d(TAG, "Load Started:" + url);
        }

        public void onLoadFinished(XWalkView view, String url) {
            super.onLoadFinished(view, url);
            try {
                PB.setVisibility(View.GONE);
                webview1.setVisibility(View.VISIBLE);
                Log.d("tesuYeah", "Load Finished xwalk:" + url);
            }catch (Exception e){

            }
        }

        public void onProgressChanged(XWalkView view, int progressInPercent) {
            super.onProgressChanged(view, progressInPercent);
//            Log.d(TAG, "Loading Progress:" + progressInPercent);
        }
    }

    @Override
    public void onDestroy() {
        try {
            webview1.onDestroy();
            finish();
        }catch (Exception e){

        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        try {
            webview1.onDestroy();
            finish();
        }catch (Exception e){

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                webview1.onDestroy();
                finish();
            }catch (Exception e){

            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
