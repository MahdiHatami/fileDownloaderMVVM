package com.metis.downloader.data


data class User(var username: String, var password: String) {

  val isEmpty
    get() = username.isEmpty() || password.isEmpty()
}
