package com.ccc.nameproject.scenes.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ccc.nameproject.R

class MainActivity : AppCompatActivity() {

  companion object {
    fun getInstance(context: Context): Intent {
      return Intent(context, MainActivity::class.java)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}
