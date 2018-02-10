package edna.ceniza.com.clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Edna Ceniza on 06/02/2018.
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        var getResult: String = intent!!.getStringExtra("extra")

        var service_intent: Intent = Intent(context,RingtoneService::class.java)
        service_intent.putExtra("extra", getResult)
        context!!.startService(service_intent)

    }
}