package kr.puze.coordinatorexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_main2)

//        setSupportActionBar(toolbar)
//        var actionbar = supportActionBar
//        actionbar!!.title = ""
    }
}
