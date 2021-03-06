package com.unilever.go.wallsopsi1.Contracts;

import android.content.Context;
import android.content.Intent;

import com.cometchat.pro.models.MessageReceipt;
import com.cometchat.pro.models.TypingIndicator;
import com.unilever.go.wallsopsi1.Activity.GroupChatActivity;
import com.unilever.go.wallsopsi1.Base.BasePresenter;
import com.unilever.go.wallsopsi1.Base.BaseView;
import com.unilever.go.wallsopsi1.CustomView.CircleImageView;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.models.Group;

import java.io.File;
import java.util.List;

public interface GroupChatActivityContract {


    interface GroupChatView extends BaseView {

        void setGroupId(String stringExtra);

        void setAdapter(List<BaseMessage> messageList);

        void addSentMessage(BaseMessage baseMessage);

        void setGroup(Group group);

        void setSubTitle(String[] users);

        void setOwnerUid(String id);

        void addReceivedMessage(BaseMessage baseMessage);

        void typingStarted(TypingIndicator typingIndicator);

        void typingEnded(TypingIndicator typingIndicator);

        void setDeliveryReceipt(MessageReceipt messageReceipt);

        void onMessageRead(MessageReceipt messageReceipt);

        void setDeletedMessage(BaseMessage baseMessage);

        void setEditedMessage(BaseMessage baseMessage);

        void setFilterList(List<BaseMessage> list);
    }

    interface GroupChatPresenter extends BasePresenter<GroupChatView> {

        void getContext(Context context);

        void handleIntent(Intent intent);

        void addMessageReceiveListener(String ListenerId, String groupId, String ownerId);

        void removeMessageReceiveListener(String ListenerId);

        void addGroupEventListener(String listenerId, String groupId, String ownerId);

        void sendTextMessage(String message, String groupId);

        void fetchPreviousMessage(String groupId, int limit);

        void setGroupIcon(GroupChatActivity groupChatActivity, String icon, CircleImageView groupIcon);

        void sendMediaMessage(File imagefile, String groupId, String messageType);

        void leaveGroup(Group group, Context context);

        void fetchGroupMembers(String groupId);

        void sendCallRequest(Context context, String groupId, String receiverTypeUser, String callTypeAudio);

        void addCallListener(String call_listener);

        void removeCallListener(String call_listener);

        void sendTypingIndicator(String groupId);

        void endTypingIndicator(String groupId);

        void deleteMessage(BaseMessage baseMessage);

        void editMessage(BaseMessage baseMessage, String message);

        void searchMessage(String s, String groupId);

        void refreshList(String groupId, String ownerId, int limit);
    }

}
