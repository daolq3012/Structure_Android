package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.rx.ImmediateSchedulerProvider;
import com.fstyle.structure_android.utils.validator.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import rx.Observable;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Sun on 3/12/2017.
 * Class Test for ({@link MainPresenter})
 */
@RunWith(MockitoJUnitRunner.class)
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
        mMainPresenter.setView(mMainView);
        mMainPresenter.setSchedulerProvider(mSchedulerProvider);
    }

    /**
     * #validateKeywordInput
     */
    @Test
    public void whenEmptyThenInvokesMessageNoEmptyAndReturnFalse() throws Exception {
        // Give
        boolean expect = false;
        String msg = "Must not empty";

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn(msg);

        // Then
        boolean actual = mMainPresenter.validateKeywordInput(anyString());

        verify(mMainView).onInvalidKeyWord(msg);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void whenInputNGWordThenInvokesMessageNGWordAndReturnFalse() throws Exception {
        // Give
        boolean expect = false;
        String msg = "There are unusable characters";

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateNGWord(anyString())).thenReturn(msg);

        // Then
        boolean actual = mMainPresenter.validateKeywordInput(anyString());

        verify(mMainView).onInvalidKeyWord(msg);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void whenInputKeywordValidThenReturnTrue() throws Exception {
        // Give
        boolean expect = true;

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateNGWord(anyString())).thenReturn("");

        // Then
        boolean actual = mMainPresenter.validateKeywordInput(anyString());

        Assert.assertEquals(expect, actual);
    }

    /**
     * #validateLimitNumberInput
     */
    @Test
    public void whenInputEmptyThenInvokesMessageNoEmptyAndReturnFalse() throws Exception {
        // Give
        boolean expect = false;
        String msg = "Must not empty";

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn(msg);

        // Then
        boolean actual = mMainPresenter.validateLimitNumberInput(anyString());

        verify(mMainView).onInvalidLimitNumber(msg);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void whenInputInvalidNumberThenInvokesMessageAndReturnFalse() throws Exception {
        // Give
        boolean expect = false;
        String msg = "Limit Value must be from 0 to 100";

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateValueRangeFrom0to100(anyString())).thenReturn(msg);

        // Then
        boolean actual = mMainPresenter.validateLimitNumberInput(anyString());

        verify(mMainView).onInvalidLimitNumber(msg);
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void whenInputNumberValidThenReturnTrue() throws Exception {
        // Give
        boolean expect = true;

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateValueRangeFrom0to100(anyString())).thenReturn("");

        // Then
        boolean actual = mMainPresenter.validateLimitNumberInput(anyString());

        Assert.assertEquals(expect, actual);
    }

    /**
     * #searchUsers
     */
    @Test
    public void whenInputValidDataInvokesListUsers() throws Exception {
        // Give
        List<User> users = new ArrayList<>();
        users.add(new User(USER_LOGIN_1));
        users.add(new User(USER_LOGIN_2));

        // When
        when(mUserRepository.searchUsers(Mockito.anyInt(), Mockito.anyString())).thenReturn(
                Observable.just(users));

        // Then
        mMainPresenter.searchUsers(2, USER_LOGIN_1);

        verify(mMainView, Mockito.never()).onSearchError(null);
        verify(mMainView).onSearchUsersSuccess(users);
    }

    @Test
    public void whenInputValidDataAndNetworkErrorInvokesError() throws IllegalAccessException {
        // Give
        String errorMsg = "No internet";
        Throwable throwable = new Throwable(errorMsg);

        // When
        when(mUserRepository.searchUsers(Mockito.anyInt(), Mockito.anyString())).thenReturn(
                Observable.<List<User>>error(throwable));

        // Then
        mMainPresenter.searchUsers(2, USER_LOGIN_1);

        verify(mMainView, Mockito.never()).onSearchUsersSuccess(null);
        verify(mMainView).onSearchError(throwable);
    }
}
