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

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.unilever.go.wallsopsi1.Controller.Retrofit.EventAPI;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.EventClassJson;
import com.unilever.go.wallsopsi1.Controller.Retrofit.jsonClass.getEventClassJson;
import com.unilever.go.wallsopsi1.Controller.home.gallery.gallery;
import com.unilever.go.wallsopsi1.Controller.intro.login;
import com.unilever.go.wallsopsi1.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mList;
    private SimpleAdapter mAdapter;
    private Toolbar mToolbar;
    private TextView mNoReminderView;
    private FloatingActionButton mAddReminderButton;
    private int mTempPost;
    private LinkedHashMap<Integer, Integer> IDmap = new LinkedHashMap<>();
    private ReminderDatabase rb;
    private MultiSelector mMultiSelector = new MultiSelector();
    private AlarmReceiver mAlarmReceiver;
    public static final String URL = "http://103.136.25.83:8000/api/";
    public ArrayList<String> idCategory = new ArrayList<String>();
    public ArrayList<String> idEventAPI = new ArrayList<String>();
    ArrayList<String> listemail = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize reminder database
        rb = new ReminderDatabase(getApplicationContext());

        // Initialize views
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddReminderButton = (FloatingActionButton) findViewById(R.id.add_reminder);

        mNoReminderView = (TextView) findViewById(R.id.no_reminder_text);
        mList = (RecyclerView) findViewById(R.id.reminder_list);
        mList.setLayoutManager(getLayoutManager());

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);

        // On clicking the floating action button
        mAddReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReminderAddActivity.class);
                startActivity(intent);
            }
        });

        // Initialize alarm
        mAlarmReceiver = new AlarmReceiver();

        if(isReceiveBootPermission()){

        }
