package com.demo.swt.mystudyappshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ComboActivity : AppCompatActivity() {
    lateinit var view11: ComboProgressView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combo)
        view11 = findViewById(R.id.combo_progress)
    }

    var combuNum = 0
    fun zhuabaoClick(view: View?) {
        view11.addComboNumber(80f, ++combuNum)
        //        startActivity(new Intent(this, MonitorMainActivity.class));
    }

    fun back(view: View?) {
        view11.backAnimator()
    }


}