package com.example.finle_project.View.home.chat_fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.Model.Message
import com.example.finle_project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

private lateinit var messageRecyclerView :RecyclerView
private lateinit var messageBox:EditText
private lateinit var sentButton: ImageView
private lateinit var messageAdapter: MessageAdapter
private lateinit var messageList: ArrayList<Message>
private lateinit var mDbRef :DatabaseReference

var receiverRoom:String? = null
var senderRoom:String? = null


class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()
        senderRoom = receverUid+ senderUid
        receiverRoom = senderUid+receverUid

        supportActionBar?.title = name

        messageRecyclerView = findViewById(R.id.recyclerView)
        messageBox = findViewById(R.id.messageBox)
        sentButton= findViewById(R.id.imageView)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        messageRecyclerView.layoutManager= LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter

        // logic for adding data to recyclerView

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        //add message to database
         sentButton.setOnClickListener {
             val message = messageBox.text.toString()
             val messageObject = Message(message,senderUid)

             mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                 .setValue(messageObject).addOnSuccessListener {
                     mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                         .setValue(messageObject)
                 }

         }
        messageBox.setText("**")
    }
}