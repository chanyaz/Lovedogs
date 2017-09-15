package br.com.tairoroberto.lovedogs.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import br.com.tairoroberto.lovedogs.R
import br.com.tairoroberto.lovedogs.login.view.SplashActivity
import br.com.tairoroberto.lovedogs.settings.ConfigDAO
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 * Created by tairo on 9/8/17.
 */
class CustomFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage?.data?.isNotEmpty() == true) {

            val parametroScreen = remoteMessage.data["screen"]

            if (!TextUtils.isEmpty(parametroScreen)) {
                showNotification("screen", parametroScreen)
            }
        }

        if (remoteMessage?.notification != null) {
            showNotification(remoteMessage.notification.title, remoteMessage.notification.body)
        }
    }

    private fun showNotification(title: String?, body: String?) {
        val intent = Intent(this, SplashActivity::class.java)
        val config = ConfigDAO(this.applicationContext).getById(1)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val builder = NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_dog)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        if (config?.notification == true) {
            if (!TextUtils.isEmpty(config.sound_notification)) {
                val defaultSoundUri = Uri.parse(config.sound_notification)
                builder.setSound(defaultSoundUri)
            } else {
                val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                builder.setSound(defaultSoundUri)
            }
        }

        val notification = builder.build()

        if (config?.vibrate == true) {
            notification.vibrate = longArrayOf(150, 300, 150, 600)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }
}