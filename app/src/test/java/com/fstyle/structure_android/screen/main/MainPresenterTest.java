package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.rx.ImmediateSchedulerProvider;
import com.fstyle.structure_android.utils.validator.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Sun on 3/12/2017.
 * Class Test for ({@link MainPresenter})
 */
public class MainPresenterTest {

    private static final String USER_LOGIN_1 = "user_login_1";
    private static final String USER_LOGIN_2 = "user_login_2";

    @InjectMocks
    MainPresenter mMainPresenter;
    @Mock
    MainContract.View mMainView;
    @Mock
    UserRepository mUserRepository;
    @Mock
    Validator mValidator;
    @InjectMocks
    ImmediateSchedulerProvider mSchedulerProvider;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mMainPresenter.setSchedulerProvider(mSchedulerProvider);
    }

    @Test
    public void searchUsersWhenValidDataInputThenInvokesListUsers() throws Exception {
        // Give
        List<User> users = new ArrayList<>();
        users.add(new User(USER_LOGIN_1));
        users.add(new User(USER_LOGIN_2));

        // When
        when(mUserRepository.searchUsers(Mockito.anyInt(), Mockito.anyString())).thenReturn(
                Observable.just(users));
        when(mValidator.validateAll()).thenReturn(true);
        mMainPresenter.searchUsers(2, USER_LOGIN_1);

        // Then
        verify(mMainView, Mockito.never()).onSearchError(null);
        verify(mMainView).onSearchUsersSuccess(users);
    }

    @Test
    public void searchUsersWhenValidDataInputNetworkErrorThenInvokesError() throws Exception {
        // Give
        String errorMsg = "No internet";
        Throwable throwable = new Throwable(errorMsg);

        // When
        when(mUserRepository.searchUsers(Mockito.anyInt(), Mockito.anyString())).thenReturn(
                Observable.<List<User>>error(throwable));
        when(mValidator.validateAll()).thenReturn(true);
        mMainPresenter.searchUsers(2, USER_LOGIN_1);

        // Then
        verify(mMainView, Mockito.never()).onSearchUsersSuccess(null);
        verify(mMainView).onSearchError(throwable);
    }
}
