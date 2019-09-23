package com.unilever.go.walls.Contracts;

import android.content.Context;

import com.cometchat.pro.models.User;
import com.unilever.go.walls.Base.BasePresenter;

import java.util.HashMap;

public interface BlockedUserListActivityContract {

    interface BlockedUserListActivityView{

        void setAdapter(HashMap<String, User> userHashMap);

        void userUnBlocked(String uid);
    }

    interface BlockedUserListActivityPresenter extends BasePresenter<BlockedUserListActivityView>{

        void getBlockedUsers();

        void unBlockUser(Context context, User user);
    }
}
