package com.fstyle.structure_android.data.repository;

import android.database.sqlite.SQLiteDatabase;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.local.UserLocalDataSource;
import com.fstyle.structure_android.data.source.remote.UserRemoteDataSource;
import com.google.common.collect.Lists;
import io.reactivex.subscribers.TestSubscriber;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by daolq on 12/22/17.
 * Class Test for ({@link UserRepository})
 */
public class UserRepositoryTest {

    private static final String LIMIT_NUMBER = "3";
    private static final String KEYWORK = "abc";

    private static User user = new User("Name 1");
    private static List<User> users =
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

        mUserRepository = new UserRepository(mUserLocalDataSource, mUserRemoteDataSource);
        mListUserTestSubscriber = new TestSubscriber<>();
        mUserTestSubscriber = new TestSubscriber<>();
    }

    @After
    public void cleanUp() {
        UserRepository.destroyInstance();
    }

    public void searchUsers(String keyWord, String limit) throws Exception {

    }

    public void insertUser() throws Exception {
    }

    public void updateUser() throws Exception {
    }

    public void deleteUser() throws Exception {
    }

    public void insertOrUpdateUser() throws Exception {
    }

    public void getAllUser() throws Exception {
    }

    public void getUserByUserLogin() throws Exception {
    }

    private void setUserAvailable(SQLiteDatabase database, User user) {
    }
}
