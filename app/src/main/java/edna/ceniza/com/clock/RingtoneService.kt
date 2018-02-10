package edna.ceniza.com.clock

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.support.v4.app.NotificationCompat

/**
 * Created by Edna Ceniza on 07/02/2018.
 */
class RingtoneService : Service() {
    companion object {
        lateinit var r: Ringtone
    }
    var id: Int = 0
    var isRinging: Boolean = false
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var state: String = intent!!.getStringExtra("extra")
        assert(state!=null)
        when (state){
            "on" -> id = 1
            "off" -> id = 0
        }
        if (!this.isRinging && id == 1){
            playAlarm()
            this.isRinging = true
            this.id = 0
            fireNotification()
        }
        else if (this.isRinging && id == 0){
            r.stop()
            this.isRinging = false
            this.id = 0
        }
        else if (!this.isRinging && id == 0){
            this.isRinging = false
            this.id = 0
        }
        else if (this.isRinging && id == 1){
            this.isRinging = true
            this.id = 1
        }
        else{

        }

        return START_NOT_STICKY
    }

    private fun fireNotification() {
        var set_alarm_activity : Intent = Intent(this, SetAlarmActivity::class.java)
        var pi: PendingIntent = PendingIntent.getActivity(this,0, set_alarm_activity, 0)
        var defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notify_manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notification: Notification = NotificationCompat.Builder(this)
                .setContentTitle("Alarm is going off")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultSoundUri)
                .setContentText("Click Me")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()

        notify_manager.notify(0, notification)
    }

    private fun playAlarm() {
        var alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri==null){
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        r = RingtoneManager.getRingtone(baseContext, alarmUri)
        r.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.isRinging = false
    }
}