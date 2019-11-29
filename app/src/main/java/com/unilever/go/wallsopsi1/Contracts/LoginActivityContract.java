package com.unilever.go.wallsopsi1.Contracts;

import android.content.Context;

import com.unilever.go.wallsopsi1.Base.BasePresenter;

public interface LoginActivityContract {

    interface LoginActivityView {

        void startCometChatActivity();
    }

    interface LoginActivityPresenter extends BasePresenter<LoginActivityView> {

        void Login(Context context, String uid);

        void loginCheck();
    }
}
