package com.unilever.go.wallsopsi1.Contracts;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.unilever.go.wallsopsi1.Activity.GroupDetailActivity;
import com.unilever.go.wallsopsi1.Base.BasePresenter;
import com.cometchat.pro.models.User;

public interface GroupDetailActivityContract {

    interface GroupDetailView{

        void setGroupName(String groupName);

        void setGroupId(String groupId);

        void setOwnerDetail(User user);

        void setGroupOwnerName(String owner);

        void setGroupIcon(String icon);

        void setGroupDescription(String description);

        void setUserScope(String scope);
    }

    interface GroupDetailPresenter extends BasePresenter<GroupDetailView> {

        void handleIntent(Intent data, Context context);

        void leaveGroup(String gUid);

        void clearConversation(String gUid);

        void setIcon(GroupDetailActivity groupDetailActivity, String icon, ImageView groupImage);


    }
}
