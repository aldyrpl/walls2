package com.unilever.go.walls.Controller.home.gallery;

import com.unilever.go.walls.Controller.Retrofit.GalleryAPI;
import com.unilever.go.walls.Controller.Retrofit.galleryClassJson;
import com.unilever.go.walls.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class gallery extends AppCompatActivity{
    android.app.Activity is_this;
    private ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    public static final String URL = "http://13.228.214.159/mosii/belajar_api/api/";
    public static galleryClassJson.Result galleryResult;
    //    String[][] stringImageURL;
    DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        imageView = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        TextView close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GalleryAPI api = retrofit.create(GalleryAPI.class);
        Call<galleryClassJson> call = api.getGallery("1", "1","20","0");
        call.enqueue(new Callback<galleryClassJson>() {
            @Override
            public void onResponse(Call<galleryClassJson> call, Response<galleryClassJson> response) {

                galleryResult = response.body().getResult();

                Log.d("gallery", "List count: " + galleryResult.getListData().size());
                String stringImageURL[][] = new String[galleryResult.getListData().size()][];
                for(int i = 0;i < galleryResult.getListData().size(); i++){

                    stringImageURL[i] = new String[galleryResult.getListData().get(i).getData().size()];
                            for(int j = 0; j < galleryResult.getListData().get(i).getData().size();j++){
                                stringImageURL[i][j] = galleryResult.getListData().get(i).getData().get(j).getImage();
                            }
                }

                ArrayList imageUrlList = prepareData(stringImageURL);
                dataAdapter = new DataAdapter(getApplicationContext(), imageUrlList);
                recyclerView.setAdapter(dataAdapter);


            }

            @Override
            public void onFailure(Call<galleryClassJson> call, Throwable t) {
            }
        });

//        dataAdapter.setClickListener(this);
    }
//
//    @Override
//    public void onItemClick(View view, int position) {
//        Log.i("TAG", "You clicked number " + dataAdapter.getItemId(position) + ", which is at cell position " + position);
//    }



    private ArrayList prepareData(String[][] stringimageURL_) {

//        String imageUrls[] = {
//                "url1",
//                "url2",
//                "url3",
//                "url4"};

        ArrayList imageUrlList = new ArrayList<>();
        for (int i = 0; i < stringimageURL_.length; i++) {

            for (int j = 0; j < stringimageURL_[i].length; j++) {
                Log.d("gallery", "stringImageURL : " + stringimageURL_[i][j]);
                ImageUrl imageUrl = new ImageUrl();
                imageUrl.setImageUrl(stringimageURL_[i][j]);
                imageUrlList.add(imageUrl);
            }
        }
        Log.d("gallery", "List count: " + imageUrlList.size());
        return imageUrlList;
    }

    public class ImageUrl {
        String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}


