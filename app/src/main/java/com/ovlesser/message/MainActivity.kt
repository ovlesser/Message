package com.ovlesser.message

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.ovlesser.message.view.MessageFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var dataRepository: DataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MainApplication).component.inject(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content, MessageFragment())
            .disallowAddToBackStack()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onPause() {
        super.onPause()
        dataRepository.saveAllMessage()
    }
}
