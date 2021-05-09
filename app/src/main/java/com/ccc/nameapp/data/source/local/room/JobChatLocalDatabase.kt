package com.ccc.nameapp.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ccc.nameapp.data.source.local.room.daos.UserDao
import com.ccc.nameapp.data.source.local.room.entities.UserEntity

/**
 * LobeLocalDatabase using Persistence Room
 */

@Database(entities = [UserEntity::class], version = 5, exportSchema = false)
abstract class JobChatLocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
