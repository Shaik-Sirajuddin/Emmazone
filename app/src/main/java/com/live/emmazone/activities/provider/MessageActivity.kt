package com.live.emmazone.activities.provider

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.live.emmazone.R
import com.live.emmazone.activities.main.ChatActivity
import com.live.emmazone.adapter.MessageAdapter
import com.live.emmazone.base.SocketManager
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.response_model.socket_response.ChatListResponse
import com.live.emmazone.utils.AppConstants
import kotlinx.android.synthetic.main.activity_message.*
import org.json.JSONArray
import org.json.JSONObject

class MessageActivity : AppCompatActivity(), SocketManager.SocketInterface {

    private var messageAdapter: MessageAdapter? = null
    private val listMsg = ArrayList<ChatListResponse.ChatListResponseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        clicksHandle()
        setMsgAdapter()

    }

    private fun getChatList() {
        val jsonObj = JSONObject()
        jsonObj.put("userId", getPreference(AppConstants.USER_ID, ""))
        SocketManager.sendDataToServer(SocketManager.CHAT_LISTING, jsonObj)
    }


    private fun clicksHandle() {
        rlBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setMsgAdapter() {
        messageAdapter = MessageAdapter(listMsg)
        rvMessage.adapter = messageAdapter

        messageAdapter?.onItemCLick = { pos, clickOn ->
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(AppConstants.USER2_NAME, listMsg[pos].userName)
            intent.putExtra(
                AppConstants.USER2_IMAGE,
                AppConstants.IMAGE_USER_URL + listMsg[pos].image
            )
            intent.putExtra(AppConstants.USER2_ID, listMsg[pos].otherUserId.toString())
            startActivity(intent)
        }
    }

    override fun onSocketCall(event: String?, vararg args: Any?) {
        when (event) {
            SocketManager.CHAT_MSG_LISTENER -> {
                val mObject = (JSONArray(args[0]).get(0)) as JSONArray

                val chatListModel =
                    Gson().fromJson(mObject.toString(), ChatListResponse::class.java)

                listMsg.clear()
                listMsg.addAll(chatListModel)
                messageAdapter?.notifyDataSetChanged()

                if (listMsg.isNotEmpty()) {
                    rvMessage.visibility = View.VISIBLE
                    tvNoData.visibility = View.GONE
                } else {
                    rvMessage.visibility = View.GONE
                    tvNoData.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onSocketConnect(vararg args: Any?) {

    }

    override fun onSocketDisConnect(vararg args: Any?) {

    }

    override fun onError(event: String?, vararg args: Any?) {

    }

    override fun onResume() {
        super.onResume()
        SocketManager.onRegister(this)
        getChatList()
    }
}