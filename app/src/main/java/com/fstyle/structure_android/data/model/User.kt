package com.fstyle.structure_android.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class User() : BaseModel(), Parcelable {

  @SerializedName("login")
  @Expose
  var login: String? = null
  @SerializedName("id")
  @Expose
  var id: Int? = null
  @SerializedName("avatar_url")
  @Expose
  var avatarUrl: String? = null
  @SerializedName("gravatar_id")
  @Expose
  var gravatarId: String? = null
  @SerializedName("url")
  @Expose
  var url: String? = null
  @SerializedName("html_url")
  @Expose
  var htmlUrl: String? = null
  @SerializedName("followers_url")
  @Expose
  var followersUrl: String? = null
  @SerializedName("following_url")
  @Expose
  var followingUrl: String? = null
  @SerializedName("gists_url")
  @Expose
  var gistsUrl: String? = null
  @SerializedName("starred_url")
  @Expose
  var starredUrl: String? = null
  @SerializedName("subscriptions_url")
  @Expose
  var subscriptionsUrl: String? = null
  @SerializedName("organizations_url")
  @Expose
  var organizationsUrl: String? = null
  @SerializedName("repos_url")
  @Expose
  var reposUrl: String? = null
  @SerializedName("events_url")
  @Expose
  var eventsUrl: String? = null
  @SerializedName("received_events_url")
  @Expose
  var receivedEventsUrl: String? = null
  @SerializedName("type")
  @Expose
  var type: String? = null
  @SerializedName("site_admin")
  @Expose
  var siteAdmin: Boolean? = null
  @SerializedName("name")
  @Expose
  var name: String? = null
  @SerializedName("company")
  @Expose
  var company: String? = null
  @SerializedName("blog")
  @Expose
  var blog: String? = null
  @SerializedName("location")
  @Expose
  var location: String? = null
  @SerializedName("email")
  @Expose
  var email: String? = null
  @SerializedName("hireable")
  @Expose
  var hireable: String? = null
  @SerializedName("bio")
  @Expose
  var bio: String? = null
  @SerializedName("public_repos")
  @Expose
  var publicRepos: Int? = null
  @SerializedName("public_gists")
  @Expose
  var publicGists: Int? = null
  @SerializedName("followers")
  @Expose
  var followers: Int? = null
  @SerializedName("following")
  @Expose
  var following: Int? = null
  @SerializedName("created_at")
  @Expose
  var createdAt: String? = null
  @SerializedName("updated_at")
  @Expose
  var updatedAt: String? = null

  constructor(name: String) : this() {
    this.name = name
  }

  constructor(parcel: Parcel) : this() {
    login = parcel.readString()
    id = parcel.readValue(Int::class.java.classLoader) as? Int
    avatarUrl = parcel.readString()
    gravatarId = parcel.readString()
    url = parcel.readString()
    htmlUrl = parcel.readString()
    followersUrl = parcel.readString()
    followingUrl = parcel.readString()
    gistsUrl = parcel.readString()
    starredUrl = parcel.readString()
    subscriptionsUrl = parcel.readString()
    organizationsUrl = parcel.readString()
    reposUrl = parcel.readString()
    eventsUrl = parcel.readString()
    receivedEventsUrl = parcel.readString()
    type = parcel.readString()
    siteAdmin = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    name = parcel.readString()
    company = parcel.readString()
    blog = parcel.readString()
    location = parcel.readString()
    email = parcel.readString()
    hireable = parcel.readString()
    bio = parcel.readString()
    publicRepos = parcel.readValue(Int::class.java.classLoader) as? Int
    publicGists = parcel.readValue(Int::class.java.classLoader) as? Int
    followers = parcel.readValue(Int::class.java.classLoader) as? Int
    following = parcel.readValue(Int::class.java.classLoader) as? Int
    createdAt = parcel.readString()
    updatedAt = parcel.readString()
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(login)
    parcel.writeValue(id)
    parcel.writeString(avatarUrl)
    parcel.writeString(gravatarId)
    parcel.writeString(url)
    parcel.writeString(htmlUrl)
    parcel.writeString(followersUrl)
    parcel.writeString(followingUrl)
    parcel.writeString(gistsUrl)
    parcel.writeString(starredUrl)
    parcel.writeString(subscriptionsUrl)
    parcel.writeString(organizationsUrl)
    parcel.writeString(reposUrl)
    parcel.writeString(eventsUrl)
    parcel.writeString(receivedEventsUrl)
    parcel.writeString(type)
    parcel.writeValue(siteAdmin)
    parcel.writeString(name)
    parcel.writeString(company)
    parcel.writeString(blog)
    parcel.writeString(location)
    parcel.writeString(email)
    parcel.writeString(hireable)
    parcel.writeString(bio)
    parcel.writeValue(publicRepos)
    parcel.writeValue(publicGists)
    parcel.writeValue(followers)
    parcel.writeValue(following)
    parcel.writeString(createdAt)
    parcel.writeString(updatedAt)
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
