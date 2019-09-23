package com.unilever.go.walls.Contracts;

import android.content.Context;

import com.unilever.go.walls.Base.BasePresenter;
import com.unilever.go.walls.Base.BaseView;
import com.cometchat.pro.models.Group;


public interface CreateGroupActivityContract {

    interface CreateGroupView extends BaseView {

    }

    interface CreateGroupPresenter extends BasePresenter<CreateGroupView> {

        void createGroup(Context context, Group group);

    }
}
