package com.unilever.go.wallsopsi1.Controller.home.gallery;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.unilever.go.wallsopsi1.BuildConfig;
import com.unilever.go.wallsopsi1.Controller.Retrofit.GalleryAPI;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.galleryClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.uploadImageJson;
import com.unilever.go.wallsopsi1.Controller.intro.login;
import com.unilever.go.wallsopsi1.R;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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
    public static final String URL = "http://103.136.25.83:8000/api/";
    public static galleryClassJson.Result galleryResult;
    //    String[][] stringImageURL;
    DataAdapter dataAdapter;
    private FloatingActionButton fabupload;
    public final int GALLERY_REQUEST_CODE = 234;
    public final int CAMERA_REQUEST_CODE = 567;
    private String cameraFilePath;
    public File image;
    TextView previousCat = null;
    String catId = "1";
    TextView notfound = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        imageView = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        LinearLayout menu = (LinearLayout) findViewById(R.id.menu);
        if(!getResources().getBoolean(R.bool.orientationnya)){
//            menu.setGravity(cen);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            params.gravity = Gravity.CENTER;

            menu.setLayoutParams(params);
        }
        TextView close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
        fabupload = (FloatingActionButton) findViewById(R.id.upload);
        fabupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStoragePermissionGranted()){
                    if(isReadStoragePermissionGranted()){
                       if(isCameraPermissionGranted()){
                           showPictureDialog();
                       }
                    }
                }
            }
        });
        notfound = findViewById(R.id.notfound);
        TextView utility = findViewById(R.id.utility);
        previousCat = utility;
        utility.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                previousCat.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                previousCat.setTypeface(utility.getTypeface(), Typeface.NORMAL);
                utility.setBackground(getResources().getDrawable(R.drawable.border_kuning_bawah));
                utility.setTypeface(utility.getTypeface(), Typeface.BOLD);
                previousCat = utility;
                catId = "1";
                prepare();
            }
        });

        TextView ammonia = findViewById(R.id.ammonia);
        ammonia.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                previousCat.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                previousCat.setTypeface(ammonia.getTypeface(), Typeface.NORMAL);
                ammonia.setBackground(getResources().getDrawable(R.drawable.border_kuning_bawah));
                ammonia.setTypeface(ammonia.getTypeface(), Typeface.BOLD);
                previousCat = ammonia;
                catId = "2";
                prepare();
            }
        });

        TextView electricity = findViewById(R.id.electricity);
        electricity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                previousCat.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                previousCat.setTypeface(electricity.getTypeface(), Typeface.NORMAL);
                electricity.setBackground(getResources().getDrawable(R.drawable.border_kuning_bawah));
                electricity.setTypeface(electricity.getTypeface(), Typeface.BOLD);
                previousCat = electricity;
                catId = "3";
                prepare();
            }
        });

        TextView coldstore = findViewById(R.id.coldstore);
        coldstore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                previousCat.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                previousCat.setTypeface(coldstore.getTypeface(), Typeface.NORMAL);
                coldstore.setBackground(getResources().getDrawable(R.drawable.border_kuning_bawah));
                coldstore.setTypeface(coldstore.getTypeface(), Typeface.BOLD);
                previousCat = coldstore;
                catId = "4";
                prepare();
            }
        });

        TextView mixing = findViewById(R.id.mixing);
        mixing.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                previousCat.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                previousCat.setTypeface(mixing.getTypeface(), Typeface.NORMAL);
                mixing.setBackground(getResources().getDrawable(R.drawable.border_kuning_bawah));
                mixing.setTypeface(mixing.getTypeface(), Typeface.BOLD);
                previousCat = mixing;
                catId = "5";
                prepare();
            }
        });
        prepare();
    }


    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d(gallery.class.getSimpleName(), "imgDecodableString : " + imgDecodableString);
                    uploadToServer(imgDecodableString);
                    break;
                case CAMERA_REQUEST_CODE:
                    Log.d(gallery.class.getSimpleName(), "cameraFilePath : " + cameraFilePath);
                    uploadToServer(cameraFilePath);
                    break;
            }
    }

    private void prepare(){
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Load Photos...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GalleryAPI api = retrofit.create(GalleryAPI.class);
        Call<galleryClassJson> call = api.getGallery(login.dataUser.getId(),catId, "1","100","0");
        call.enqueue(new Callback<galleryClassJson>() {
            @Override
            public void onResponse(Call<galleryClassJson> call, Response<galleryClassJson> response) {
                try {
                    galleryResult = response.body().getResult();

                    Log.d("gallery", "List count: " + galleryResult.getListData().size());
                    String stringImageURL[][] = new String[galleryResult.getListData().size()][];
                    for (int i = 0; i < galleryResult.getListData().size(); i++) {

                        stringImageURL[i] = new String[galleryResult.getListData().get(i).getData().size()];
                        for (int j = 0; j < galleryResult.getListData().get(i).getData().size(); j++) {
                            stringImageURL[i][j] = galleryResult.getListData().get(i).getData().get(j).getImage();
                        }
                    }

                    ArrayList imageUrlList = prepareData(stringImageURL);
                    dataAdapter = new DataAdapter(getApplicationContext(), imageUrlList);
                    recyclerView.setAdapter(dataAdapter);
                    notfound.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    notfound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                progress.dismiss();


            }

            @Override
            public void onFailure(Call<galleryClassJson> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                pickFromGallery();
                                break;
                            case 1:
                                captureFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void uploadToServer(String filePath) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Uploading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
//        progress.dismiss();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        Log.d(gallery.class.getSimpleName(), "yow 1 ");
        GalleryAPI uploadAPIs = retrofit.create(GalleryAPI.class);
        //Create a file object using file path
        File file = null;
        if(cameraFilePath != "" && cameraFilePath != null){
            try{
                Log.d(gallery.class.getSimpleName(), "yow 1.5 ");
                file = image;
            }catch (Exception e){

            }

        }else{
            file = new File(filePath);
        }

        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("img", file.getName(), fileReqBody);
        //Create request body with text description and text media type
//        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        RequestBody ref_menu_category_id = RequestBody.create(MediaType.parse("text/plain"), catId);
        Log.d(gallery.class.getSimpleName(), "yow 2");
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), login.dataUser.getId());
        Call<uploadImageJson> call = uploadAPIs.uploadImage(part, ref_menu_category_id, user_id);
        call.enqueue(new Callback<uploadImageJson>() {
            @Override
            public void onResponse(Call<uploadImageJson> call, Response<uploadImageJson> response) {
                Log.d(gallery.class.getSimpleName(), "message > " + response.message());
                Log.d(gallery.class.getSimpleName(), "raw > " + response.raw());
                Toast.makeText(getApplicationContext(),
                        "Success Upload Image",
                        Toast.LENGTH_SHORT).show();
                prepare();
                progress.dismiss();
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error no internet connection!", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

    private void captureFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(gallery.class.getSimpleName(),"Permission is granted");
                return true;
            } else {

                Log.v(gallery.class.getSimpleName(),"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(gallery.class.getSimpleName(),"Permission is granted");
            return true;
        }
    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(gallery.class.getSimpleName(),"Permission read ext is granted");
                return true;
            } else {

                Log.v(gallery.class.getSimpleName(),"Permission read ext is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(gallery.class.getSimpleName(),"Permission is granted");
            return true;
        }
    }

    public  boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(gallery.class.getSimpleName(),"Permission is granted");
                return true;
            } else {

                Log.v(gallery.class.getSimpleName(),"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(gallery.class.getSimpleName(),"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(gallery.class.getSimpleName(),"Permission: "+permissions[0]+ "was "+grantResults[0]);
            showPictureDialog();
        }
    }

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


