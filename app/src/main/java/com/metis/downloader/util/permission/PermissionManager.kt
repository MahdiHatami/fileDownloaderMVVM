package com.metis.downloader.util.permission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.metis.downloader.util.permission.PermissionStatus.CAN_ASK_PERMISSION
import com.metis.downloader.util.permission.PermissionStatus.PERMISSION_DENIED
import com.metis.downloader.util.permission.PermissionStatus.PERMISSION_GRANTED

class PermissionManager {
  fun getPermissionStatus(
    @NonNull activity: Activity,
    @NonNull permission: String
  ): PermissionStatus {
    return when {
      PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
        activity,
        permission
      ) -> PERMISSION_GRANTED
      ActivityCompat.shouldShowRequestPermissionRationale(
        activity,
        permission
      ) -> CAN_ASK_PERMISSION
      else -> PERMISSION_DENIED
    }
  }
}
