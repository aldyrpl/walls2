package com.unilever.go.wallsopsi1.Contracts;

import android.content.Context;

import com.unilever.go.wallsopsi1.Adapter.GroupMemberListAdapter;
import com.unilever.go.wallsopsi1.Base.BasePresenter;
import com.cometchat.pro.models.GroupMember;

import java.util.List;

public interface OutCastedMemberListContract {

    interface OutCastedMemberListView{

        void setAdapter(List<GroupMember> list);
    }

    interface OutCastedMemberListPresenter extends BasePresenter<OutCastedMemberListView>
    {

        void initMemberList(String groupId, int limit, Context context);

        void reinstateUser(String uid, String groupId, GroupMemberListAdapter groupMemberListAdapter);
    }


}
