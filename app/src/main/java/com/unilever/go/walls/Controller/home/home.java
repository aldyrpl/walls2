package com.unilever.go.walls.Controller.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unilever.go.walls.Activity.CometChatActivity;
import com.unilever.go.walls.Contracts.LoginActivityContract;
import com.unilever.go.walls.Contracts.StringContract;
import com.unilever.go.walls.Controller.MainActivity;
import com.unilever.go.walls.Controller.Retrofit.WeatherAPI;
import com.unilever.go.walls.Controller.Retrofit.WeatherJson.JsonWeather;
import com.unilever.go.walls.Controller.Retrofit.loginClassJson;
import com.unilever.go.walls.Controller.SQL.DatabaseHandler;
import com.unilever.go.walls.Controller.browser.browser;
import com.unilever.go.walls.Controller.home.gallery.gallery;
import com.unilever.go.walls.Controller.intro.login;
//import com.unilever.go.walls.Controller.intro.profile;
//import com.unilever.go.walls.Presenters.LoginAcitivityPresenter;
import com.unilever.go.walls.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class home extends AppCompatActivity implements View.OnClickListener {
    GridView gridView;
    public static Activity is_this;
    static final String[] utility_category = new String[] {
            "Lighting", "AHU", "HWG", "Water Supply", "Air Compressor", "WTP"};

    static final String[] ammonia = new String[] {
            "NH3 Sensor", "Refrigerant System", "Windsock"};

    static final String[] electric_category = new String[] {
            "ATS", "Cotopaxy", "Powermeter"};

    static final String[] coldstore_category = new String[] {
            "Coldstore Plant", "Evaporator"};

    static final String[] mixingplant_category = new String[] {
            "Mixing Plant", "Glycol"};

    static final String[] cctv_category = new String[] {
            "Group 1", "Group 2", "Group 3","Group 4","Group 5","Group 6","Group 7","Group 8","Group 9","Group 10","Group 11","Group 12"};
    ImageView imgAmmonia;
    ImageView imgUtility;
    ImageView menuAktif;
    ImageView imgElectric;
    ImageView imgColdstore;
    ImageView imgMixingPlaint;
    LinearLayout scroll;
    TextView txtHomeCategory;
    ImageView imgCCTV;
    DatabaseHandler databaseHandler;
    public static String stateName;
    public static Bitmap imageprofil;
//    private LoginActivityContract.LoginActivityPresenter loginActivityPresenter;
    private FusedLocationProviderClient mFusedLocationClient;
    TextView grup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_walls);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1000);

        } else {
            getWeatherAndLocation();
        }
        databaseHandler = new DatabaseHandler(this);
        is_this = this;
        gridView = findViewById(R.id.gridHomeCategory);
        gridView.setFocusable(false);
        gridView.setAdapter(new UtilityCategoryGridAdapter(this, utility_category));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                FragmentManager fm = getSupportFragmentManager();
                browser alertDialog = browser.newInstance("Title");
                alertDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                alertDialog.show(fm, "fragment_alert");

                browser.value = utility_category[position];
            }
        });

        imgAmmonia = findViewById(R.id.imgAmmonia);
        imgAmmonia.setOnClickListener(this);
        imgUtility = findViewById(R.id.imgUtility);
        imgUtility.setOnClickListener(this);
        imgElectric = findViewById(R.id.imgElectric);
        imgElectric.setOnClickListener(this);
        imgColdstore = findViewById(R.id.imgColdstore);
        imgColdstore.setOnClickListener(this);
        imgMixingPlaint = findViewById(R.id.imgMixingPlaint);
        imgMixingPlaint.setOnClickListener(this);
        scroll = findViewById(R.id.linearLayout_gridtableLayout);
        txtHomeCategory = findViewById(R.id.txtHomeCategory);
        imgCCTV = findViewById(R.id.imgCCTV);
        imgCCTV.setOnClickListener(this);
        menuAktif = imgUtility;

        ImageView imgHomeCamera = findViewById(R.id.imgHomeCamera);
        imgHomeCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(is_this, gallery.class);
                startActivity(intent);
            }
        });

        ImageView imgHomeProfil = findViewById(R.id.imgHomeProfil);
        imgHomeProfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(is_this, profile.class);
                startActivity(intent);
