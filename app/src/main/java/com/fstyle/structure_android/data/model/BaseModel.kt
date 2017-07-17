package com.fstyle.structure_android.data.model

import com.google.gson.Gson

/**
 * Created by le.quang.dao on 16/03/2017.
 */

abstract class BaseModel : Cloneable {

  @Throws(CloneNotSupportedException::class)
  public override fun clone(): BaseModel {
    super.clone()
    val gson = Gson()
    return gson.fromJson(gson.toJson(this), this.javaClass)
  }
}
