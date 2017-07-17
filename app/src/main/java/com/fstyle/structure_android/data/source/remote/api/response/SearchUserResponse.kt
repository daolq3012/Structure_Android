package com.fstyle.structure_android.data.source.remote.api.response

import com.fstyle.structure_android.data.model.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by DaoLQ on 17/07/2017.
 * Fstyle Ltd
 * daole.2511@gmail.com
 */
class SearchUserResponse : BaseRespone() {
  @SerializedName("total_count")
  @Expose
  var totalCount: Int? = null
  @SerializedName("items")
  @Expose
  var users: List<User> = arrayListOf()
}
