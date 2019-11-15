package com.metis.downloader.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metis.downloader.Event
import com.metis.downloader.R
import com.metis.downloader.data.User

class LoginViewModel : ViewModel() {

  val username = MutableLiveData<String>()
  val password = MutableLiveData<String>()

  private val _snackbarText = MutableLiveData<Event<Int>>()
  val snackbarMessage: LiveData<Event<Int>> = _snackbarText

  private val _navigateToFiles = MutableLiveData<Event<Unit>>()
  val navigateToFiles: LiveData<Event<Unit>> = _navigateToFiles

  fun loginBtnOnClick() {
    val usernameStr = username.value
    val passwordStr = password.value

    if (usernameStr == null || passwordStr == null) {
      _snackbarText.value = Event(R.string.fill_login_credential)
      return
    }

    if (User(usernameStr, passwordStr).isEmpty) {
      _snackbarText.value = Event(R.string.fill_login_credential)
      return
    }

    _navigateToFiles.value = Event(Unit)
  }
}
