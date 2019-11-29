package com.unilever.go.wallsopsi1.Controller.home.remindme;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;
import java.util.List;

public class AlarmService extends Service
{
    AlarmReceiver alarm = new AlarmReceiver();
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;
    private String mRepeat;
    private String[] mDateSplit;
    private String[] mTimeSplit;
    private int mYear, mMonth, mHour, mMinute, mDay, mReceivedID;
    private long mRepeatTime;

    private Calendar mCalendar;
    private AlarmReceiver mAlarmReceiver;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        start();
        return START_STICKY;
    }

    void start(){
        ReminderDatabase rb = new ReminderDatabase(this);
        mCalendar = Calendar.getInstance();
        mAlarmReceiver = new AlarmReceiver();

        List<Reminder> reminders = rb.getAllReminders();

        for (Reminder rm : reminders) {
            mReceivedID = rm.getID();
            mRepeat = rm.getRepeat();
            mRepeatNo = rm.getRepeatNo();
            mRepeatType = rm.getRepeatType();
            mActive = rm.getActive();
            mDate = rm.getDate();
            mTime = rm.getTime();

            mDateSplit = mDate.split("/");
            mTimeSplit = mTime.split(":");

            mDay = Integer.parseInt(mDateSplit[0]);
            mMonth = Integer.parseInt(mDateSplit[1]);
            mYear = Integer.parseInt(mDateSplit[2]);
            mHour = Integer.parseInt(mTimeSplit[0]);
            mMinute = Integer.parseInt(mTimeSplit[1]);

            mCalendar.set(Calendar.MONTH, --mMonth);
            mCalendar.set(Calendar.YEAR, mYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
            mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
            mCalendar.set(Calendar.MINUTE, mMinute);
            mCalendar.set(Calendar.SECOND, 0);

            // Cancel existing notification of the reminder by using its ID
            // mAlarmReceiver.cancelAlarm(context, mReceivedID);

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

            // Create a new notification
            if (mActive.equals("true")) {
                if (mRepeat.equals("true")) {
                    mAlarmReceiver.setRepeatAlarm(this, mCalendar, mReceivedID, mRepeatTime);
                } else if (mRepeat.equals("false")) {
                    mAlarmReceiver.setAlarm(this, mCalendar, mReceivedID);
                }
            }
        }
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
       start();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}