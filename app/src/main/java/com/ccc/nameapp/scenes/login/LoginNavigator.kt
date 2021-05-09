package com.ccc.nameapp.scenes.login

import com.ccc.nameapp.scenes.BaseNavigator
import javax.inject.Inject

interface LoginNavigator

class LoginNavigatorImpl @Inject constructor(activity: LoginActivity) : BaseNavigator(activity),
    LoginNavigator
