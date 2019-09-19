package com.inscripts.cometchatpulse.demo.Controller.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.StrictMode;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.inscripts.cometchatpulse.demo.Activity.CometChatActivity;
import com.inscripts.cometchatpulse.demo.Activity.LoginActivity;
import com.inscripts.cometchatpulse.demo.CometApplication;
import com.inscripts.cometchatpulse.demo.Contracts.LoginActivityContract;
import com.inscripts.cometchatpulse.demo.Contracts.StringContract;
import com.inscripts.cometchatpulse.demo.Controller.Retrofit.loginClassJson;
import com.inscripts.cometchatpulse.demo.Controller.browser.browser;
import com.inscripts.cometchatpulse.demo.Controller.intro.login;
import com.inscripts.cometchatpulse.demo.Presenters.LoginAcitivityPresenter;
import com.inscripts.cometchatpulse.demo.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class home extends AppCompatActivity implements View.OnClickListener,LoginActivityContract.LoginActivityView {
    GridView gridView;
    public Activity is_this;
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
    public static loginClassJson.Result dataUser;
    private LoginActivityContract.LoginActivityPresenter loginActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_walls);
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



        try {
            TextView nama = findViewById(R.id.txtHomeHeaderGreet2);
            nama.setText(dataUser.getFullname());
        }catch (Exception e){}
        try{
            TextView grup = findViewById(R.id.txtHomeHeaderLocation);
            grup.setText(dataUser.getGroupName());
        }catch (Exception e){}
        try{
            ImageView fotoprofil = findViewById(R.id.imgUserHome);

            URL url = null;
            try {
                url = new URL(dataUser.getImg());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                fotoprofil.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){}
        ImageView imgHomeMessage = findViewById(R.id.imgHomeMessage);
        imgHomeMessage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                CometChat.init(home.this, StringContract.AppDetails.APP_ID,new CometChat.CallbackListener<String>() {

                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(home.this, "SetUp Complete", Toast.LENGTH_SHORT).show();
//                        initComponentView();
                        loginActivityPresenter = new LoginAcitivityPresenter();
                        loginActivityPresenter.attach(home.this);
                        loginActivityPresenter.loginCheck();
                        loginActivityPresenter.Login(home.this,dataUser.getId());
                    }

                    @Override
                    public void onError(CometChatException e) {
                        Toast.makeText(home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(home.class.getSimpleName(), "onError: "+e.getMessage());
                    }

                });
            }
        });

    }

    @Override
    public void startCometChatActivity() {

        startActivity(new Intent(home.this, CometChatActivity.class));
//        finish();
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
