package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.rx.ImmediateSchedulerProvider;
import com.fstyle.structure_android.utils.validator.Validator;
import java.util.ArrayList;
import java.util.List;
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
    MainViewModel mMainViewModel;
    @Mock
    UserRepository mUserRepository;
    @Mock
    Validator mValidator;
    @InjectMocks
    ImmediateSchedulerProvider mSchedulerProvider;

    @Before
    public void setUp() throws Exception {
        mMainPresenter.setSchedulerProvider(mSchedulerProvider);
        mMainViewModel.setPresenter(mMainPresenter);
    }

    /**
     * #validateKeywordInput
     */
    @Test
    public void validateKeywordInput_emptyKeyword_invokesMessageNoEmpty() throws Exception {
        // Give
        String msg = "Must not empty";

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn(msg);

        // Then
        mMainPresenter.validateKeywordInput(anyString());

        verify(mMainViewModel).onInvalidKeyWord(msg);
    }

    @Test
    public void validateKeywordInput_NGWord_invokesMessageNGWord() throws Exception {
        // Give
        String msg = "There are unusable characters";

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateNGWord(anyString())).thenReturn(msg);

        // Then
        mMainPresenter.validateKeywordInput(anyString());

        verify(mMainViewModel).onInvalidKeyWord(msg);
    }

    @Test
    public void validateKeywordInput_validKeyword_notShowError() throws Exception {

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateNGWord(anyString())).thenReturn("");

        // Then
        mMainPresenter.validateKeywordInput(anyString());

        verify(mMainViewModel).onInvalidKeyWord("");
    }

    /**
     * #validateLimitNumberInput
     */
    @Test
    public void validateLimitNumberInput_emptyNumber_invokesMessageNoEmpty() throws Exception {
        // Give
        String msg = "Must not empty";

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn(msg);

        // Then
        mMainPresenter.validateLimitNumberInput(anyString());

        verify(mMainViewModel).onInvalidLimitNumber(msg);
    }

    @Test
    public void validateLimitNumberInput_numberValueRangeFrom0To100_invokesMessageLimitValue()
            throws Exception {
        // Give
        String msg = "Limit Value must be from 0 to 100";

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateValueRangeFrom0to100(anyString())).thenReturn(msg);

        // Then
        mMainPresenter.validateLimitNumberInput(anyString());

        verify(mMainViewModel).onInvalidLimitNumber(msg);
    }

    @Test
    public void validateKeywordInput_validNumber_notShowError() throws Exception {

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateValueRangeFrom0to100(anyString())).thenReturn("");

        // Then
        mMainPresenter.validateLimitNumberInput(anyString());

        verify(mMainViewModel).onInvalidLimitNumber("");
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
        when(mUserRepository.searchUsers(Mockito.anyString(), Mockito.anyInt())).thenReturn(
                Observable.just(users));

        // Then
        mMainPresenter.searchUsers(USER_LOGIN_1, 2);

        verify(mMainViewModel, Mockito.never()).onSearchError(null);
        verify(mMainViewModel).onSearchUsersSuccess(users);
    }

    @Test
    public void whenInputValidDataAndNetworkErrorInvokesError() throws IllegalAccessException {
        // Give
        String errorMsg = "No internet";
        Throwable throwable = new Throwable(errorMsg);

        // When
        when(mUserRepository.searchUsers(Mockito.anyString(), Mockito.anyInt())).thenReturn(
                Observable.<List<User>>error(throwable));

        // Then
        mMainPresenter.searchUsers(USER_LOGIN_1, 2);

        verify(mMainViewModel, Mockito.never()).onSearchUsersSuccess(null);
        verify(mMainViewModel).onSearchError(throwable);
    }
}
