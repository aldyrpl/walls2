package com.unilever.go.walls.Contracts;

import android.app.ProgressDialog;
import android.content.Context;

import com.unilever.go.walls.Adapter.GroupListAdapter;
import com.unilever.go.walls.Base.BasePresenter;
import com.unilever.go.walls.Base.BaseView;
import com.cometchat.pro.models.Group;

import java.util.List;

public interface GroupListContract {


    interface GroupView extends BaseView {

        void setGroupAdapter(List<Group> groupList);

        void groupjoinCallback(Group group);

        void setFilterGroup(List<Group> groups);
    }

    interface GroupPresenter extends BasePresenter<GroupView>
    {
        void initGroupView();

        void joinGroup(Context context, Group group, ProgressDialog progressDialog, GroupListAdapter groupListAdapter);

        void refresh();

        void searchGroup(String s);
    }
}
