package com.ovlesser.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.ovlesser.message.db.AppDatabase
import com.ovlesser.message.model.Message
import com.ovlesser.message.view.MessageFragment
import com.ovlesser.message.viewModel.MessageViewModel
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
