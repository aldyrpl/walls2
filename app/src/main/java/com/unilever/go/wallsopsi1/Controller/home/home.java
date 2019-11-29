package com.unilever.go.wallsopsi1.Controller.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.unilever.go.wallsopsi1.Activity.CometChatActivity;
import com.unilever.go.wallsopsi1.Controller.Retrofit.WeatherAPI;
import com.unilever.go.wallsopsi1.Controller.Retrofit.WeatherJson.JsonWeather;
import com.unilever.go.wallsopsi1.Controller.SQL.DatabaseHandler;
import com.unilever.go.wallsopsi1.Controller.browser.browser;
import com.unilever.go.wallsopsi1.Controller.browser.browser2;
import com.unilever.go.wallsopsi1.Controller.home.gallery.gallery;
import com.unilever.go.wallsopsi1.Controller.home.remindme.MainActivity;
import com.unilever.go.wallsopsi1.Controller.home.my_profile;
import com.unilever.go.wallsopsi1.Controller.intro.login;
import com.unilever.go.wallsopsi1.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.unilever.go.wallsopsi1.Controller.intro.profile;
//import com.unilever.go.wallsopsi1.Presenters.LoginAcitivityPresenter;
//import org.mozilla.geckoview.GeckoRuntime;
//import org.mozilla.geckoview.GeckoSession;

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
            "CS3 AREA", "Area Parking truck", "CS3 Gudang","CS3 Despatch","Ageing Mixing","CS2 & Eng PH","Loading CS","Utility & Mixing","PH & RMS","Loading Luar CS","Packing Hall","Perimeter Walls"};
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
    private static String sPackageNameToUse;
    private static final String ACTION_CUSTOM_TABS_CONNECTION =
            "android.support.customtabs.action.CustomTabsService";

    public ArrayList<String> idCategory = new ArrayList<String>();;
    public ArrayList<String> idEventAPI = new ArrayList<String>();;

    public static String stateName;
    public static final String URL = "http://103.136.25.83:8000/api/";
    public static Bitmap imageprofil;
