package com.unilever.go.walls.Contracts;

import android.content.Context;

import com.unilever.go.walls.Adapter.GroupMemberListAdapter;
import com.unilever.go.walls.Base.BasePresenter;
import com.cometchat.pro.models.GroupMember;

import java.util.List;

public interface MemberListFragmentContract {


    interface MemberListFragmentView{

        void setAdapter(List<GroupMember> groupMemberList);


    }

    interface MemberListFragmentPresenter extends BasePresenter<MemberListFragmentView> {

        void initMemberList(String guid, int LIMIT, Context context);

        void outCastUser(String uid, String groupGuid, GroupMemberListAdapter groupMemberListAdapter);

        void kickUser(String uid, String groupId, GroupMemberListAdapter groupMemberListAdapter);
    }
}
