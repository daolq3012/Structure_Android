package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.local.realm.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.utils.validator.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by Sun on 3/12/2017.
 * Class Test for ({@link MainPresenter})
 */
public class MainPresenterTest {

    private static final String USER_LOGIN_1 = "user_login_1";
    private static final String USER_LOGIN_2 = "user_login_2";

    @Mock
    MainViewModel mViewModel;
    @Mock
    UserLocalDataSource mLocalDataSource;
    @Mock
    UserRemoteDataSource mRemoteDataSource;
    @Mock
    Validator mValidator;
    @Mock
    UserRepository mUserRepository;

    private MainPresenter mMainPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
//        mMainPresenter = new MainPresenter(mUserRepository, mValidator,
//                new ImmediateSchedulerProvider());
//        mMainPresenter.setViewModel(mViewModel);
    }

    @Test
    public void searchUsers_ValidDataInput_ShowListUser() {
        // Give
        List<User> users = new ArrayList<>();
        users.add(new User(USER_LOGIN_1));
        users.add(new User(USER_LOGIN_2));

//        // When
//        when(mUserRepository.searchUsers(Mockito.anyInt(), Mockito.anyString())).thenReturn(
//                Observable.just(users));
//        when(mValidator.validateAll(mViewModel, false)).thenReturn(true);
//
//        // Then
//        mMainPresenter.searchUsers(2, USER_LOGIN_1);
//
//        Mockito.verify(mViewModel, Mockito.never()).onSearchError(null);
//        Mockito.verify(mViewModel).onSearchUsersSuccess(users);
    }

    @Test
    public void searchUsers_ValidDataInputNetworkError_ShowError() {
        // Give
        String errorMsg = "No internet";
        Throwable throwable = new Throwable(errorMsg);

//        // When
//        when(mUserRepository.searchUsers(Mockito.anyInt(), Mockito.anyString())).thenReturn(
//                Observable.error(throwable));
//        when(mValidator.validateAll(mViewModel, false)).thenReturn(true);
//
//        // Then
//        mMainPresenter.searchUsers(2, USER_LOGIN_1);
//
//        Mockito.verify(mViewModel, Mockito.never()).onSearchUsersSuccess(null);
//        Mockito.verify(mViewModel).onSearchError(throwable);
    }
}