//    private LoginActivityContract.LoginActivityPresenter loginActivityPresenter;
    private FusedLocationProviderClient mFusedLocationClient;
    String manufacturer = Build.MANUFACTURER;
    TextView grup;
    CustomTabsIntent.Builder builder;
    CustomTabsIntent customTabsIntent;

    static final String STABLE_PACKAGE = "com.android.chrome";
    static final String BETA_PACKAGE = "com.chrome.beta";
    static final String DEV_PACKAGE = "com.chrome.dev";
    static final String LOCAL_PACKAGE = "com.google.android.apps.chrome";
    private static final String EXTRA_CUSTOM_TABS_KEEP_ALIVE =
            "android.support.customtabs.extra.KEEP_ALIVE";
    String tipe = "3";
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
        builder = new CustomTabsIntent.Builder();
        customTabsIntent = builder.build();
        String packageName = getPackageNameToUse(this);
        customTabsIntent.intent.setPackage(packageName);
        databaseHandler = new DatabaseHandler(this);
        is_this = this;
        gridView = findViewById(R.id.gridHomeCategory);
        gridView.setFocusable(false);
        gridView.setAdapter(new UtilityCategoryGridAdapter(this, utility_category));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(tipe == "1") {
                    if(manufacturer.contains("samsung")) {
                        try {
                            Log.d("ini apa", manufacturer);
                            Intent intent = new Intent(is_this, browser2.class);
                            startActivity(intent);

                            browser2.value = utility_category[position];
                        }catch (Exception e){

                        }
                    }else{
                        Log.d("ini apaa", manufacturer);
                        FragmentManager fm = getSupportFragmentManager(); // returns from support lib
                        DialogFragment dlg = new browser();
                        dlg.show(fm, "tags"); // wants core...
                        dlg.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                        browser.value = utility_category[position];
                    }
                }
                else if(tipe == "2"){
                    Intent intent = new Intent(is_this, browser2.class);
                    startActivity(intent);

                    browser2.value = utility_category[position];
                }else {
                    customTabsIntent.launchUrl(is_this, Uri.parse(ConvertUrl(utility_category[position])));
                }
            }
        });
        Log.d("manufacture ", manufacturer);
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

        ImageView imgHomeCalendar = findViewById(R.id.imgHomeCalendar);
        imgHomeCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(is_this, MainActivity.class);
                startActivity(intent);
            }
        });
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

        event();
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

   public String ConvertUrl(String value){
        String url = "";
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

        return url;
    }

    public static String getPackageNameToUse(Context context) {
        if (sPackageNameToUse != null) return sPackageNameToUse;

        PackageManager pm = context.getPackageManager();
        // Get default VIEW intent handler.
        Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));
        ResolveInfo defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0);
        String defaultViewHandlerPackageName = null;
        if (defaultViewHandlerInfo != null) {
            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName;
        }

        // Get all apps that can handle VIEW intents.
        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, 0);
        List<String> packagesSupportingCustomTabs = new ArrayList<>();
        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(info.activityInfo.packageName);
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName);
            }
        }

        // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
        // and service calls.
        if (packagesSupportingCustomTabs.isEmpty()) {
            sPackageNameToUse = null;
        } else if (packagesSupportingCustomTabs.size() == 1) {
            sPackageNameToUse = packagesSupportingCustomTabs.get(0);
        } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
                && !hasSpecializedHandlerIntents(context, activityIntent)
                && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {
            sPackageNameToUse = defaultViewHandlerPackageName;
        } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
            sPackageNameToUse = STABLE_PACKAGE;
        } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
            sPackageNameToUse = BETA_PACKAGE;
        } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
            sPackageNameToUse = DEV_PACKAGE;
        } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
            sPackageNameToUse = LOCAL_PACKAGE;
        }
        return sPackageNameToUse;
    }

    private static boolean hasSpecializedHandlerIntents(Context context, Intent intent) {
        try {
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> handlers = pm.queryIntentActivities(
                    intent,
                    PackageManager.GET_RESOLVED_FILTER);
            if (handlers == null || handlers.size() == 0) {
                return false;
            }
            for (ResolveInfo resolveInfo : handlers) {
                IntentFilter filter = resolveInfo.filter;
                if (filter == null) continue;
                if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue;
                if (resolveInfo.activityInfo == null) continue;
                return true;
            }
        } catch (RuntimeException e) {
            Log.e("hasspecialhandler", "Runtime exception while getting specialized handlers");
        }
        return false;
    }

    void getWeatherAndLocation(){
        try {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int hour = cal.get(Calendar.HOUR_OF_DAY);

            //Set greeting
            String greeting = null;
            if (hour >= 12 && hour < 17) {
                greeting = "Good Afternoon";
            } else if (hour >= 18 && hour < 21) {
                greeting = "Good Evening";
            } else if (hour >= 21 && hour < 24) {
                greeting = "Good Night";
            } else {
                greeting = "Good Morning";
            }

            TextView txtHomeHeaderGreet = findViewById(R.id.txtHomeHeaderGreet);
            txtHomeHeaderGreet.setText(greeting);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(login.URL_WEATHER_API)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        WeatherAPI api = retrofit.create(WeatherAPI.class);
                        Call<JsonWeather> call = api.getWeather(location.getLatitude(), location.getLongitude(), "metric", login.WEATHER_API_KEY);
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
                                    } catch (Exception e) {
                                    }
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
                                    Date date = new Date();
                                    String s = parseFormat.format(date);

                                    txtHomeDate.setText(s);
                                    if (weatherList.getWeather().get(0).getMain() == "Thunderstorm") {
                                        imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.thunderstorm));
                                    } else if (weatherList.getWeather().get(0).getMain() == "Drizzle") {
                                        imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.drizzle));
                                    } else if (weatherList.getWeather().get(0).getMain() == "Rain") {
                                        imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.drizzle));
                                    } else if (weatherList.getWeather().get(0).getMain() == "Snow") {
                                        imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.snow));
                                    } else if (weatherList.getWeather().get(0).getMain() == "Clear") {
                                        imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.clear));
                                    } else if (weatherList.getWeather().get(0).getMain() == "Clouds") {
                                        imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.clouds));
                                    } else {
                                        imgHomeWeather.setImageDrawable(home.this.getDrawable(R.drawable.clear));
                                    }


                                }
                            }

                            @Override
                            public void onFailure(Call<JsonWeather> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }catch (Exception e){

        }
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
    public void onResume(){
        super.onResume();
        ImageView fotoprofil = findViewById(R.id.imgUserHome);
        if(my_profile.isChange == true){
            new Thread(new Runnable() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fotoprofil.setImageBitmap(my_profile.bitmapProfil);
                            my_profile.isChange = false;
                        }
                    });

                }
            }).start();
        }
