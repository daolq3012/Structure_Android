package com.ccc.nameapp.scenes.login.main

import javax.inject.Inject

interface MainLoginNavigator

class MainLoginNavigatorImpl @Inject constructor(fragment: MainLoginFragment) : MainLoginNavigator
