package com.unilever.go.wallsopsi1.Contracts;

import android.content.Context;

import com.unilever.go.wallsopsi1.Base.BasePresenter;

public interface CallActivityContract {

    interface CallActivityView{

    }

    interface CallActivityPresenter extends BasePresenter<CallActivityView>{

        void removeCallListener(String listener);

        void addCallListener(Context context, String listener);

    }
}
