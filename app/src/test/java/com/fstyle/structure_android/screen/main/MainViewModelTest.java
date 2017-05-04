package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.rx.ImmediateSchedulerProvider;
import com.fstyle.structure_android.utils.validator.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by framgia on 03/05/2017.
 * Test for @{@link MainViewModel}
 */
@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @InjectMocks
    MainViewModel mMainViewModel;
    @Mock
    Validator mValidator;
    @Mock
    UserRepository mUserRepository;
    @InjectMocks
    ImmediateSchedulerProvider mSchedulerProvider;

    @Before
    public void setUp() throws Exception {
        mMainViewModel.setValidator(mValidator);
        mMainViewModel.setSchedulerProvider(mSchedulerProvider);
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
        mMainViewModel.validateKeywordInput(anyString());

        Assert.assertEquals(msg, mMainViewModel.getKeywordErrorMsg());
    }

    @Test
    public void validateKeywordInput_NGWord_invokesMessageNGWord() throws Exception {
        // Give
        String msg = "There are unusable characters";

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateNGWord(anyString())).thenReturn(msg);

        // Then
        mMainViewModel.validateKeywordInput(anyString());

        Assert.assertEquals(msg, mMainViewModel.getKeywordErrorMsg());
    }

    @Test
    public void validateKeywordInput_validKeyword_notShowError() throws Exception {

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateNGWord(anyString())).thenReturn("");

        // Then
        mMainViewModel.validateKeywordInput(anyString());

        Assert.assertEquals("", mMainViewModel.getKeywordErrorMsg());
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
        mMainViewModel.validateLimitNumberInput(anyString());

        Assert.assertEquals(msg, mMainViewModel.getLimitErrorMsg());
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
        mMainViewModel.validateLimitNumberInput(anyString());

        Assert.assertEquals(msg, mMainViewModel.getLimitErrorMsg());
    }

    @Test
    public void validateKeywordInput_validNumber_notShowError() throws Exception {

        // When
        when(mValidator.validateValueNonEmpty(anyString())).thenReturn("");
        when(mValidator.validateValueRangeFrom0to100(anyString())).thenReturn("");

        // Then
        mMainViewModel.validateLimitNumberInput(anyString());

        Assert.assertEquals("", mMainViewModel.getLimitErrorMsg());
    }
}
