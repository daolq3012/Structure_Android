package com.fstyle.structure_android.data.source;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.local.realm.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by Sun on 3/11/2017.
 */
public class UserRepositoryTest {

    private static final String USER_LOGIN_1 = "abc";
    private static final String USER_LOGIN_2 = "def";

    @InjectMocks
    private UserRepository mUserRepository;
    @Mock
    UserLocalDataSource mLocalDataSource;
    @Mock
    UserRemoteDataSource mRemoteDataSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void searchUsers_200ResponseCode_InvokesCorrectApiCalls() {
        User user = new User(USER_LOGIN_1);
        User user2 = new User(USER_LOGIN_2);

        List<User> userReturns = new ArrayList<>();
        userReturns.add(user);
        userReturns.add(user2);
        // Given
        Mockito.when(mRemoteDataSource.searchUsers(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt())).thenReturn(Observable.just(userReturns));

        // When
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        mUserRepository.searchUsers(USER_LOGIN_1, 2).subscribe(subscriber);

        // Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<User>> onNextEvents = subscriber.getOnNextEvents();
        List<User> users = onNextEvents.get(0);
        Assert.assertEquals(USER_LOGIN_1, users.get(0).getLogin());
        Assert.assertEquals(USER_LOGIN_2, users.get(1).getLogin());
        Mockito.verify(mRemoteDataSource).searchUsers(USER_LOGIN_1, 2);
    }

    @Test
    public void searchUsers_OtherHttpError_SearchTerminatedWithError() {
        // Given
        Mockito.when(mRemoteDataSource.searchUsers(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()))
                .thenReturn(Observable.<List<User>>error(new HttpException(Response.error(403,
                        ResponseBody.create(MediaType.parse("application/json"), "Forbidden")))));

        // When
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        mUserRepository.searchUsers(USER_LOGIN_1, 2).subscribe(subscriber);

        // Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        Mockito.verify(mRemoteDataSource).searchUsers(USER_LOGIN_1, 2);
        Mockito.verify(mRemoteDataSource, Mockito.never()).searchUsers(USER_LOGIN_2, 2);
    }
}
