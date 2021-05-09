package com.ccc.nameapp.data.source.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity {

    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var email: String = ""
    var address: String = ""
    var avatar: String = ""
    var birthday: String = ""
    var company: String = ""
    var description: String = ""
}
