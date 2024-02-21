package com.example.rooans

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.rooans.databinding.ActivityMainBinding
import com.example.rooans.viewmodel.MainViewModel
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

//    private lateinit var repository: Repository
    private var mExitTime: Long = 0
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel()::class.java] }
    private val scopeWork = object : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = Job()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("--------onCreate","onCreate")
        Logger.addLogAdapter(AndroidLogAdapter())
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        CoroutineScope(Dispatchers.IO).launch {
//            deleteDatabase(Utils.database)
//            repository.createDb(applicationContext)
//        }
        viewModel.coil(this)
        viewModel.getData(this)
        viewModel.adapter(binding, this)
        viewModel.recycler(binding, this)
        binding.model = viewModel
//        repository.closeDb()
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("--------onRestart","onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.e("--------onStart","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("--------onResume","onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.e("--------onStop","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
        scopeWork.cancel()
//        repository.deleteTable()
        Log.e("--------onDestroy","onDestroy")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出App", Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()
            } else {
                finish()
            }
        }
        return true
    }
}