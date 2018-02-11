package edna.ceniza.com.clock

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import java.util.*

class SetAlarmActivity : AppCompatActivity() {

    lateinit var am : AlarmManager
    lateinit var tp : TimePicker
    lateinit var con : Context
    lateinit var btnStart : Button
    lateinit var btnStop : Button
    var hour: Int = 0
    var min: Int = 0
    lateinit var pi: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)
        this.con = this
        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        tp = findViewById(R.id.time_picker) as TimePicker
        btnStart = findViewById(R.id.btn_set) as Button
        btnStop = findViewById(R.id.btn_stop) as Button
        var calendar: Calendar = Calendar.getInstance()
        var myIntent: Intent = Intent(this,AlarmReceiver::class.java)
        btnStart.setOnClickListener(object : View.OnClickListener{
            @TargetApi(Build.VERSION_CODES.KITKAT)
            override fun onClick(v: View?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calendar.set(Calendar.HOUR_OF_DAY,tp.hour)
                    calendar.set(Calendar.MINUTE,tp.minute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = tp.hour
                    min = tp.minute
                }
                else{
                    calendar.set(Calendar.HOUR_OF_DAY, tp.currentHour)
                    calendar.set(Calendar.MINUTE, tp.currentMinute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = tp.currentHour
                    min = tp.currentMinute
                }
                var hr_str:String = hour.toString()
                var min_str:String = min.toString()

                var ampm: Int = 0
                ampm = calendar.get(Calendar.AM_PM)
                val ampmStr = if (ampm === 0) "AM" else "PM"

                if (hour > 12){
                    hr_str = (hour - 12).toString()
                }
                if (min < 10){
                    min_str = "0$min"
                }
//                set_alarm_text("Alarm set to: $hr_str : $min_str")

                val context = applicationContext
                val text = "Alarm set to $hr_str : $min_str "
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, text + ampmStr, duration)
                toast.show()

                myIntent.putExtra("extra", "on")
                pi = PendingIntent.getBroadcast(this@SetAlarmActivity,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT)
                am.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pi)
            }
        })
        btnStop.setOnClickListener(object  : View.OnClickListener{
            override fun onClick(v: View?) {
//                set_alarm_text("Alarm off")
                myIntent.putExtra("extra", "off")
                pi = PendingIntent.getBroadcast(this@SetAlarmActivity,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT)
                am.cancel (pi)
                sendBroadcast(myIntent)

                val context = applicationContext
                val text = "Alarm has been stopped."
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }

        })
    }
}
