package lv.dt.todoit

import android.content.Context
import androidx.multidex.MultiDexApplication

class App : MultiDexApplication() {

    companion object {
        fun from(context: Context) = context.applicationContext as App
    }
}