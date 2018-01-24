package com.fstyle.structure_android.data.repository;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserDataSource;
import com.fstyle.structure_android.data.source.local.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.subscribers.TestSubscriber;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by daolq on 12/22/17.
 * Class Test for ({@link UserRepository})
 */
public class UserRepositoryTest {

    private static final String LIMIT_NUMBER = "3";
    private static final String KEYWORK = "abc";

    private static User mUser = new User("Name 1");
    private static List<User> mUsers =
            Lists.newArrayList(new User("Name 2"), new User("Name 3"), new User("Name 4"));

    private UserRepository mUserRepository;

    private TestSubscriber<List<User>> mListUserTestSubscriber;
    private TestSubscriber<User> mUserTestSubscriber;

    @Mock
    private UserLocalDataSource mUserLocalDataSource;
    @Mock
    private UserRemoteDataSource mUserRemoteDataSource;

    @Before
    public void setup() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        mUserRepository = UserRepository.getInstance(mUserLocalDataSource, mUserRemoteDataSource);
        mListUserTestSubscriber = new TestSubscriber<>();
        mUserTestSubscriber = new TestSubscriber<>();
    }

    @After
    public void tearDown() throws Exception {
        UserRepository.destroyInstance();
    }

    @Test
    public void insertUser() throws Exception {
    }

    @Test
    public void updateUser() throws Exception {
    }

    @Test
    public void deleteUser() throws Exception {
    }

    @Test
    public void insertOrUpdateUser() throws Exception {
    }

    @Test
    public void getAllUser_repositoryCachesAfterFirstSubscription_whenTasksAvailableInLocalStorage() throws Exception {
        // Given that the local data source has data available
        setUsersAvailable(mUserLocalDataSource, mUsers);
        // And the remote data source does not have any data available
        setUsersNotAvailable(mUserRemoteDataSource);

        // When two subscriptions are set
        TestSubscriber<List<User>> testSubscriber1 = new TestSubscriber<>();
        mUserRepository.getUsers().subscribe(testSubscriber1);

        TestSubscriber<List<User>> testSubscriber2 = new TestSubscriber<>();
        mUserRepository.getUsers().subscribe(testSubscriber2);

        // Then users were only requested once from remote and local sources
        verify(mUserRemoteDataSource).getUsers();
        verify(mUserLocalDataSource).getUsers();
        //
        assertFalse(mUserRepository.mCacheIsDirty);
        testSubscriber1.assertValue(mUsers);
//        testSubscriber2.assertValue(mUsers);
    }

    @Test
    public void findUserByUserLogin() throws Exception {
    }

    @Test
    public void deleteAllUsers() throws Exception {
    }

    private void setUsersNotAvailable(UserDataSource dataSource) {
        when(dataSource.getUsers()).thenReturn(Flowable.just(Collections.emptyList()));
    }

    private void setUsersAvailable(UserDataSource dataSource, List<User> users) {
        when(dataSource.getUsers()).thenReturn(Flowable.just(users).concatWith(Flowable.never()));
    }

    private void setTaskNotAvailable(UserLocalDataSource dataSource, String userLogin) {
        when(dataSource.getUser(eq(userLogin))).thenReturn(Flowable.just(null));
    }

    private void setTaskAvailable(UserLocalDataSource dataSource, User user) {
        when(dataSource.getUser(eq(user.getLogin()))).thenReturn(Flowable.just(user));
    }
}
