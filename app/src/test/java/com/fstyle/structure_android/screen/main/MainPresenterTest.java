package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.rx.BaseSchedulerProvider;
import com.fstyle.structure_android.utils.rx.CustomCompositeSubscription;
import com.fstyle.structure_android.utils.validator.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rx.Observable;

/**
 * Created by Sun on 3/12/2017.
 * Class Test for ({@link MainPresenter})
 */
public class MainPresenterTest {

    private static final String USER_LOGIN_1 = "user_login_1";
    private static final String USER_LOGIN_2 = "user_login_2";

    @Mock
    MainActivity mView;
    @Mock
    UserRepository mUserRepository;
    @Mock
    Validator mValidator;
    @Mock
    CustomCompositeSubscription mSubscription;
    @Mock
    BaseSchedulerProvider mSchedulerProvider;

    private MainPresenter mMainPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainPresenter = new MainPresenter(mView, mUserRepository, mValidator, mSubscription,
                mSchedulerProvider);
    }

    @Test
    public void searchUsers() throws Exception {
        // Give
        List<User> users = new ArrayList<>();
        users.add(new User(USER_LOGIN_1));
        users.add(new User(USER_LOGIN_2));

        // When
        Mockito.when(mUserRepository.searchUsers(Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(Observable.just(users));

        // Then
        mMainPresenter.searchUsers(2, USER_LOGIN_1);

        //        Mockito.verify(mView, Mockito.never()).showError(null);
        //        Mockito.verify(mView).showListUser(usersList);

        // Give
        String errorMsg = "No internet";
        Throwable throwable = new Throwable(errorMsg);

        // When
        Mockito.when(mUserRepository.searchUsers(Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(Observable.<List<User>>error(throwable));

        // Then
        //        mMainPresenter.searchUsers(Mockito.anyInt(), Mockito.anyString());

        //        Mockito.verify(mView, Mockito.never()).showListUser(null);
        //        Mockito.verify(mView).showError(throwable);
    }
}