//                CometChat.logout(new CometChat.CallbackListener<String>() {
//                    @Override
//                    public void onSuccess(String successMessage) {
//                    }
//                    @Override
//                    public void onError(CometChatException e) {
////                        Toast.makeText(home.this, "Logout Failed", Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        });
        try {
            TextView nama = findViewById(R.id.txtHomeHeaderGreet2);
            nama.setText(login.dataUser.getFullname());
        }catch (Exception e){}
        try{
            grup = findViewById(R.id.txtHomeHeaderLocation);
            grup.setText(login.dataUser.getGroupName());
        }catch (Exception e){}
        try{
            ImageView fotoprofil = findViewById(R.id.imgUserHome);

            URL url = null;
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
                        imageprofil = BitmapFactory.decodeStream(url.openConnection().getInputStream());
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
        ImageView imgHomeMessage = findViewById(R.id.imgHomeMessage);
        imgHomeMessage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(home.this, CometChatActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getWeatherAndLocation();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    void getWeatherAndLocation(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        //Set greeting
        String greeting = null;
        if(hour>= 12 && hour < 17){
            greeting = "Good Afternoon";
        } else if(hour >= 18 && hour < 21){
            greeting = "Good Evening";
        } else if(hour >= 21 && hour < 24){
            greeting = "Good Night";
        } else {
            greeting = "Good Morning";
        }

        TextView txtHomeHeaderGreet = findViewById(R.id.txtHomeHeaderGreet);
        txtHomeHeaderGreet.setText(greeting);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(login.URL_WEATHER_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    WeatherAPI api = retrofit.create(WeatherAPI.class);
                    Call<JsonWeather> call = api.getWeather(location.getLatitude(),location.getLongitude(),"metric",login.WEATHER_API_KEY);
                    call.enqueue(new Callback<JsonWeather>() {
                        @Override
                        public void onResponse(Call<JsonWeather> call, Response<JsonWeather> response) {

                            if (response.isSuccessful()) {
                                JsonWeather weatherList = response.body();
                                List<Address> addresses = null;
                                Geocoder geocoder = new Geocoder(home.this, Locale.getDefault());
                                try {
                                    addresses = geocoder.getFromLocation(
                                            location.getLatitude(),
                                            location.getLongitude(),
                                            // In this sample, get just a single address.
                                            1);
                                    Address address = addresses.get(0);
                                    String cityName = address.getLocality();
                                    stateName = address.getSubLocality();
                                    String countryName = address.getAddressLine(2);
                                    grup.setText(grup.getText() + " - " + stateName);

                                    Log.e(home.class.getSimpleName(), "cityName : " + cityName + "state : " + stateName);
                                }catch (Exception e){}
                                String stringBuilder =
                                        "lat : " + location.getLatitude() + "long : " + location.getLongitude() +
                                        "\nCountry: " +
                                        weatherList.getSys().getCountry() +
                                        "\n" +
                                        "Temperature: " +
                                        weatherList.getMain().getTemp() +
                                        "\n" +
                                        "Temperature(Min): " +
                                        weatherList.getMain().getTempMin() +
                                        "\n" +
                                        "Temperature(Max): " +
                                        weatherList.getMain().getTempMax() +
                                        "\n" +
                                        "Humidity: " +
                                        weatherList.getMain().getHumidity() +
                                        "\n" +
                                        "getWeather: " +
                                        weatherList.getWeather().get(0).getMain();
                                ImageView imgHomeWeather = findViewById(R.id.imgHomeWeather);
                                TextView txtHomeWeather = findViewById(R.id.txtHomeWeather);
                                txtHomeWeather.setText(weatherList.getWeather().get(0).getMain());
                                TextView txtHomeDate = findViewById(R.id.txtHomeDate);
                                TextView txtHomeDegree = findViewById(R.id.txtHomeDegree);
                                double a = weatherList.getMain().getTemp();
                                int temp = (int) a;
                                txtHomeDegree.setText(temp + "°");
                                SimpleDateFormat parseFormat = new SimpleDateFormat("E, d MMMM y", Locale.US);
                                Date date =new Date();
                                String s = parseFormat.format(date);

                                txtHomeDate.setText(s);
                                if(weatherList.getWeather().get(0).getMain() == "Thunderstorm"){
                                    imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.thunderstorm));
                                }else if(weatherList.getWeather().get(0).getMain() == "Drizzle"){
                                    imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.drizzle));
                                }else if(weatherList.getWeather().get(0).getMain() == "Rain"){
                                    imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.drizzle));
                                }else if(weatherList.getWeather().get(0).getMain() == "Snow"){
                                    imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.snow));
                                }else if(weatherList.getWeather().get(0).getMain() == "Clear"){
                                    imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.clear));
                                }else if(weatherList.getWeather().get(0).getMain() == "Clouds"){
                                    imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.clouds));
                                }else{
                                    imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.clear));
                                }


                            }
                        }
                        @Override
                        public void onFailure(Call<JsonWeather> call, Throwable t) {

                            Toast.makeText(home.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
//                            btnMasuk.setText("MASUK");
                        }
                    });
                }
            }
        });
    }


    public Drawable checkImageAktif(ImageView img){
        if(img == imgAmmonia){
            return this.getDrawable(R.drawable.icon_ammoria_1);
        }else if(img == imgUtility){
            return this.getDrawable(R.drawable.group_15);
        }else if(img == imgElectric){
            return this.getDrawable(R.drawable.stroke);
        }else if(img == imgColdstore){
            return this.getDrawable(R.drawable.group_25_2);
        }else if(img == imgMixingPlaint){
            return this.getDrawable(R.drawable.mixing_plant);
        }else if(img == imgCCTV){
            return this.getDrawable(R.drawable.cctv);
        }else{
            return null;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgAmmonia:

                gridView.setAdapter(new UtilityCategoryGridAdapter(this, ammonia));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        FragmentManager fm = getSupportFragmentManager();
                        browser alertDialog = browser.newInstance("Title");
                        alertDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                        alertDialog.show(fm, "fragment_alert");

                        browser.value = ammonia[position];
                    }
                });
                gridView.setNumColumns(ammonia.length);
                menuAktif.setBackground(this.getDrawable(R.drawable.oval_fill_grey));
                menuAktif.setImageDrawable(checkImageAktif(menuAktif));

                imgAmmonia.setBackground(this.getDrawable(R.drawable.oval_fill_red));
                imgAmmonia.setImageDrawable(this.getDrawable(R.drawable.group_15_white));
                if(menuAktif == imgMixingPlaint){
                    menuAktif.setColorFilter(null);
                }
                ViewGroup.LayoutParams params = scroll.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                int dp = (int) (getResources().getDimension(R.dimen.sell_size_frthsnd3_normal) / getResources().getDisplayMetrics().density);
                params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
                scroll.setLayoutParams(params);
                txtHomeCategory.setText("Ammonia Categories");
                menuAktif = imgAmmonia;
                break;
            case R.id.imgUtility:

                gridView.setAdapter(new UtilityCategoryGridAdapter(this, utility_category));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        FragmentManager fm = getSupportFragmentManager();
                        browser alertDialog = browser.newInstance("Title");
                        alertDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                        alertDialog.show(fm, "fragment_alert");

                        browser.value = utility_category[position];
                    }
                });
                gridView.setNumColumns(utility_category.length);
                imgUtility.setBackground(this.getDrawable(R.drawable.oval_fill_red));
                imgUtility.setImageDrawable(this.getDrawable(R.drawable.group_15_2));

                menuAktif.setBackground(this.getDrawable(R.drawable.oval_fill_grey));
                menuAktif.setImageDrawable(checkImageAktif(menuAktif));
                if(menuAktif == imgMixingPlaint){
                    menuAktif.setColorFilter(null);
                }
                ViewGroup.LayoutParams paramss = scroll.getLayoutParams();
                paramss.height = ViewGroup.LayoutParams.MATCH_PARENT;
                int dpp = (int) (getResources().getDimension(R.dimen.size_840dp_normal) / getResources().getDisplayMetrics().density);
                paramss.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpp, getResources().getDisplayMetrics());;
                scroll.setLayoutParams(paramss);
                txtHomeCategory.setText("Utility Categories");
                menuAktif = imgUtility;
                break;
            case R.id.imgElectric:

                gridView.setAdapter(new UtilityCategoryGridAdapter(this, electric_category));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        FragmentManager fm = getSupportFragmentManager();
                        browser alertDialog = browser.newInstance("Title");
                        alertDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                        alertDialog.show(fm, "fragment_alert");

                        browser.value = electric_category[position];
                    }
                });
                gridView.setNumColumns(electric_category.length);
                imgElectric.setBackground(this.getDrawable(R.drawable.oval_fill_red));
                imgElectric.setImageDrawable(this.getDrawable(R.drawable.electric_white));

                menuAktif.setBackground(this.getDrawable(R.drawable.oval_fill_grey));
                menuAktif.setImageDrawable(checkImageAktif(menuAktif));
                if(menuAktif == imgMixingPlaint){
                    menuAktif.setColorFilter(null);
                }
                ViewGroup.LayoutParams paramsss = scroll.getLayoutParams();
                paramsss.height = ViewGroup.LayoutParams.MATCH_PARENT;
                int dppp = (int) (getResources().getDimension(R.dimen.sell_size_frthsnd3_normal) / getResources().getDisplayMetrics().density);
                paramsss.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dppp, getResources().getDisplayMetrics());;
                scroll.setLayoutParams(paramsss);
                txtHomeCategory.setText("Electric Categories");
                menuAktif = imgElectric;
                break;
            case R.id.imgColdstore:

                gridView.setAdapter(new UtilityCategoryGridAdapter(this, coldstore_category));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        FragmentManager fm = getSupportFragmentManager();
                        browser alertDialog = browser.newInstance("Title");
                        alertDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                        alertDialog.show(fm, "fragment_alert");

                        browser.value = coldstore_category[position];
                    }
                });
                gridView.setNumColumns(coldstore_category.length);
                imgColdstore.setBackground(this.getDrawable(R.drawable.oval_fill_red));
                imgColdstore.setImageDrawable(this.getDrawable(R.drawable.coldstore_white));

                menuAktif.setBackground(this.getDrawable(R.drawable.oval_fill_grey));
                menuAktif.setImageDrawable(checkImageAktif(menuAktif));
                if(menuAktif == imgMixingPlaint){
                    menuAktif.setColorFilter(null);
                }
                ViewGroup.LayoutParams paramssss = scroll.getLayoutParams();
                paramssss.height = ViewGroup.LayoutParams.MATCH_PARENT;
                int dpppp = (int) (getResources().getDimension(R.dimen.sell_size_twthsvtyfv_normal) / getResources().getDisplayMetrics().density);
                paramssss.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpppp, getResources().getDisplayMetrics());;
                scroll.setLayoutParams(paramssss);
                txtHomeCategory.setText("Coldstore Categories");
                menuAktif = imgColdstore;
                break;
            case R.id.imgMixingPlaint:

                gridView.setAdapter(new UtilityCategoryGridAdapter(this, mixingplant_category));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        FragmentManager fm = getSupportFragmentManager();
                        browser alertDialog = browser.newInstance("Title");
                        alertDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                        alertDialog.show(fm, "fragment_alert");

                        browser.value = mixingplant_category[position];
                    }
                });
                gridView.setNumColumns(mixingplant_category.length);
                imgMixingPlaint.setBackground(this.getDrawable(R.drawable.oval_fill_red));
                imgMixingPlaint.setImageDrawable(this.getDrawable(R.drawable.mixing_plant));
                imgMixingPlaint.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

                menuAktif.setBackground(this.getDrawable(R.drawable.oval_fill_grey));
                menuAktif.setImageDrawable(checkImageAktif(menuAktif));

                ViewGroup.LayoutParams paramsssss = scroll.getLayoutParams();
                paramsssss.height = ViewGroup.LayoutParams.MATCH_PARENT;
                int dppppp = (int) (getResources().getDimension(R.dimen.sell_size_twthsvtyfv_normal) / getResources().getDisplayMetrics().density);
                paramsssss.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dppppp, getResources().getDisplayMetrics());;
                scroll.setLayoutParams(paramsssss);
                txtHomeCategory.setText("Mixing Plant Categories");
                menuAktif = imgMixingPlaint;
                break;
            case R.id.imgCCTV:

                gridView.setAdapter(new UtilityCategoryGridAdapter(this, cctv_category));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        FragmentManager fm = getSupportFragmentManager();
                        browser alertDialog = browser.newInstance("Title");
                        alertDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                        alertDialog.show(fm, "fragment_alert");

                        browser.value = cctv_category[position];
                    }
                });
                gridView.setNumColumns(cctv_category.length);
                imgCCTV.setBackground(this.getDrawable(R.drawable.oval_fill_red));
                imgCCTV.setImageDrawable(this.getDrawable(R.drawable.cctv));

                menuAktif.setBackground(this.getDrawable(R.drawable.oval_fill_grey));
                menuAktif.setImageDrawable(checkImageAktif(menuAktif));

                ViewGroup.LayoutParams paramssssss = scroll.getLayoutParams();
                paramssssss.height = ViewGroup.LayoutParams.MATCH_PARENT;
                int dpppppp = (int) (getResources().getDimension(R.dimen.size_1680_normal) / getResources().getDisplayMetrics().density);
                paramssssss.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpppppp, getResources().getDisplayMetrics());;
                scroll.setLayoutParams(paramssssss);
                txtHomeCategory.setText("CCTV Categories");
                menuAktif = imgCCTV;
                break;
        }
    }

    public class UtilityCategoryGridAdapter extends BaseAdapter {
        private Context context;
        private final String[] categoryValues;

        public UtilityCategoryGridAdapter(Context context, String[] mobileValues) {
            this.context = context;
            this.categoryValues = mobileValues;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(context);

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.home_grid_item, null);

                // set value into textview
                TextView textView = (TextView) gridView
                        .findViewById(R.id.title);
                textView.setText(categoryValues[position]);

                // set image based on selected text
                ImageView imageView = (ImageView) gridView
                        .findViewById(R.id.img);

                String mobile = categoryValues[position];
                //Utility
                if (mobile.equals("Lighting")) {
                    imageView.setImageResource(R.drawable.group_9);
                } else if (mobile.equals("AHU")) {
                    imageView.setImageResource(R.drawable.group_5_copy_2);
                } else if (mobile.equals("HWG")) {
                    imageView.setImageResource(R.drawable.group_12);
                } else if (mobile.equals("Water Supply")) {
                    imageView.setImageResource(R.drawable.group_13);
                } else if (mobile.equals("Air Compressor")) {
                    imageView.setImageResource(R.drawable.group_10);
                }else if (mobile.equals("WTP")) {
                    imageView.setImageResource(R.drawable.wtp);
                }
                //Ammonia
//                "NH3 Sensor", "Refrigerant System", "Windsock"};
                if (mobile.equals("NH3 Sensor")) {
                    imageView.setImageResource(R.drawable.group_7);
                } else if (mobile.equals("Refrigerant System")) {
                    imageView.setImageResource(R.drawable.group_24);
                } else if (mobile.equals("Windsock")) {
                    imageView.setImageResource(R.drawable.group_8);
                }
                //Electric
                //"ATS", "Cotopaxy", "Powermeter"};
                if (mobile.equals("ATS")) {
                    imageView.setImageResource(R.drawable.ats);
                } else if (mobile.equals("Cotopaxy")) {
                    imageView.setImageResource(R.drawable.coto);
                } else if (mobile.equals("Powermeter")) {
                    imageView.setImageResource(R.drawable.powermeter);
                }
                //Coldstore
                // "Coldstore Plant", "Evaporator"};
                if (mobile.equals("Coldstore Plant")) {
                    imageView.setImageResource(R.drawable.ammonia_system);
                } else if (mobile.equals("Evaporator")) {
                    imageView.setImageResource(R.drawable.evaporator);
                }
                //Mixingplant
                //"Mixing Plant", "Glycol"};
                if (mobile.equals("Mixing Plant")) {
                    imageView.setImageResource(R.drawable.mixingplant);
                } else if (mobile.equals("Glycol")) {
                    imageView.setImageResource(R.drawable.glycol);
                }
                //CCTV
                if (mobile.equals("Group 1")) {
                    imageView.setImageResource(R.drawable.cctv);
                } else if (mobile.equals("Group 2")) {
                    imageView.setImageResource(R.drawable.cctv);
                }else if (mobile.equals("Group 3")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Group 4")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Group 5")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Group 6")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Group 7")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Group 8")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Group 9")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Group 10")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Group 11")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Group 12")) {
                    imageView.setImageResource(R.drawable.cctv);
                }


            } else {
                gridView = (View) convertView;
            }

            return gridView;
        }

        @Override
        public int getCount() {
            return categoryValues.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
