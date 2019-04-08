package lv.dt.todoit.ui.main

import android.os.Bundle
import lv.dt.todoit.R
import lv.dt.todoit.ui.core.AppActivity

class MainActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}