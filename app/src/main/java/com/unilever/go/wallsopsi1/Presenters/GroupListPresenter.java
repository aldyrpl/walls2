package com.unilever.go.wallsopsi1.Presenters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.unilever.go.wallsopsi1.Adapter.GroupListAdapter;
import com.unilever.go.wallsopsi1.Base.Presenter;
import com.unilever.go.wallsopsi1.Contracts.GroupListContract;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.core.GroupsRequest;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.Group;

import java.util.HashMap;
import java.util.List;

public class GroupListPresenter extends Presenter<GroupListContract.GroupView> implements
        GroupListContract.GroupPresenter  {

    private GroupsRequest groupsRequest;

    private HashMap<String,Group> groupHashMap=new HashMap<>();

    private static final String TAG = "GroupListPresenter";

    @Override
    public void initGroupView() {

        if (groupsRequest == null) {

            groupsRequest = new GroupsRequest.GroupsRequestBuilder().setLimit(50).build();

            groupsRequest.fetchNext(new CometChat.CallbackListener<List<Group>>() {
                @Override
                public void onSuccess(List<Group> groups) {
                    if (isViewAttached()) {
                        Log.d(TAG, "onSuccess: "+groups.toString());
                        getBaseView().setGroupAdapter(groups);


                    }

                }

                @Override
                public void onError(CometChatException e) {
                    Log.d(TAG, "onError: "+e.getMessage());
                }

            });
        } else {
            groupsRequest.fetchNext(new CometChat.CallbackListener<List<Group>>() {
                @Override
                public void onSuccess(List<Group> groups) {
                    Log.d(TAG, "onSuccess: "+groups.toString());
                    if (isViewAttached()&&groups.size()!=0)
                    {
                        getBaseView().setGroupAdapter(groups);
                    }


                }

                @Override
                public void onError(CometChatException e) {
                    Log.d(TAG, "onError: "+e.getMessage());
                }

            });
        }
    }

    @Override
    public void joinGroup(final Context context, final Group group, final ProgressDialog progressDialog,
                          final GroupListAdapter groupListAdapter) {

        CometChat.joinGroup(group.getGuid(), group.getGroupType(), group.getPassword(), new CometChat.CallbackListener<Group>() {
            @Override
            public void onSuccess(Group group) {
                progressDialog.dismiss();
                group.setHasJoined(true);
                groupListAdapter.notifyDataSetChanged();
                if (isViewAttached())
                    getBaseView().groupjoinCallback(group);
            }

            @Override
            public void onError(CometChatException e) {
                Log.d("joinGroup", "onError: "+e.getMessage());
                progressDialog.dismiss();
            }


        });

    }

    @Override
    public void refresh() {
           groupsRequest=null;
           initGroupView();
    }

    @Override
    public void searchGroup(String s) {
        GroupsRequest groupsRequest= new GroupsRequest.GroupsRequestBuilder().setSearchKeyWord(s).setLimit(100).build();

        groupsRequest.fetchNext(new CometChat.CallbackListener<List<Group>>() {
            @Override
            public void onSuccess(List<Group> groups) {
                getBaseView().setFilterGroup(groups);
            }

            @Override
            public void onError(CometChatException e) {

            }
        });


    }

}
