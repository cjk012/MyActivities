package com.j_kemp.chris.myactivities;

import android.support.v4.app.Fragment;

/**
 * Hosting Activity for User Settings Fragment.
 * Created by Christopher Kemp on 30/10/17.
 */

public class UserActivity extends SingleFragmentActivity {
/*
    private static final String TAG = "UserActivity";
    public static final String EXTRA_USER_OBJECT = "com.j_kemp.myactivities.user";

    public static Intent newIntent(Context packageContext, User user){
        Intent intent = new Intent(packageContext, UserActivity.class);
        intent.putExtra(EXTRA_USER_OBJECT, user);
        return intent;
    }
*/

    @Override
    protected Fragment createFragment(){
        /*User user_uuid = (UUID) getIntent().getSerializableExtra(EXTRA_USER_OBJECT);*/
        return new UserFragment();
    }
}
