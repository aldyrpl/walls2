package com.unilever.go.wallsopsi1.Controller.home;

import com.unilever.go.wallsopsi1.BuildConfig;
import com.unilever.go.wallsopsi1.Controller.Retrofit.LoginAPI;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.listJabatanClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.listLokasiClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.myProfileClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.updateProfileClassJson;
import com.unilever.go.wallsopsi1.Controller.home.gallery.gallery;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

public class my_profile extends AppCompatActivity{
    android.app.Activity is_this;
    EditText namalengkap;
    EditText email;
    Spinner jabatan;
    EditText nohp;
    Spinner lokasi;
    String idJabatan = "";
    String idLokasi = "";
    ImageView fotoprofil;
    public File image;
    private String cameraFilePath;
    public final int GALLERY_REQUEST_CODE = 234;
    public final int CAMERA_REQUEST_CODE = 567;
    public ArrayList<String> listJabatan = new ArrayList<String>();
    public ArrayList<String> listLokasi = new ArrayList<String>();
    public static final String URL = "http://103.136.25.83:8000/api/";
    String imgDecodableString = "";
    public static Boolean isChange = false;
    public static Bitmap bitmapProfil = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        fotoprofil = findViewById(R.id.fotoprofil);
        namalengkap = findViewById(R.id.namalengkap);
        email = findViewById(R.id.email);
        jabatan = findViewById(R.id.jabatanSpinner);
        nohp = findViewById(R.id.nohp);
        lokasi = findViewById(R.id.lokasiSpinner);
        TextView skip = findViewById(R.id.skip);
        skip.setText("My Profile");
        skip.setVisibility(View.VISIBLE);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        getFotoProfil();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI api = retrofit.create(LoginAPI.class);
        Call<myProfileClassJson> call = api.getProfile(login.dataUser.getId());
        call.enqueue(new Callback<myProfileClassJson>() {
            @Override
            public void onResponse(Call<myProfileClassJson> call, Response<myProfileClassJson> response) {
                namalengkap.setText(response.body().getResult().getUsername());
                email.setText(response.body().getResult().getEmail());
                nohp.setText(response.body().getResult().getPhone());
                getJabatan(response.body().getResult().getIdRefJabatan());
                getLokasi(response.body().getResult().getIdRefLokasi());
                idJabatan = response.body().getResult().getIdRefJabatan();
                idLokasi = response.body().getResult().getIdRefLokasi();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<myProfileClassJson> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });

        fotoprofil.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(isStoragePermissionGranted()){
                    if(isReadStoragePermissionGranted()){
                        if(isCameraPermissionGranted()){
                            showPictureDialog();
                        }
                    }
                }
            }
        });
        TextView update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                uploadToServer(imgDecodableString);
            }
        });
        jabatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    idJabatan = String.valueOf(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        lokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    idLokasi = String.valueOf(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void getFotoProfil(){
        try{
            java.net.URL url = null;
            new Thread(new Runnable() {
                public void run() {
                    URL url = null;
                    try {
                        url = new URL(login.dataUser.getImg());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Bitmap a = null;
                    try {
                        Bitmap imageprofil = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fotoprofil.setImageBitmap(imageprofil);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }catch (Exception e){}
    }

    private void update(){
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait....");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI api = retrofit.create(LoginAPI.class);
        Call<updateProfileClassJson> call = api.updateProfileWithoutFoto(login.dataUser.getId(),namalengkap.getText().toString(),email.getText().toString(),nohp.getText().toString(),idJabatan,idLokasi);
        call.enqueue(new Callback<updateProfileClassJson>() {
            @Override
            public void onResponse(Call<updateProfileClassJson> call, Response<updateProfileClassJson> response) {
                try{
                    Log.d("lognya", response.raw().toString());
                    Log.d("lognyaa", response.message().toString());
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
//                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<updateProfileClassJson> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

    private void getJabatan(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI api = retrofit.create(LoginAPI.class);
        Call<listJabatanClassJson> call = api.getJabatan();
        call.enqueue(new Callback<listJabatanClassJson>() {
            @Override
            public void onResponse(Call<listJabatanClassJson> call, Response<listJabatanClassJson> response) {
                try{
                    listJabatanClassJson.Result result = response.body().getResult();
                    listJabatan.add("Jabatan");
                    for(int i =0;i < result.getListData().size();i++){
                        listJabatan.add(result.getListData().get(i).getName());
                    }



                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(my_profile.this, android.R.layout.simple_spinner_item, listJabatan);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    jabatan.setAdapter(dataAdapter);
                    jabatan.setSelection(Integer.parseInt(id));
                }catch (Exception e){
//                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<listJabatanClassJson> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLokasi(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI api = retrofit.create(LoginAPI.class);
        Call<listLokasiClassJson> call = api.getLokasi();
        call.enqueue(new Callback<listLokasiClassJson>() {
            @Override
            public void onResponse(Call<listLokasiClassJson> call, Response<listLokasiClassJson> response) {
                try{
                listLokasiClassJson.Result result = response.body().getResult();
                listLokasi.add("Lokasi");
                for(int i =0;i < result.getListData().size();i++){
                    listLokasi.add(result.getListData().get(i).getName());
                }



                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(my_profile.this, android.R.layout.simple_spinner_item, listLokasi);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lokasi.setAdapter(dataAdapter);
                lokasi.setSelection(Integer.parseInt(id));
                }catch (Exception e){
//                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<listLokasiClassJson> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
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
        progress.setMessage("Please Wait....");
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
        LoginAPI uploadAPIs = retrofit.create(LoginAPI.class);
        //Create a file object using file path
        File file = null;
        if(cameraFilePath != "" && cameraFilePath != null){
            try{
                file = image;
            }catch (Exception e){
            }
        }else{
            file = new File(filePath);
        }
        String emaill = email.getText().toString();
//        Call<updateProfileClassJson> call = api.updateProfile(login.dataUser.getId(),namalengkap.getText().toString(),email.getText().toString(),nohp.getText().toString(),idJabatan,idLokasi);
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("img", file.getName(), fileReqBody);

        RequestBody id_auth_user = RequestBody.create(MediaType.parse("text/plain"), login.dataUser.getId());
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), namalengkap.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emaill);
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"),nohp.getText().toString());
        RequestBody id_ref_jabatan = RequestBody.create(MediaType.parse("text/plain"),idJabatan);
        RequestBody id_ref_lokasi = RequestBody.create(MediaType.parse("text/plain"),idLokasi);
        Call<updateProfileClassJson> call = uploadAPIs.updateProfile(part, id_auth_user, username, email, phone, id_ref_jabatan, id_ref_lokasi);
        call.enqueue(new Callback<updateProfileClassJson>() {
            @Override
            public void onResponse(Call<updateProfileClassJson> call, Response<updateProfileClassJson> response) {
                Log.d(gallery.class.getSimpleName(), "message > " + response.message());
                Log.d(gallery.class.getSimpleName(), "raw > " + response.raw());
                Toast.makeText(getApplicationContext(),
                        "Success Upload Image",
                        Toast.LENGTH_SHORT).show();
//                prepare();
                login.dataUser.setImg(response.body().getResult().getImage());
                isChange = true;
                progress.dismiss();
            }
            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Error no internet connection!", Toast.LENGTH_SHORT).show();
                update();
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
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d(gallery.class.getSimpleName(), "imgDecodableString : " + imgDecodableString);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString, options);
                    bitmapProfil = bitmap;
                    fotoprofil.setImageBitmap(bitmap);
//                    uploadToServer(imgDecodableString);
                    break;
                case CAMERA_REQUEST_CODE:
                    imgDecodableString = cameraFilePath;
                    Log.d(gallery.class.getSimpleName(), "cameraFilePath : " + cameraFilePath);
                    BitmapFactory.Options optionss = new BitmapFactory.Options();
                    optionss.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmapp = BitmapFactory.decodeFile(cameraFilePath, optionss);
                    bitmapProfil = bitmapp;
                    fotoprofil.setImageBitmap(bitmapp);
//                    uploadToServer(cameraFilePath);
                    break;
            }
    }
}
