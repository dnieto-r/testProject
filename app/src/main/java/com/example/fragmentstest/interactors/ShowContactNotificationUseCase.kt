package com.example.fragmentstest.interactors

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.R
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class ShowContactNotificationUseCase(private val context: Context,
                                     private val myDatabase: Storage
) {

    private val channelId = "ForegroundService Kotlin"

    private fun updateNotification(contacts: List<User>) {
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0, notificationIntent, 0
        )

        val notification: Notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(context.getText(R.string.app_name))
            .setContentText("Hay ${contacts.size} contactos disponibles")
            .setSmallIcon(R.drawable.ic_search)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(0, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager? = context.getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    fun execute() {
        myDatabase.getRxUser().observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                Log.d("INFORMACIONM", it.toString())
                createNotificationChannel()
                updateNotification(it)
            }
    }


}
