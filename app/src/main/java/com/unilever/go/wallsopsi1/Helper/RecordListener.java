package com.unilever.go.wallsopsi1.Helper;

public interface RecordListener {
    void onStart();
    void onCancel();
    void onFinish(long time);
    void onLessTime();
}