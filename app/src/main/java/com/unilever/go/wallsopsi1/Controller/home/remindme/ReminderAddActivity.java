/*
 * Copyright 2015 Blanyal D'Souza.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.unilever.go.wallsopsi1.Controller.home.remindme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.unilever.go.wallsopsi1.Controller.Retrofit.EventAPI;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.EventClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.eventCategoryJsonClass;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.listemail;
import com.unilever.go.wallsopsi1.Controller.intro.login;
import com.unilever.go.wallsopsi1.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ReminderAddActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener,
        AdapterView.OnItemSelectedListener
        {

    private Toolbar mToolbar;
    private EditText mTitleText;
    private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
    private FloatingActionButton mFAB1;
    private FloatingActionButton mFAB2;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private long mRepeatTime;
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeat;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;

    public static final String URL = "http://103.136.25.83:8000/api/";

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_REPEAT = "repeat_key";
    private static final String KEY_REPEAT_NO = "repeat_no_key";
    private static final String KEY_REPEAT_TYPE = "repeat_type_key";
    private static final String KEY_ACTIVE = "active_key";

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;
    AppCompatMultiAutoCompleteTextView to;
    eventCategoryJsonClass.Result eventResult;
    public ArrayList<String> categoryName = new ArrayList<String>();
    Spinner spinner;
    String idCategory = "";
    ArrayList<String> emails = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        getlistemail();
        // Initialize Views
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleText = (EditText) findViewById(R.id.reminder_title);
        mDateText = (TextView) findViewById(R.id.set_date);
        mTimeText = (TextView) findViewById(R.id.set_time);
        mRepeatText = (TextView) findViewById(R.id.set_repeat);
        mRepeatNoText = (TextView) findViewById(R.id.set_repeat_no);
        mRepeatTypeText = (TextView) findViewById(R.id.set_repeat_type);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred1);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred2);



        to = (AppCompatMultiAutoCompleteTextView)
                findViewById(R.id.to);

        to.setThreshold(1);
        to.setTokenizer(new AppCompatMultiAutoCompleteTextView.CommaTokenizer());
        // Setup Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_activity_add_reminder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Initialize default values
        mActive = "true";
        mRepeat = "true";
        mRepeatNo = Integer.toString(1);
        mRepeatType = "Hour";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        Log.d("mHour", String.valueOf(Calendar.HOUR_OF_DAY));
        Log.d("mMinute", String.valueOf(Calendar.MINUTE));
        Log.d("mYear", String.valueOf(Calendar.YEAR));
        Log.d("mMonth", String.valueOf(Calendar.MONTH));
        Log.d("mDay", String.valueOf(Calendar.DATE));
        // Setup Reminder Title EditText
        mTitleText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Setup TextViews using reminder values
        mDateText.setText(mDate);
        mTimeText.setText(mTime);
        mRepeatNoText.setText(mRepeatNo);
        mRepeatTypeText.setText(mRepeatType);
        mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");

        // To save state on device rotation
        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            mTitleText.setText(savedTitle);
            mTitle = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            mTimeText.setText(savedTime);
            mTime = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            mDateText.setText(savedDate);
            mDate = savedDate;

            String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
            mRepeatText.setText(saveRepeat);
            mRepeat = saveRepeat;

            String savedRepeatNo = savedInstanceState.getString(KEY_REPEAT_NO);
            mRepeatNoText.setText(savedRepeatNo);
            mRepeatNo = savedRepeatNo;

            String savedRepeatType = savedInstanceState.getString(KEY_REPEAT_TYPE);
            mRepeatTypeText.setText(savedRepeatType);
            mRepeatType = savedRepeatType;

            mActive = savedInstanceState.getString(KEY_ACTIVE);
        }

        // Setup up active buttons
        if (mActive.equals("false")) {
            mFAB1.setVisibility(View.VISIBLE);
            mFAB2.setVisibility(View.GONE);

        } else if (mActive.equals("true")) {
            mFAB1.setVisibility(View.GONE);
            mFAB2.setVisibility(View.VISIBLE);
        }
//        spinner = (Spinner) findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener(this);
//        getCategory();

    }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // On selecting a spinner item
//                String item = parent.getItemAtPosition(position).toString();
//
//                // Showing selected spinner item
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                if((position - 1) >= 0) {
                    idCategory = eventResult.getListData().get(position - 1).getId();
                }

            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

    // To save state on device rotation
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putCharSequence(KEY_TITLE, mTitleText.getText());
            outState.putCharSequence(KEY_TIME, mTimeText.getText());
            outState.putCharSequence(KEY_DATE, mDateText.getText());
            outState.putCharSequence(KEY_REPEAT, mRepeatText.getText());
            outState.putCharSequence(KEY_REPEAT_NO, mRepeatNoText.getText());
            outState.putCharSequence(KEY_REPEAT_TYPE, mRepeatTypeText.getText());
            outState.putCharSequence(KEY_ACTIVE, mActive);
        }catch (Exception e){

        }
    }

    // On clicking Time picker
    public void setTime(View v){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    // On clicking Date picker
    public void setDate(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        mTimeText.setText(mTime);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        mDateText.setText(mDate);
    }

    // On clicking the active button
    public void selectFab1(View v) {
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred1);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred2);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "true";
    }

    // On clicking the inactive button
    public void selectFab2(View v) {
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred2);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred1);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "false";
    }

    // On clicking the repeat switch
    public void onSwitchRepeat(View view) {
        boolean on = ((Switch) view).isChecked();
        if (on) {
            mRepeat = "true";
            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
        } else {
            mRepeat = "false";
            mRepeatText.setText(R.string.repeat_off);
        }
    }

    // On clicking repeat type button
    public void selectRepeatType(View v){
        final String[] items = new String[5];

        items[0] = "Minute";
        items[1] = "Hour";
        items[2] = "Day";
        items[3] = "Week";
        items[4] = "Month";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Type");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mRepeatType = items[item];
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // On clicking repeat interval button
    public void setRepeatNo(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Number");

        // Create EditText box to input repeat number
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (input.getText().toString().length() == 0) {
                            mRepeatNo = Integer.toString(1);
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                        }
                        else {
                            mRepeatNo = input.getText().toString().trim();
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                        }
                    }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

    // On clicking the save button
    public void saveReminder(){
        ReminderDatabase rb = new ReminderDatabase(this);
        String email = to.getText().toString().replace(" ","");
        // Creating Reminder


//        int ID = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, mRepeatNo, mRepeatType, mActive));
//
//        // Set up calender for creating the notification

        Log.d("masuk truee mTitle", String.valueOf(mTitle));
        Log.d("masuk truee mDate", String.valueOf(mDate));
        Log.d("masuk truee mTime", String.valueOf(mTime));

        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        Log.d("yihuh ", String.valueOf(mCalendar.getTime()));
        // Check repeat type
        if (mRepeatType.equals("Minute")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
        } else if (mRepeatType.equals("Hour")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
        } else if (mRepeatType.equals("Day")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
        } else if (mRepeatType.equals("Week")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
        } else if (mRepeatType.equals("Month")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
        }

////        // Create a new notification
//        if (mActive.equals("true")) {
//            if (mRepeat.equals("true")) {
//                Log.d("repeatga ", String.valueOf(mRepeatTime));
////                new AlarmReceiver().setRepeatAlarm(getApplicationContext(), mCalendar, ID, mRepeatTime);
//                new AlarmReceiver().setAlarm(getApplicationContext(), mCalendar, 9);
//            } else if (mRepeat.equals("false")) {
//                new AlarmReceiver().setAlarm(getApplicationContext(), mCalendar, 9);
//            }
//        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EventAPI api = retrofit.create(EventAPI.class);
        Log.d(ReminderAddActivity.class.getSimpleName(),"title = " + mTitle);
        Log.d(ReminderAddActivity.class.getSimpleName(),"mDate = " + mDate);
        Log.d(ReminderAddActivity.class.getSimpleName(),"mTime = " + mTime);
        Log.d(ReminderAddActivity.class.getSimpleName(),"mActive = " + mActive);
        Log.d(ReminderAddActivity.class.getSimpleName(),"login.dataUser.getId() = " + login.dataUser.getId());
//        Log.d(ReminderAddActivity.class.getSimpleName(),"mRepeatType = " + mRepeatType);
//        Log.d(ReminderAddActivity.class.getSimpleName(),"mActive = " + mActive);
        if(mActive.equals("true")){
            mActive = "1";
        }else{
            mActive = "0";
        }
        Call<EventClassJson> call = api.addEvent(email,"1", mTitle,mDate,mTime,mActive,login.dataUser.getId());
        call.enqueue(new Callback<EventClassJson>() {
            @Override
            public void onResponse(Call<EventClassJson> call, Response<EventClassJson> response) {

                try {
                    if (response.body().getStatus() == false) {
                        Toast.makeText(getApplicationContext(), response.body().getError().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Saved",
                                Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventClassJson> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        // Create toast to confirm new reminder



    }

//    private void getCategory(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        EventAPI api = retrofit.create(EventAPI.class);
//        Call<eventCategoryJsonClass> call = api.getEvent();
//        call.enqueue(new Callback<eventCategoryJsonClass>() {
//            @Override
//            public void onResponse(Call<eventCategoryJsonClass> call, Response<eventCategoryJsonClass> response) {
//                eventResult = response.body().getResult();
//                categoryName.add("Select Task");
//                for(int i =0;i < eventResult.getListData().size();i++){
//                    categoryName.add(eventResult.getListData().get(i).getName());
//                }
//
//
//
////                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ReminderAddActivity.this, android.R.layout.simple_spinner_item, categoryName);
////                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                spinner.setAdapter(dataAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<eventCategoryJsonClass> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
//                return;
////                finish();
//            }
//        });
//    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }

    // On clicking menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // On clicking the back arrow
            // Discard any changes
            case android.R.id.home:
                onBackPressed();
                return true;

            // On clicking save reminder button
            // Update reminder
            case R.id.save_reminder:
                mTitleText.setText(mTitle);

                if (mTitleText.getText().toString().length() == 0)
                    mTitleText.setError("Reminder Title cannot be blank!");

                else {
                    saveReminder();
                }
                return true;

            // On clicking discard reminder button
            // Discard any changes
            case R.id.discard_reminder:
                Toast.makeText(getApplicationContext(), "Discarded",
                        Toast.LENGTH_SHORT).show();

                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void getlistemail(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EventAPI api = retrofit.create(EventAPI.class);
        Call<listemail> call = api.getListEmail();
        call.enqueue(new Callback<listemail>() {
            @Override
            public void onResponse(Call<listemail> call, Response<listemail> response) {
                String[] emailnya = response.body().getResult().getListData().split(",");
                for(int i=0;i < emailnya.length;i++){
                    emails.add(emailnya[i]);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_dropdown_item_1line, emails);
                to.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<listemail> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
//                progress.dismiss();
            }
        });
    }
}