package com.ccc.nameapp.data.source.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ccc.nameapp.data.source.local.room.entities.UserEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM USER")
    fun getUser(): Flowable<List<UserEntity>>

    @Query("SELECT * FROM USER")
    fun getUserWithSingle(): Single<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("DELETE FROM USER")
    fun deleteUser()
}
