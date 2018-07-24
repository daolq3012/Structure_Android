package com.ccc.nameproject.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by daolq on 5/17/18.
 * nameproject_Android
 */
class User() : Parcelable {
  @Expose
  var id: Int? = null
  @Expose
  var name: String? = null
  @Expose
  @SerializedName("src_small")
  var avatar: String? = null
  @Expose
  var email: String? = null
  @Expose
  var phoneNumber: String? = null
  @Expose
  var address: String? = null
  @Expose
  var birthday: String? = null

  constructor(parcel: Parcel) : this() {
    id = parcel.readValue(Int::class.java.classLoader) as? Int
    name = parcel.readString()
    avatar = parcel.readString()
    email = parcel.readString()
    phoneNumber = parcel.readString()
    address = parcel.readString()
    birthday = parcel.readString()
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeValue(id)
    parcel.writeString(name)
    parcel.writeString(avatar)
    parcel.writeString(email)
    parcel.writeString(phoneNumber)
    parcel.writeString(address)
    parcel.writeString(birthday)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<User> {
    override fun createFromParcel(parcel: Parcel): User {
      return User(parcel)
    }

    override fun newArray(size: Int): Array<User?> {
      return arrayOfNulls(size)
    }
  }
}
