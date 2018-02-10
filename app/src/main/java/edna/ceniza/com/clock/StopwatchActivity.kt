package edna.ceniza.com.clock

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.os.Handler
import java.util.Arrays
import java.util.ArrayList


class StopwatchActivity : AppCompatActivity() {

    internal lateinit var textView: TextView

    internal lateinit var start: Button
    internal lateinit var pause: Button
    internal lateinit var reset: Button
    internal lateinit var lap: Button

    internal var MillisecondTime: Long = 0
    internal var StartTime: Long = 0
    internal var TimeBuff: Long = 0
    internal var UpdateTime = 0L

    internal lateinit var handler: Handler

    internal var Seconds: Int = 0
    internal var Minutes: Int = 0
    internal var MilliSeconds: Int = 0

    internal lateinit var listView: ListView

    internal var ListElements = arrayOf<String>()

    internal lateinit var ListElementsArrayList: MutableList<String>

    internal lateinit var adapter: ArrayAdapter<String>

    var runnable: Runnable = object : Runnable {

        override fun run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime

            UpdateTime = TimeBuff + MillisecondTime

            Seconds = (UpdateTime / 1000).toInt()

            Minutes = Seconds / 60

            Seconds = Seconds % 60

            MilliSeconds = (UpdateTime % 1000).toInt()
            MilliSeconds = MilliSeconds/10

            textView.text = ("" + java.lang.String.format("%02d", Minutes) + ":"
                    + java.lang.String.format("%02d", Seconds) + "."
                    + MilliSeconds)

            handler.postDelayed(this, 0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        textView = findViewById<View>(R.id.textView) as TextView
        start = findViewById<View>(R.id.btn_start) as Button
        pause = findViewById<View>(R.id.btn_pause) as Button
        reset = findViewById<View>(R.id.btn_reset) as Button
        lap = findViewById<View>(R.id.btn_savelap) as Button
        listView = findViewById<View>(R.id.listview1) as ListView
        handler = Handler()

        ListElementsArrayList = ArrayList(Arrays.asList(*ListElements))

        adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        )

        listView.adapter = adapter

        start.setOnClickListener {
            StartTime = SystemClock.uptimeMillis()
            handler.postDelayed(runnable, 0)

            reset.isEnabled = false
        }

        pause.setOnClickListener {
            TimeBuff += MillisecondTime

            handler.removeCallbacks(runnable)

            reset.isEnabled = true
        }

        reset.setOnClickListener {
            MillisecondTime = 0L
            StartTime = 0L
            TimeBuff = 0L
            UpdateTime = 0L
            Seconds = 0
            Minutes = 0
            MilliSeconds = 0

            textView.text = "00:00.00"

            ListElementsArrayList.clear()

            adapter.notifyDataSetChanged()
        }

        lap.setOnClickListener {
            ListElementsArrayList.add(textView.text.toString())

            adapter.notifyDataSetChanged()
        }
    }
}

