package com.unilever.go.walls.Contracts;

import android.content.Context;

import com.unilever.go.walls.Base.BasePresenter;

public interface CallActivityContract {

    interface CallActivityView{

    }

    interface CallActivityPresenter extends BasePresenter<CallActivityView>{

        void removeCallListener(String listener);

        void addCallListener(Context context,String listener);

    }
}
