package com.fstyle.structure_android.data.source;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.model.UsersList;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.fstyle.structure_android.data.source.remote.api.service.NameApi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
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

    @Mock
    NameApi mNameApi;

    private UserRepository mUserRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mUserRepository = new UserRepository(null, new UserRemoteDataSource(mNameApi));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void searchUsers200ResponseCodeInvokesCorrectApiCalls() {
        // Given
        Mockito.when(
                mNameApi.searchGithubUsers(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString()))
                .thenReturn(Observable.just(githubUserList()));

        // When
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        mUserRepository.searchUsers(2, USER_LOGIN_1).subscribe(subscriber);

        // Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<User>> onNextEvents = subscriber.getOnNextEvents();
        List<User> users = onNextEvents.get(0);
        Assert.assertEquals(USER_LOGIN_1, users.get(0).getLogin());
        Assert.assertEquals(USER_LOGIN_2, users.get(1).getLogin());
        Mockito.verify(mNameApi).searchGithubUsers(2, USER_LOGIN_1);
    }

    private UsersList githubUserList() {
        User user = new User(USER_LOGIN_1);

        User user2 = new User(USER_LOGIN_2);

        List<User> githubUsers = new ArrayList<>();
        githubUsers.add(user);
        githubUsers.add(user2);
        UsersList usersList = new UsersList(githubUsers);
        return usersList;
    }

    @Test
    public void searchUsersOtherHttpErrorSearchTerminatedWithError() {
        // Given
        Mockito.when(
                mNameApi.searchGithubUsers(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString()))
                .thenReturn(get403ForbiddenError());

        // When
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        mUserRepository.searchUsers(2, USER_LOGIN_1).subscribe(subscriber);

        // Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        Mockito.verify(mNameApi).searchGithubUsers(2, USER_LOGIN_1);
        Mockito.verify(mNameApi, Mockito.never()).searchGithubUsers(2, USER_LOGIN_2);
        Mockito.verify(mNameApi, Mockito.never()).getUser(USER_LOGIN_1);
    }

    private Observable<UsersList> get403ForbiddenError() {
        return Observable.error(new HttpException(Response.error(403,
                ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));
    }
}
