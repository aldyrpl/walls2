package com.unilever.go.walls.Contracts;

import android.content.Context;

import com.unilever.go.walls.Base.BasePresenter;

public interface LoginActivityContract {

    interface LoginActivityView {

        void startCometChatActivity();
    }

    interface LoginActivityPresenter extends BasePresenter<LoginActivityView> {

        void Login(Context context,String uid);

        void loginCheck();
    }
}
