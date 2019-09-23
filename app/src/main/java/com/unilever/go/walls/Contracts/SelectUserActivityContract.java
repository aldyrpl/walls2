package com.unilever.go.walls.Contracts;

import android.content.Intent;

import com.cometchat.pro.models.User;
import com.unilever.go.walls.Activity.SelectUserActivity;
import com.unilever.go.walls.Base.BasePresenter;

import java.util.HashMap;
import java.util.Set;

public interface SelectUserActivityContract {


    interface SelectUserActivityView{

        void setScope(String scope);

        void setGUID(String guid);

        void setContactAdapter(HashMap<String, User> userHashMap);
    }

    interface SelectUserActivityPresenter extends BasePresenter<SelectUserActivityView> {

        void getIntent(Intent intent);

        void getUserList(int i);


        void addMemberToGroup(String guid, SelectUserActivity selectUserActivity, Set<String> keySet);
    }
}