package com.live.emmazone.activities.provider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.emmazone.R
import com.live.emmazone.adapter.MessageAdapter
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {

    var messageAdapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        clicksHandle()
        setMsgAdapter()
    }



    private fun clicksHandle() {
        rlBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setMsgAdapter() {
        messageAdapter = MessageAdapter()
        rvMessage.adapter = messageAdapter
    }
}