//
//        try{
//            browser2.runtime = GeckoRuntime.create(this);
//        }catch (Exception e){
//
//        }
//
//        try{
//            browser2.session = new GeckoSession();
//        }catch (Exception e){
//
//        }
//        try {
//            if (!browser2.session.isOpen()) {
//                browser2.session.open(browser2.runtime);
//            }
//        }catch (Exception e){
//
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgAmmonia:

                gridView.setAdapter(new UtilityCategoryGridAdapter(this, ammonia));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        if(tipe == "1") {
                            if(manufacturer.contains("samsung")) {
                                Intent intent = new Intent(is_this, browser2.class);
                                startActivity(intent);

                                browser2.value = ammonia[position];
                            }else{
                                FragmentManager fm = getSupportFragmentManager(); // returns from support lib
                                DialogFragment dlg = new browser();
                                dlg.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                                dlg.show(fm, "tags"); // wants core...
                                browser.value = ammonia[position];
                            }
                        }else if(tipe == "2"){
                            Intent intent = new Intent(is_this, browser2.class);
                            startActivity(intent);

                            browser2.value = ammonia[position];
                        }
                        else {
                            customTabsIntent.launchUrl(is_this, Uri.parse(ConvertUrl(ammonia[position])));
                        }
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
                        if(tipe == "1") {
                            if(manufacturer.contains("samsung")) {
                                try{
                                    Intent intent = new Intent(is_this, browser2.class);
                                    startActivity(intent);

                                    browser2.value = utility_category[position];
                                }catch (Exception e){

                                }
                            }else {
                                FragmentManager fm = getSupportFragmentManager(); // returns from support lib
                                DialogFragment dlg = new browser();
                                dlg.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                                dlg.show(fm, "tags"); // wants core...
                                browser.value = utility_category[position];
                            }
                        }else if(tipe == "2"){
                            Intent intent = new Intent(is_this, browser2.class);
                            startActivity(intent);

                            browser2.value = utility_category[position];
                        }else {
                            customTabsIntent.launchUrl(is_this, Uri.parse(ConvertUrl(utility_category[position])));
                        }
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
                        if(tipe == "1") {
                            if (manufacturer.contains("samsung")) {
                                try{
                                    Intent intent = new Intent(is_this, browser2.class);
                                    startActivity(intent);

                                    browser2.value = electric_category[position];
                                }catch (Exception e){

                                }
                            } else {
                                FragmentManager fm = getSupportFragmentManager(); // returns from support lib
                                DialogFragment dlg = new browser();
                                dlg.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                                dlg.show(fm, "tags"); // wants core...
                                browser.value = electric_category[position];
                            }
                        }else if(tipe == "2"){
                            Intent intent = new Intent(is_this, browser2.class);
                            startActivity(intent);

                            browser2.value = electric_category[position];
                        }else {
                            customTabsIntent.launchUrl(is_this, Uri.parse(ConvertUrl(electric_category[position])));
                        }
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
                        if(tipe == "1") {
                            if(manufacturer.contains("samsung")) {
                                try{
                                    Intent intent = new Intent(is_this, browser2.class);
                                    startActivity(intent);

                                    browser2.value = coldstore_category[position];
                                }catch (Exception e){

                                }
                            }else{
                                FragmentManager fm = getSupportFragmentManager(); // returns from support lib
                                DialogFragment dlg = new browser();
                                dlg.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                                dlg.show(fm, "tags"); // wants core...
                                browser.value = coldstore_category[position];
                            }
                        }else if(tipe == "2"){
                            Intent intent = new Intent(is_this, browser2.class);
                            startActivity(intent);

                            browser2.value = coldstore_category[position];
                        }else {
                            customTabsIntent.launchUrl(is_this, Uri.parse(ConvertUrl(coldstore_category[position])));
                        }
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
                        if(tipe == "1") {
                            if(manufacturer.contains("samsung")) {
                                try{
                                    Intent intent = new Intent(is_this, browser2.class);
                                    startActivity(intent);

                                    browser2.value = mixingplant_category[position];
                                }catch (Exception e){

                                }
                            }else{
                                FragmentManager fm = getSupportFragmentManager(); // returns from support lib
                                DialogFragment dlg = new browser();
                                dlg.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                                dlg.show(fm, "tags"); // wants core...
                                browser.value = mixingplant_category[position];
                            }
                        }else if(tipe == "2"){
                            Intent intent = new Intent(is_this, browser2.class);
                            startActivity(intent);

                            browser2.value = mixingplant_category[position];
                        }else {
                            customTabsIntent.launchUrl(is_this, Uri.parse(ConvertUrl(mixingplant_category[position])));
                        }
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
                        if(tipe == "1") {
                            if (manufacturer.contains("samsung")) {
                                try{
                                    Intent intent = new Intent(is_this, browser2.class);
                                    startActivity(intent);

                                    browser2.value = cctv_category[position];
                                }catch (Exception e){

                                }
                            } else {
                                FragmentManager fm = getSupportFragmentManager(); // returns from support lib
                                DialogFragment dlg = new browser();
                                dlg.show(fm, "tags"); // wants core...
                                dlg.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                                browser.value = cctv_category[position];
                            }
                        }else if(tipe == "2"){
                            Intent intent = new Intent(is_this, browser2.class);
                            startActivity(intent);

                            browser2.value = cctv_category[position];
                        }else {
                            customTabsIntent.launchUrl(is_this, Uri.parse(ConvertUrl(cctv_category[position])));
                        }
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
                if (mobile.equals("CS3 AREA")) {
                    imageView.setImageResource(R.drawable.cctv);
                } else if (mobile.equals("Area Parking truck")) {
                    imageView.setImageResource(R.drawable.cctv);
                }else if (mobile.equals("CS3 Gudang")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("CS3 Despatch")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Ageing Mixing")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("CS2 & Eng PH")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Loading CS")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Utility & Mixing")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("PH & RMS")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Loading Luar CS")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Packing Hall")) {
                    imageView.setImageResource(R.drawable.cctv);
                }
                else if (mobile.equals("Perimeter Walls")) {
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


    void event(){
//        AlarmReceiver mAlarmReceiver = new AlarmReceiver();
        try {
            if (isReceiveBootPermission()) {

            }
        }catch (Exception e){

        }

    }

    public  boolean isReceiveBootPermission() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.RECEIVE_BOOT_COMPLETED)
                        == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Log.v(gallery.class.getSimpleName(), "Permission is granted");
                        MainActivity reminder = new MainActivity();
                        reminder.getEventFromAPI(this);
                    }catch (Exception e){

                    }
                    return true;
                } else {
                    try {
                        Log.v(gallery.class.getSimpleName(), "Permission is revoked");
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, 1);
                        finish();
                    }catch (Exception e){

                    }
                    return false;
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v(gallery.class.getSimpleName(), "Permission is granted");
                return true;
            }
        }catch (Exception e){
            return  false;
        }
    }

}
