package com.unilever.go.wallsopsi1.Controller.home.gallery;

import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.galleryClassJson;
import com.unilever.go.wallsopsi1.R;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class view_image extends AppCompatActivity {
    android.app.Activity is_this;
    private ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    public static String urlimage = "";
    public static galleryClassJson.Result galleryResult;
    //    String[][] stringImageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false);
        progress.show();
        setContentView(R.layout.view_image);
        ImageView image = findViewById(R.id.image);
        new Thread(new Runnable() {
            public void run() {
                java.net.URL url = null;
                try {
                    url = new URL(urlimage);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap a = null;
                try {
                    Bitmap imageprofil = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageBitmap(imageprofil);
                            progress.dismiss();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    progress.dismiss();
                }
            }
        }).start();

        final ZoomLinearLayout zoomLinearLayout = (ZoomLinearLayout) findViewById(R.id.zoom_linear_layout);
        zoomLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomLinearLayout.init(view_image.this);
                return false;
            }
        });
    }

}


