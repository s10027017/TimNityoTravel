package com.example.tim.nityo.travel.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tim.nityo.travel.R
import com.example.tim.nityo.travel.base.BaseActivity
import com.example.tim.nityo.travel.databinding.ActivityMainBinding
import com.example.tim.nityo.travel.view.fragment.IndexFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun thisActivity(): AppCompatActivity {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarLight(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.authentication_fragment_container, IndexFragment())
            .commit()
    }

}