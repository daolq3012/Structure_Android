package com.fstyle.structure_android.data.source;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.After;
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
    UserRepositoryImpl mUserRepository;
    @Mock
    UserRemoteDataSource mRemoteDataSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void searchUsers200ResponseCodeInvokesCorrectApiCalls() {
        // Given
        Mockito.when(mRemoteDataSource.searchUsers(ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString())).thenReturn(Observable.just(githubUserList()));

        // When
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        mUserRepository.searchUsers(2, USER_LOGIN_1).subscribe(subscriber);

        List<List<User>> onNextEvents = subscriber.getOnNextEvents();
        List<User> usersList = onNextEvents.get(0);
        Assert.assertEquals(USER_LOGIN_1, usersList.get(0).getLogin());
        Assert.assertEquals(USER_LOGIN_2, usersList.get(1).getLogin());
        Mockito.verify(mRemoteDataSource).searchUsers(2, USER_LOGIN_1);
    }

    private List<User> githubUserList() {
        User user = new User(USER_LOGIN_1);

        User user2 = new User(USER_LOGIN_2);

        List<User> githubUsers = new ArrayList<>();
        githubUsers.add(user);
        githubUsers.add(user2);
        return githubUsers;
    }

    @Test
    public void searchUsersOtherHttpErrorSearchTerminatedWithError() {
        // Given
        Mockito.when(mRemoteDataSource.searchUsers(ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString())).thenReturn(get403ForbiddenError());

        // When
        TestSubscriber<List> subscriber = new TestSubscriber<>();
        mUserRepository.searchUsers(2, USER_LOGIN_1).subscribe(subscriber);

        Mockito.verify(mRemoteDataSource).searchUsers(2, USER_LOGIN_1);
        Mockito.verify(mRemoteDataSource, Mockito.never()).searchUsers(2, USER_LOGIN_2);
    }

    private Observable<List<User>> get403ForbiddenError() {
        return Observable.error(new HttpException(Response.error(403,
                ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));
    }
}
