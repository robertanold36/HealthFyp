package com.example.health.chat

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.health.R
import com.example.health.chat.adapter.ChatFromAdapter
import com.example.health.chat.adapter.ChatToAdapter
import com.example.health.model.ChatModel
import com.example.health.model.Doctor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import java.util.*

class ChatActivity : AppCompatActivity() {
    private val args: ChatActivityArgs by navArgs()
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    private val senderId = firebaseAuth.uid ?: ""
    val time = Calendar.getInstance().time.toString()
    private val adapter = GroupAdapter<ViewHolder>()
    private var rvChat: RecyclerView? = null
    private var sendBtn: ImageButton? = null
    private var messageText: EditText? = null

    companion object {
        var chat:ChatModel?=null
        var doctor: Doctor? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        doctor = args.doctorDetails
        supportActionBar?.title = doctor?.name

        rvChat = findViewById(R.id.chat_recyclerview)
        sendBtn = findViewById(R.id.send)
        messageText = findViewById(R.id.chat_message)

        rvChat?.adapter = adapter

        loadMsg()

        sendBtn?.setOnClickListener {
            val message = messageText?.text.toString()
            if(message.isEmpty()){

            }else{
                sendMsg(message)
                messageText?.text?.clear()
                rvChat?.scrollToPosition(adapter.itemCount - 1)
            }

        }
    }

    private fun sendMsg(message: String) {
        val receiverId = doctor?.doctorId
        val chatModel = ChatModel(message, senderId, receiverId!!, false, time)
        val reference = firebaseDatabase.getReference("/messages/${senderId}/${receiverId}").push()
        val toReference =
            firebaseDatabase.getReference("/messages/${receiverId}/${senderId}").push()
        val latestReference = firebaseDatabase.getReference("/latest/${senderId}/${receiverId}")
        val latestToReference = firebaseDatabase.getReference("/latest/${receiverId}/${senderId}")

        reference.setValue(chatModel)
        toReference.setValue(chatModel)
        latestReference.setValue(chatModel)
        latestToReference.setValue(chatModel)
    }

    private fun loadMsg() {

        val receiverId = doctor?.doctorId
        val reference = firebaseDatabase.getReference("/messages/${senderId}/${receiverId}")

        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, previousChildName: String?) {
                if (p0.exists()) {
                    Log.e("Testing",p0.toString())
                    p0.getValue(ChatModel::class.java).also { chat = it }
                    if (chat?.senderId == senderId) {
                        chat!!.message?.let { ChatToAdapter(it) }?.let { adapter.add(it) }
                    } else {
                        chat!!.message?.let { ChatFromAdapter(it) }?.let { adapter.add(it) }
                    }

                    rvChat?.scrollToPosition(adapter.itemCount - 1)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                chat=snapshot.getValue(ChatModel::class.java)
                if(chat?.senderId==senderId){
                    chat!!.message?.let { ChatFromAdapter(it) }?.let { adapter.remove(it) }
                }else{
                    chat!!.message?.let { ChatToAdapter(it) }?.let { adapter.remove(it) }

                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}