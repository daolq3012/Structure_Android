package com.fstyle.appname.screens.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.fstyle.appname.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
    }
}
