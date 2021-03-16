package com.fstyle.appname.utils

import android.app.Activity
import androidx.fragment.app.Fragment

interface PermissionManager

class PermissionManagerImpl(
    private val activity: Activity,
    private val fragment: Fragment? = null
) : PermissionManager
