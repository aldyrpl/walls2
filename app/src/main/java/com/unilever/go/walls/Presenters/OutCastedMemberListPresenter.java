package com.unilever.go.walls.Presenters;

import android.content.Context;
import android.widget.Toast;
import com.cometchat.pro.core.BannedGroupMembersRequest;
import com.unilever.go.walls.Adapter.GroupMemberListAdapter;
import com.unilever.go.walls.Base.Presenter;
import com.unilever.go.walls.Contracts.OutCastedMemberListContract;
import com.unilever.go.walls.Utils.Logger;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.GroupMember;

import java.util.List;

public class OutCastedMemberListPresenter extends Presenter<OutCastedMemberListContract.OutCastedMemberListView>
        implements OutCastedMemberListContract.OutCastedMemberListPresenter {

    private BannedGroupMembersRequest bannedMembersRequest;
    private Context context;

    @Override
    public void initMemberList(String groupId, int limit, Context context) {

        this.context = context;

        if (bannedMembersRequest == null) {
            bannedMembersRequest = new BannedGroupMembersRequest.BannedGroupMembersRequestBuilder(groupId).setLimit(limit).build();
            bannedMembersRequest.fetchNext(new CometChat.CallbackListener<List<GroupMember>>(){
                @Override
                public void onSuccess(List<GroupMember> groupMembers) {
                    if (groupMembers != null && groupMembers.size() != 0) {
                        Logger.error("OutcastMembersRequest", " " + groupMembers.size());
                        if (isViewAttached())
                            getBaseView().setAdapter(groupMembers);
                    }
                }

                @Override
                public void onError(CometChatException e) {
                    e.printStackTrace();
                }


            });
        } else {
            bannedMembersRequest.fetchNext(new CometChat.CallbackListener<List<GroupMember>>() {
                @Override
                public void onSuccess(List<GroupMember> groupMembers) {
                    if (groupMembers != null && groupMembers.size() != 0) {
                        Logger.error("OutcastMembersRequest", " " + groupMembers.size());
                        if (isViewAttached())
                            getBaseView().setAdapter(groupMembers);
                    }
                }

                @Override
                public void onError(CometChatException e) {
                    e.printStackTrace();
                }


            });
        }

    }

    @Override
    public void reinstateUser(final String uid, String groupId, final GroupMemberListAdapter groupMemberListAdapter) {
        CometChat.unbanGroupMember(uid, groupId, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String s) {
                groupMemberListAdapter.removeMember(uid);
                Logger.error("User ReinstatedListener", "Success");
            }

            @Override
            public void onError(CometChatException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }



}
