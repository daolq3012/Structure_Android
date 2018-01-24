package com.fstyle.structure_android.screen.edituser;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.fstyle.structure_android.R;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.repository.UserRepository;
import com.fstyle.structure_android.data.source.local.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.screen.BaseActivity;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.utils.rx.SchedulerProvider;

/**
 * Create by DaoLQ
 */
public class EditUserActivity extends BaseActivity implements EditUserContract.View {

    private EditUserContract.Presenter mPresenter;

    private EditText mEditText;
    private ImageView mImageViewAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        mEditText = findViewById(R.id.edtUserLogin);
        mImageViewAvatar = findViewById(R.id.imgAvatar);

        UserRepository userRepository =
                UserRepository.getInstance(UserLocalDataSource.getInstance(this),
                        UserRemoteDataSource.getInstance(getApplicationContext()));
        BaseSchedulerProvider schedulerProvider = SchedulerProvider.getInstance();

        mPresenter = new EditUserPresenter(userRepository, schedulerProvider);
        mPresenter.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                mPresenter.updateUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getAvatarUrl() {
        return getIntent().getStringExtra(Constant.ARGS_AVATAR_URL);
    }

    @Override
    public String getUserLoginInput() {
        return mEditText.getText().toString().trim();
    }

    @Override
    public void showUser(User user) {
        mEditText.setText(user.getLogin());
        Glide.with(this)
                .load(user.getAvatarUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(mImageViewAvatar);
    }

    @Override
    public void showGetUserError(Throwable throwable) {

    }

    @Override
    public void showListUser() {
        finish();
    }

    @Override
    public void showUpdateUserError(Throwable throwable) {

    }
}
