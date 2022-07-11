package com.example.testest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject


class chatroom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)

        val arrayList = ArrayList<ChatModel>()
        val chat_recycler = findViewById<RecyclerView>(R.id.recycler_view)
        val madapter = ChatAdapter2(this, arrayList, "-1")

        chat_recycler.adapter = madapter
        chat_recycler.layoutManager = LinearLayoutManager(this)
        chat_recycler.setHasFixedSize(true)

        val send_button = findViewById<Button>(R.id.sendButton)
        val text = findViewById<EditText>(R.id.type_text)

        var mSocket: Socket = IO.socket("http://172.10.18.125:443")
        mSocket.connect()

        val key_id = intent.getStringExtra("id")
        Log.d("key_id", ""+ key_id)
        mSocket.emit("connect user", key_id)

        send_button.setOnClickListener {
            if (text.text.toString() == ""){
                Toast.makeText(this, "no text", Toast.LENGTH_SHORT)
            }
            else{
                val jsonObject = JSONObject()
                jsonObject.put("name", "")
                jsonObject.put("text", text.text.toString())
                jsonObject.put("key_id", "-1")
                jsonObject.put("time", System.currentTimeMillis())
                jsonObject.put("room_id",key_id)
                text.setText("")
                mSocket.emit("chat message", jsonObject)
            }
        }


        mSocket.on("chat message", Emitter.Listener { args ->
            runOnUiThread {
                val data = args[0] as JSONObject
                val name = data.getString("name")
                val text = data.getString("text")
                val time = data.getString("time")
                val key_id = data.getString("key_id")
                madapter.addItem(ChatModel(name, key_id, text, time))
                madapter.notifyDataSetChanged()
            }
        })


    }
}
class ChatModel(val name: String, val key_id: String, val text: String, val time: String)

class ChatAdapter2(val context: Context, val arrayList: ArrayList<ChatModel>, val key_id: String): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    fun addItem(item: ChatModel){
        if (arrayList != null) {
            arrayList.add(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (arrayList.get(position).key_id == key_id){
            return 1
        }
        else{
            return 2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyHolder){
            holder.chat_text.text = arrayList.get(position).text
            holder.chat_time.text = arrayList.get(position).time
        }
        if (holder is YourHolder){
            holder.chat_text.text = arrayList.get(position).text
            holder.chat_time.text = arrayList.get(position).time
            holder.chat_name.text =arrayList.get(position).name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == 1){
            view = LayoutInflater.from(context).inflate(R.layout.itemview_mine, parent, false)
            return MyHolder(view)
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_yours, parent, false)
            return YourHolder(view)
        }
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chat_text = itemView?.findViewById<TextView>(R.id.my_text)
        val chat_time = itemView?.findViewById<TextView>(R.id.my_time)
    }
    inner class YourHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chat_name = itemView?.findViewById<TextView>(R.id.your_name)
        val chat_text = itemView?.findViewById<TextView>(R.id.your_text)
        val chat_time = itemView?.findViewById<TextView>(R.id.your_time)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}