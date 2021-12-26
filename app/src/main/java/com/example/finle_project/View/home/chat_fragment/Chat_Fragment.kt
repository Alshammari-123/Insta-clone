package com.example.finle_project.View.home.chat_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.Model.UserChate
import com.example.finle_project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Chat_Fragment : Fragment() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: MutableList<UserChate>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_chat_, container, false)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        userList = mutableListOf()
        adapter = UserAdapter(requireActivity().applicationContext, userList)
        userRecyclerView = v.findViewById(R.id.userRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.adapter = adapter

        mDbRef.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                userList.clear()
                Log.d("Chat_Fragment", "onDataChange: ${snapshot.children.count()}")
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(UserChate::class.java)
                    Log.d("Chat_Fragment", "onDataChange: ${currentUser!!.name}")
                    adapter.appendData(currentUser)

//                    if (mAuth.currentUser?.uid != currentUser?.uid){
//                        userList.add(currentUser!!)
//                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return v


    }

//for logout

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.toolbar) {
            mAuth.signOut()

            return true
        }
        return true
    }

}