//        getEventFromAPI(this);
    }

    public void getEventFromAPI(Context context){
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait....");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EventAPI api = retrofit.create(EventAPI.class);
        Call<getEventClassJson> call = api.getEvent("100", "0", login.dataUser.getId());
        call.enqueue(new Callback<getEventClassJson>() {
            @Override
            public void onResponse(Call<getEventClassJson> call, Response<getEventClassJson> response) {
//                try {
//                    List<Reminder> mTest = rb.getAllReminders();
//                }catch (Exception e){
//
//                }
                try{
                    listemail.clear();
                }catch (Exception e){

                }
                rb = new ReminderDatabase(context);
//                idCategory.clear();
//                idEventAPI.clear();
                try {
//                    if (mTest.size() != response.body().getResult().getListData().size()) {
                        rb.deleteAllReminder();
                        for(int i =0;i < 50;i++){
                            try {
                                mAlarmReceiver.cancelAlarm(getApplicationContext(), i);
                            }catch (Exception e){

                            }
                        }

                        Log.d("countk ", String.valueOf(response.body().getResult().getListData().size()));
                        for (int i = 0; i < response.body().getResult().getListData().size(); i++) {
                            String mTitle = response.body().getResult().getListData().get(i).getDescription();
                            String mDate = response.body().getResult().getListData().get(i).getDueDate();
                            String mTime = response.body().getResult().getListData().get(i).getDueTime();
                            String mRepeat = "true";
                            String mRepeatNo = "1";
                            String mRepeatType = "Minute";
                            String mActive = response.body().getResult().getListData().get(i).getIsAlarm();
                            if (mActive.equals("0")) {
                                mActive = "false";
                            } else {
                                mActive = "true";
                            }
                            String mID_API = response.body().getResult().getListData().get(i).getId();

//                        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
                            try {
//                            Date date1=formatter1.parse(mDate);
                                String OLD_FORMAT = "yyyy-MM-dd";
                                String NEW_FORMAT = "dd/MM/yyyy";
                                SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
                                Date d = sdf.parse(mDate);
                                sdf.applyPattern(NEW_FORMAT);
                                mDate = sdf.format(d);
                                Log.d("datenya ", mDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.d("titlenya ", response.body().getResult().getListData().get(i).getTo());
//                            idCategory.add(response.body().getResult().getListData().get(i).getTitle());
                            idEventAPI.add(response.body().getResult().getListData().get(i).getId());
//                            Log.d("titlenyaa ", response.body().getResult().getListData().get(i).getTitle());
                            saveReminder(mTitle, mDate, mTime, mRepeat, mRepeatNo, mRepeatType, mActive, mID_API, context);

                            listemail.add(response.body().getResult().getListData().get(i).getTo());
                        }
//                     Create recycler view
                        try {
                            registerForContextMenu(mList);
                            mAdapter = new SimpleAdapter();
                            mAdapter.setItemCount(getDefaultItemCount());
                            mList.setAdapter(mAdapter);
                        }catch (Exception e){

                        }
//                    } else {
//                        // Create recycler view
//                        for (int i = 0; i < response.body().getResult().getListData().size(); i++) {
//                            idCategory.add(response.body().getResult().getListData().get(i).getTitle());
//                            idEventAPI.add(response.body().getResult().getListData().get(i).getId());
//                        }
//                        mList.setLayoutManager(getLayoutManager());
//                        registerForContextMenu(mList);
//                        mAdapter = new SimpleAdapter();
//                        mAdapter.setItemCount(getDefaultItemCount());
//                        mList.setAdapter(mAdapter);
//                    }
                }catch (Exception e){
//                    mNoReminderView.setVisibility(View.VISIBLE);
                    Log.d("ex ",e.toString());
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<getEventClassJson> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

    public void saveReminder(String mTitle, String mDate, String mTime, String mRepeat, String mRepeatNo, String mRepeatType, String mActive, String mID_API, Context context){
//        ReminderDatabase rb = new ReminderDatabase(this);

        // Creating Reminder

        Log.d("masuk truee mTitle", String.valueOf(mTitle));
        Log.d("masuk truee mDate", String.valueOf(mDate));
        Log.d("masuk truee mTime", String.valueOf(mTime));
        int ID = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, mRepeatNo, mRepeatType, mActive, mID_API));
        Log.d("idbro ", String.valueOf(ID));
        Calendar mCalendar = Calendar.getInstance();
        int mYear, mMonth, mHour, mMinute, mDay;
        long mRepeatTime = 60000L;

        long milMinute = 60000L;
        long milHour = 3600000L;
        long milDay = 86400000L;
        long milWeek = 604800000L;
        long milMonth = 2592000000L;

        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        int tanggal = Integer.parseInt(mDate.split("/")[0]);
        int  bulan = Integer.parseInt(mDate.split("/")[1]);
        int  tahun = Integer.parseInt(mDate.split("/")[2]);

        int  jam = Integer.parseInt(mTime.split(":")[0]);
        int  menit = Integer.parseInt(mTime.split(":")[1]);



        mCalendar.set(Calendar.MONTH, --bulan);
        mCalendar.set(Calendar.YEAR, tahun);
        mCalendar.set(Calendar.DAY_OF_MONTH, tanggal);
        mCalendar.set(Calendar.HOUR_OF_DAY, jam);
        mCalendar.set(Calendar.MINUTE, menit);
        mCalendar.set(Calendar.SECOND, 0);

//        Log.d("masuk true menit", String.valueOf(menit));
        Log.d("yihuh ", String.valueOf(mCalendar.getTime()));
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

        // Create a new notification
        if (mActive.equals("true")) {
            Log.d("masuk truenih", mRepeat);
            if (mRepeat.equals("true")) {
                new AlarmReceiver().setAlarm(getApplicationContext(), mCalendar, ID);
            } else if (mRepeat.equals("false")) {
                new AlarmReceiver().setAlarm(getApplicationContext(), mCalendar, ID);
            }
        }


    }

    public  boolean isReceiveBootPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECEIVE_BOOT_COMPLETED)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(gallery.class.getSimpleName(),"Permission is granted");
                return true;
            } else {

                Log.v(gallery.class.getSimpleName(),"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, 1);
                finish();
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(gallery.class.getSimpleName(),"Permission is granted");
            return true;
        }
    }
    // Create context menu for long press actions
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
    }

    // Multi select items in recycler view
    private android.support.v7.view.ActionMode.Callback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                // On clicking discard reminders
                case R.id.discard_reminder:
                    // Close the context menu
                    actionMode.finish();

                    // Get the reminder id associated with the recycler view item
                    for (int i = IDmap.size(); i >= 0; i--) {
                        if (mMultiSelector.isSelected(i, 0)) {
                            int id = IDmap.get(i);

                            // Get reminder from reminder database using id
                            Reminder temp = rb.getReminder(id);
                            // Delete reminder
                            rb.deleteReminder(temp);
                            // Remove reminder from recycler view
                            mAdapter.removeItemSelected(i);
                            // Delete reminder alarm
                            mAlarmReceiver.cancelAlarm(getApplicationContext(), id);

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            EventAPI api = retrofit.create(EventAPI.class);
                            Call<EventClassJson> call = api.deleteEvent(login.dataUser.getId(), temp.getID_API());
                            call.enqueue(new Callback<EventClassJson>() {
                                @Override
                                public void onResponse(Call<EventClassJson> call, Response<EventClassJson> response) {

                                    try{
                                        Toast.makeText(getApplicationContext(), "Removed", Toast.LENGTH_SHORT).show();
                                    }catch (Exception e){
                                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<EventClassJson> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    // Clear selected items in recycler view
                    mMultiSelector.clearSelections();
                    // Recreate the recycler items
                    // This is done to remap the item and reminder ids
                    mAdapter.onDeleteItem(getDefaultItemCount());

                    // Display toast to confirm delete
                    Toast.makeText(getApplicationContext(),
                            "Deleted",
                            Toast.LENGTH_SHORT).show();

                    // To check is there are saved reminders
                    // If there are no reminders display a message asking the user to create reminders
                    List<Reminder> mTest = rb.getAllReminders();

                    if (mTest.isEmpty()) {
                        mNoReminderView.setVisibility(View.VISIBLE);
                    } else {
                        mNoReminderView.setVisibility(View.GONE);
                    }

                    return true;

                // On clicking save reminders
                case R.id.save_reminder:
                    // Close the context menu
                    actionMode.finish();
                    // Clear selected items in recycler view
                    mMultiSelector.clearSelections();
                    return true;

                default:
                    break;
            }
            return false;
        }
    };

    // On clicking a reminder item
    private void selectReminder(int mClickID) {
        String mStringClickID = Integer.toString(mClickID);

        // Create intent to edit the reminder
        // Put reminder id as extra
        Intent i = new Intent(this, ReminderEditActivity.class);
        i.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, mStringClickID);
        startActivityForResult(i, 1);
        Log.d("idnyacuy ",idEventAPI.get(mClickID));
//        ReminderEditActivity.idCategory = idCategory.get(mClickID - 1);
        ReminderEditActivity.id_event_api = idEventAPI.get(mClickID - 1);
        Log.d("idCategory ", ReminderEditActivity.idCategory);

        ReminderEditActivity.listemail = listemail.get(mClickID - 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAdapter.setItemCount(getDefaultItemCount());
    }

    // Recreate recycler view
    // This is done so that newly created reminders are displayed
    @Override
    public void onResume(){
        super.onResume();

        // To check is there are saved reminders
        // If there are no reminders display a message asking the user to create reminders

        getEventFromAPI(this);
    }

    @Override
    public void onStart(){
        super.onStart();

        // To check is there are saved reminders
        // If there are no reminders display a message asking the user to create reminders

        getEventFromAPI(this);
    }


    // Layout manager for recycler view
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    protected int getDefaultItemCount() {
        return 100;
    }

    // Create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    // Setup menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // start licenses activity
//            case R.id.action_licenses:
//                Intent intent = new Intent(this, LicencesActivity.class);
//                startActivity(intent);
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Adapter class for recycler view
    public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.VerticalItemHolder> {
        private ArrayList<ReminderItem> mItems;

        public SimpleAdapter() {
            mItems = new ArrayList<>();
        }

        public void setItemCount(int count) {
            mItems.clear();
            mItems.addAll(generateData(count));
            notifyDataSetChanged();
        }

        public void onDeleteItem(int count) {
            mItems.clear();
            mItems.addAll(generateData(count));
        }

        public void removeItemSelected(int selected) {
            if (mItems.isEmpty()) return;
            mItems.remove(selected);
            notifyItemRemoved(selected);
        }

        // View holder for recycler view items
        @Override
        public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View root = inflater.inflate(R.layout.recycle_items, container, false);

            return new VerticalItemHolder(root, this);
        }

        @Override
        public void onBindViewHolder(VerticalItemHolder itemHolder, int position) {
            ReminderItem item = mItems.get(position);
            itemHolder.setReminderTitle(item.mTitle);
            itemHolder.setReminderDateTime(item.mDateTime);
            itemHolder.setReminderRepeatInfo(item.mRepeat, item.mRepeatNo, item.mRepeatType);
            itemHolder.setActiveImage(item.mActive);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        // Class for recycler view items
        public  class ReminderItem {
            public String mTitle;
            public String mDateTime;
            public String mRepeat;
            public String mRepeatNo;
            public String mRepeatType;
            public String mActive;

            public ReminderItem(String Title, String DateTime, String Repeat, String RepeatNo, String RepeatType, String Active) {
                this.mTitle = Title;
                this.mDateTime = DateTime;
                this.mRepeat = Repeat;
                this.mRepeatNo = RepeatNo;
                this.mRepeatType = RepeatType;
                this.mActive = Active;
            }
        }

        // Class to compare date and time so that items are sorted in ascending order
        public class DateTimeComparator implements Comparator {
            DateFormat f = new SimpleDateFormat("dd/mm/yyyy hh:mm");

            public int compare(Object a, Object b) {
                String o1 = ((DateTimeSorter)a).getDateTime();
                String o2 = ((DateTimeSorter)b).getDateTime();

                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        // UI and data class for recycler view items
        public  class VerticalItemHolder extends SwappingHolder
                implements View.OnClickListener, View.OnLongClickListener {
            private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
            private ImageView mActiveImage , mThumbnailImage;
            private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
            private TextDrawable mDrawableBuilder;
            private SimpleAdapter mAdapter;

            public VerticalItemHolder(View itemView, SimpleAdapter adapter) {
                super(itemView, mMultiSelector);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
                itemView.setLongClickable(true);


                // Initialize adapter for the items
                mAdapter = adapter;

                // Initialize views
                mTitleText = (TextView) itemView.findViewById(R.id.recycle_title);
                mDateAndTimeText = (TextView) itemView.findViewById(R.id.recycle_date_time);
                mRepeatInfoText = (TextView) itemView.findViewById(R.id.recycle_repeat_info);
                mActiveImage = (ImageView) itemView.findViewById(R.id.active_image);
                mThumbnailImage = (ImageView) itemView.findViewById(R.id.thumbnail_image);
            }

            // On clicking a reminder item
            @Override
            public void onClick(View v) {
                if (!mMultiSelector.tapSelection(this)) {
                    mTempPost = mList.getChildAdapterPosition(v);

                    int mReminderClickID = IDmap.get(mTempPost);
                    try {
                        selectReminder(mReminderClickID);
                    }catch (Exception e){}

                } else if(mMultiSelector.getSelectedPositions().isEmpty()){
                    mAdapter.setItemCount(getDefaultItemCount());
                }
            }

            // On long press enter action mode with context menu
            @Override
            public boolean onLongClick(View v) {
                AppCompatActivity activity = MainActivity.this;
                activity.startSupportActionMode(mDeleteMode);
                mMultiSelector.setSelected(this, true);
                v.getBackground().setTint(Color.parseColor("#43A047"));
                return true;
            }

            // Set reminder title view
            public void setReminderTitle(String title) {
                mTitleText.setText(title);
                String letter = "A";

                if(title != null && !title.isEmpty()) {
                    letter = title.substring(0, 1);
                }

                int color = mColorGenerator.getRandomColor();

                // Create a circular icon consisting of  a random background colour and first letter of title
                mDrawableBuilder = TextDrawable.builder()
                        .buildRound(letter, color);
                mThumbnailImage.setImageDrawable(mDrawableBuilder);
            }

            // Set date and time views
            public void setReminderDateTime(String datetime) {
                mDateAndTimeText.setText(datetime);
            }

            // Set repeat views
            public void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
                if(repeat.equals("true")){
                    mRepeatInfoText.setText("Every " + repeatNo + " " + repeatType + "(s)");
                }else if (repeat.equals("false")) {
                    mRepeatInfoText.setText("Repeat Off");
                }
            }

            // Set active image as on or off
            public void setActiveImage(String active){
                if(active.equals("true")){
                    mActiveImage.setImageResource(R.drawable.ic_notifications_on_white_24dp);
                    mActiveImage.getDrawable().setTint(Color.parseColor("#43A047"));
                }else if (active.equals("false")) {
                    mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
                    mActiveImage.getDrawable().setTint(Color.parseColor("#ff0057"));
                }
            }
        }

        // Generate random test data
        public  ReminderItem generateDummyData() {
            return new ReminderItem("1", "2", "3", "4", "5", "6");
        }

        // Generate real data for each item
        public List<ReminderItem> generateData(int count) {
            ArrayList<ReminderItem> items = new ArrayList<>();

            // Get all reminders from the database
            List<Reminder> reminders = rb.getAllReminders();

            // Initialize lists
            List<String> Titles = new ArrayList<>();
            List<String> Repeats = new ArrayList<>();
            List<String> RepeatNos = new ArrayList<>();
            List<String> RepeatTypes = new ArrayList<>();
            List<String> Actives = new ArrayList<>();
            List<String> DateAndTime = new ArrayList<>();
            List<Integer> IDList= new ArrayList<>();
            List<DateTimeSorter> DateTimeSortList = new ArrayList<>();

            // Add details of all reminders in their respective lists
            for (Reminder r : reminders) {
                Titles.add(r.getTitle());
                DateAndTime.add(r.getDate() + " " + r.getTime());
                Repeats.add(r.getRepeat());
                RepeatNos.add(r.getRepeatNo());
                RepeatTypes.add(r.getRepeatType());
                Actives.add(r.getActive());
                IDList.add(r.getID());
            }

            int key = 0;

            // Add date and time as DateTimeSorter objects
            for(int k = 0; k<Titles.size(); k++){
                DateTimeSortList.add(new DateTimeSorter(key, DateAndTime.get(k)));
                key++;
            }

            // Sort items according to date and time in ascending order
//            Collections.sort(DateTimeSortList, new DateTimeComparator());

            int k = 0;

            // Add data to each recycler view item
            for (DateTimeSorter item:DateTimeSortList) {
                int i = item.getIndex();

                items.add(new SimpleAdapter.ReminderItem(Titles.get(i), DateAndTime.get(i), Repeats.get(i),
                        RepeatNos.get(i), RepeatTypes.get(i), Actives.get(i)));
                IDmap.put(k, IDList.get(i));
                k++;
            }
          return items;
        }
    }
}
