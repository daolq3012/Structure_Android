package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.data.source.local.realm.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.utils.validator.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by Sun on 3/12/2017.
 * Class Test for ({@link MainPresenter})
 */
public class MainPresenterTest {

    private static final String USER_LOGIN_1 = "user_login_1";
    private static final String USER_LOGIN_2 = "user_login_2";

    @Mock
    MainViewModel mView;
    @Mock
    UserLocalDataSource mLocalDataSource;
    @Mock
    UserRemoteDataSource mRemoteDataSource;
    @Mock
    Validator mValidator;

    private UserRepository mUserRepository;
    private MainPresenter mMainPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
        mUserRepository = new UserRepository(mLocalDataSource, mRemoteDataSource);
        mMainPresenter = new MainPresenter(mView, mUserRepository, mValidator);
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void searchUsers() throws Exception {
        // Give
        List<User> users = new ArrayList<>();
        users.add(new User(USER_LOGIN_1));
        users.add(new User(USER_LOGIN_2));

//        // When
//        Mockito.when(mUserRepository.getRemoteDataSource()
//                .searchUsers(Mockito.anyInt(), Mockito.anyString()))
//                .thenReturn(Observable.just(users));
//
//        // Then
//        mMainPresenter.searchUsers(2, USER_LOGIN_1);
//
//        Mockito.verify(mView, Mockito.never()).onSearchError(null);
//        //        Mockito.verify(mView).showListUser(users);
//
//        // Give
//        String errorMsg = "No internet";
//        Throwable throwable = new Throwable(errorMsg);
//
//        // When
//        Mockito.when(mUserRepository.getRemoteDataSource()
//                .searchUsers(Mockito.anyInt(), Mockito.anyString()))
//                .thenReturn(Observable.<List<User>>error(throwable));
//
//        // Then
//        mMainPresenter.searchUsers(2, Mockito.anyString());
//
//        Mockito.verify(mView, Mockito.never()).onSearchUsersSuccess(null);
//        Mockito.verify(mView).showError(throwable);
    }
}
