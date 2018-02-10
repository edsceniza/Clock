package edna.ceniza.com.clock

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_alarm.setOnClickListener{
            val anIntent = Intent(this, SetAlarmActivity::class.java)
            startActivity(anIntent)
        }

        btn_stopwatch.setOnClickListener {
            val anIntent = Intent (this, StopwatchActivity::class.java)
            startActivity(anIntent)
        }

    }


    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
