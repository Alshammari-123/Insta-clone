package com.example.finle_project.view.home.chat_fragment

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finle_project.model.UserChate
import com.example.finle_project.R

class UserAdapter(val context: Context, private val userList:MutableList<UserChate>):
    RecyclerView.Adapter<UserAdapter.UserViweHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViweHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.user_layout, parent, false)
        return UserViweHolder(view)
    }

    override fun onBindViewHolder(holder: UserViweHolder, position: Int) {
        val currentUser =userList[position]
        holder.textName.text=currentUser.name

        holder.itemView.setOnClickListener {
            val intent= Intent(context,ChatActivity::class.java)

            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            holder.itemView.context

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun submitData(list: MutableList<UserChate>) {
        userList.clear()
        userList.addAll(list)
        Log.d("UserAdapter", "submitData: $list")
        notifyDataSetChanged()
    }

    fun appendData(user: UserChate) {
        userList.add(user)
        notifyItemInserted(userList.size - 1)
    }

    class UserViweHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val textName = itemView.findViewById<TextView>(R.id.textName)
    }

}