package com.live.emmazone.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.live.emmazone.adapter.ChatAdapter
import com.live.emmazone.base.SocketManager
import com.live.emmazone.databinding.ActivityChatBinding
import com.live.emmazone.extensionfuncton.getPreference
import com.live.emmazone.response_model.socket_response.ChatModel
import com.live.emmazone.response_model.socket_response.ChatResponse
import com.live.emmazone.response_model.socket_response.NewMsgResopnse
import com.live.emmazone.utils.AppConstants
import com.schunts.extensionfuncton.loadImage
import org.json.JSONArray
import org.json.JSONObject

class ChatActivity : AppCompatActivity(), SocketManager.SocketInterface {

    private lateinit var binding: ActivityChatBinding
    private var user2Id: String? = null

    private var chatAdapter: ChatAdapter? = null
    var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    private var chatList: ArrayList<ChatModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppConstants.SEND_CHAT_PUSH = false
        clicksHandle()
        setChatAdapter()
        getChat()
        chatReadUnread()

    }

    private fun chatReadUnread() {
        val jsonObject = JSONObject()
        jsonObject.put("senderId", user2Id)
        jsonObject.put("receiverId", getPreference(AppConstants.USER_ID, ""))
        SocketManager.sendDataToServer(SocketManager.READ_UNREAD, jsonObject)
    }


    private fun setChatAdapter() {
        chatAdapter = ChatAdapter(chatList)
        binding.rvChat.adapter = chatAdapter

        binding.rvChat.layoutManager = linearLayoutManager

        binding.rvChat.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                chatAdapter?.itemCount?.let {
                    linearLayoutManager.smoothScrollToPosition(
                        binding.rvChat,
                        null,
                        it
                    )
                }
            }
        }


    }

    private fun clicksHandle() {
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnSend.setOnClickListener {
            sendMsg()
        }
    }

    private fun sendMsg() {
        val msg = binding.edtMsg.text.toString().trim()

        if (msg.isNotEmpty()) {
            val jsonObject = JSONObject()
            jsonObject.put("senderId", getPreference(AppConstants.USER_ID, ""))
            jsonObject.put("receiverId", user2Id)
            jsonObject.put("messageType", "1") // 1=> text Msg
            jsonObject.put("message", msg)
            SocketManager.sendDataToServer(SocketManager.SEND_MSG, jsonObject)
        }

    }

    private fun getChat() {
        user2Id = intent.getStringExtra(AppConstants.USER2_ID)!!
        val user2Image = intent.getStringExtra(AppConstants.USER2_IMAGE)!!
        val user2Name = intent.getStringExtra(AppConstants.USER2_NAME)!!
        binding.civUser.loadImage(user2Image)
        binding.tvUserName.text = user2Name

        val jsonObject = JSONObject()
        jsonObject.put("senderId", getPreference(AppConstants.USER_ID, ""))
        jsonObject.put("receiverId", user2Id)
        SocketManager.sendDataToServer(SocketManager.GET_MSG, jsonObject)
    }

    override fun onSocketCall(event: String?, vararg args: Any?) {
        when (event) {
            SocketManager.GET_DATA_MSG_LISTENER -> {
                val mObject = (JSONArray(args[0]).get(0)) as JSONArray
                val getChatModel =
                    Gson().fromJson(mObject.toString(), ChatResponse::class.java)

                getChatModel.forEach {
                    val chatModel = ChatModel(
                        it.senderImage, it.receiverImage, it.message,
                        it.created.toString(), it.senderId.toString(), it.receiverId.toString()
                    )
                    chatList.add(chatModel)
                }
                chatAdapter?.notifyDataSetChanged()
                binding.rvChat.scrollToPosition(chatList.size - 1)
            }
            SocketManager.NEW_MSG_LISTENER -> {
                val mObject = (JSONArray(args[0]).get(0)) as JSONObject

                val newMsg =
                    Gson().fromJson(mObject.toString(), NewMsgResopnse::class.java)

                val chatModel = ChatModel(
                    AppConstants.IMAGE_USER_URL + newMsg.senderImage,
                    AppConstants.IMAGE_USER_URL + newMsg.receiverImage,
                    newMsg.message,
                    newMsg.created.toString(),
                    newMsg.senderId.toString(),
                    newMsg.receiverId.toString()
                )
                if (chatModel.senderId == getPreference(AppConstants.USER_ID, "")) {
                    binding.edtMsg.text.clear()
                }
                chatList.add(chatModel)
                chatAdapter?.notifyDataSetChanged()
                binding.rvChat.scrollToPosition(chatList.size - 1)
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
    }

    override fun onStop() {
        super.onStop()
        AppConstants.SEND_CHAT_PUSH = true
        SocketManager.unRegister(this)
    }
}