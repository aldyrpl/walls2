package com.unilever.go.walls.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.cometchat.pro.models.User;
import com.unilever.go.walls.Adapter.ContactListAdapter;
import com.unilever.go.walls.Contracts.BlockedUserListActivityContract;
import com.unilever.go.walls.Helper.RecyclerTouchListener;
import com.unilever.go.walls.Presenters.BlockedUserListActivityPresenter;
import com.unilever.go.walls.R;
import com.unilever.go.walls.Utils.CommonUtils;

import java.util.HashMap;

public class BlockedUserListActivity extends AppCompatActivity implements BlockedUserListActivityContract.BlockedUserListActivityView {


    private BlockedUserListActivityContract.BlockedUserListActivityPresenter blockedUserListActivityPresenter;

    private RecyclerView rvBlockedUser;

    private ContactListAdapter contactListAdapter;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockeduser_list);
        blockedUserListActivityPresenter=new BlockedUserListActivityPresenter();
        blockedUserListActivityPresenter.attach(this);
        initView();
    }

    private void initView() {

        rvBlockedUser=findViewById(R.id.rvBlockedUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvBlockedUser.setLayoutManager(linearLayoutManager);
        blockedUserListActivityPresenter.getBlockedUsers();


        rvBlockedUser.addOnItemTouchListener(new RecyclerTouchListener(this, rvBlockedUser, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View var1, int var2) {
                registerForContextMenu(rvBlockedUser);
                user = (User) var1.getTag(R.string.user);

                openContextMenu(var1);

            }

            @Override
            public void onLongClick(View var1, int var2) {

            }
        }));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.blockedlist_menu, menu);

        menu.setHeaderTitle(CommonUtils.setTitle("Select Action",this));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.unBlockUser:
                 blockedUserListActivityPresenter.unBlockUser(this,user);
                break;
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void setAdapter(HashMap<String, User> userHashMap) {

        if (contactListAdapter == null) {
            contactListAdapter = new ContactListAdapter(userHashMap, BlockedUserListActivity.this, R.layout.contact_list_item,true);
            rvBlockedUser.setAdapter(contactListAdapter);
        } else {
            if (userHashMap != null) {
                contactListAdapter.refreshData(userHashMap);
            }
        }

    }

    @Override
    public void userUnBlocked(String uid) {
        contactListAdapter.removeUser(uid);
    }
}
