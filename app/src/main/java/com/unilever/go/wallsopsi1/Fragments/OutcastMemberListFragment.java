package com.unilever.go.wallsopsi1.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unilever.go.wallsopsi1.R;
import com.unilever.go.wallsopsi1.Adapter.GroupMemberListAdapter;
import com.unilever.go.wallsopsi1.Contracts.OutCastedMemberListContract;
import com.unilever.go.wallsopsi1.Contracts.StringContract;
import com.unilever.go.wallsopsi1.Helper.RecyclerTouchListener;
import com.unilever.go.wallsopsi1.Presenters.OutCastedMemberListPresenter;
import com.unilever.go.wallsopsi1.Utils.CommonUtils;
import com.cometchat.pro.models.GroupMember;
import com.cometchat.pro.models.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutcastMemberListFragment extends Fragment implements OutCastedMemberListContract.OutCastedMemberListView{

    private RecyclerView rvMembers;

    private static final int LIMIT = 30;

    private LinearLayoutManager linearLayoutManager;

    private User user;

    private String groupId;

    private GroupMemberListAdapter groupMemberListAdapter;

    private OutCastedMemberListContract.OutCastedMemberListPresenter outCastedMemberListPresenter;

    public OutcastMemberListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_outcast_member_list, container, false);

        outCastedMemberListPresenter = new OutCastedMemberListPresenter();
        outCastedMemberListPresenter.attach(this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvMembers = view.findViewById(R.id.rv_member);
        rvMembers.setLayoutManager(linearLayoutManager);

        registerForContextMenu(rvMembers);

        if (getArguments().containsKey(StringContract.IntentStrings.INTENT_GROUP_ID)) {
            groupId = getArguments().getString(StringContract.IntentStrings.INTENT_GROUP_ID);
            outCastedMemberListPresenter.initMemberList(groupId, LIMIT, getContext());
        }

        rvMembers.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvMembers, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View var1, int var2) {

            }

            @Override
            public void onLongClick(View var1, int var2) {

                user = (User) var1.getTag(R.string.user);
                getActivity().openContextMenu(var1);

            }
        }));

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_group_action, menu);
        MenuItem kickItem=menu.findItem(R.id.menu_item_kick);
        MenuItem outcastItem=menu.findItem(R.id.menu_item_outcast);

        kickItem.setVisible(false);
        outcastItem.setVisible(false);
        menu.setHeaderTitle(CommonUtils.setTitle("Select Action",getContext()));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item_Reinstate:
                outCastedMemberListPresenter.reinstateUser(user.getUid(), groupId,groupMemberListAdapter);
                showToast("reinstate");
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        outCastedMemberListPresenter.detach();
    }

    @Override
    public void setAdapter(List<GroupMember> list) {

        if (groupMemberListAdapter == null) {
            groupMemberListAdapter = new GroupMemberListAdapter(list, getContext(),null);
            rvMembers.setAdapter(groupMemberListAdapter);
        } else {
            groupMemberListAdapter.notifyDataSetChanged();
        }
    }
}
