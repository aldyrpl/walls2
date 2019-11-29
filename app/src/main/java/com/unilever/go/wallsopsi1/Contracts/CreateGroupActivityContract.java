package com.unilever.go.wallsopsi1.Contracts;

import android.content.Context;

import com.unilever.go.wallsopsi1.Base.BasePresenter;
import com.unilever.go.wallsopsi1.Base.BaseView;
import com.cometchat.pro.models.Group;


public interface CreateGroupActivityContract {

    interface CreateGroupView extends BaseView {

    }

    interface CreateGroupPresenter extends BasePresenter<CreateGroupView> {

        void createGroup(Context context, Group group);

    }
}
