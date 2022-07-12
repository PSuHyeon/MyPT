package com.example.testest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Reply(val name: String, val time: String, val text: String, val feedId: String){

}
class ReplyAdapter(val context: Context, val array: ArrayList<Reply>): RecyclerView.Adapter<ReplyAdapter.ReplyHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyAdapter.ReplyHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.communityreplyrecyclerviewitem, parent, false)
        return ReplyHolder(view)
    }

    inner class ReplyHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name_text = itemView.findViewById<TextView>(R.id.replyNameTextView)
        val text = itemView.findViewById<TextView>(R.id.replyTextView)
        val date = itemView.findViewById<TextView>(R.id.replyDate)
    }

    override fun onBindViewHolder(holder: ReplyAdapter.ReplyHolder, position: Int) {
        holder.name_text.text = array.get(position).name
        holder.date.text = array.get(position).time
        holder.text.text = array.get(position).text
    }

    override fun getItemCount(): Int {
        return array.size
    }
}