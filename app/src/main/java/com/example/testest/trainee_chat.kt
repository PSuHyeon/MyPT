package com.example.testest

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.testest.Menu.key_id
import com.example.testest.Menu.name
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class trainee_chat : Fragment() {


    lateinit var context1: Context
    override fun onAttach(context: Context) {
        context1 = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arrayList = ArrayList<ChatModel>()
        val activity = inflater.inflate(R.layout.chat, container, false)
        val chat_recycler = activity.findViewById<RecyclerView>(R.id.recycler_view)
        val madapter = ChatAdapter2(context1, arrayList, key_id)

        chat_recycler.adapter = madapter
        chat_recycler.layoutManager = LinearLayoutManager(context1)
        chat_recycler.setHasFixedSize(true)

        val send_button = activity.findViewById<Button>(R.id.sendButton)
        val text = activity.findViewById<EditText>(R.id.type_text)

        val url = "http://192.249.18.125:80/get_chat_info/" + key_id

        val request = object : JsonArrayRequest(
            Request.Method.GET,
            url,null, Response.Listener {
                for (i in 0 until it.length()){
                    val jsonObject = it.getJSONObject(i)
                    val name = jsonObject.getString("name")
                    val text = jsonObject.getString("text")
                    val time = jsonObject.getString("time")
                    val keyId = jsonObject.getString("key_id")
                    arrayList.add(ChatModel(name, keyId, text, time))
                    (chat_recycler.adapter as ChatAdapter2).notifyDataSetChanged()
                }
            }, Response.ErrorListener {

            }
        ) {

        }
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        VolleySingleton.getInstance(context1).addToRequestQueue(request)

        var mSocket: Socket = IO.socket("http://192.249.18.125:443")
        mSocket.connect()

        mSocket.emit("connect user", key_id)

        send_button.setOnClickListener {
            if (text.text.toString() == ""){
                Toast.makeText(context1, "no text", Toast.LENGTH_SHORT)
            }
            else{
                val jsonObject = JSONObject()
                jsonObject.put("name", name)
                jsonObject.put("text", text.text.toString())
                jsonObject.put("key_id", key_id)
                val now = System.currentTimeMillis()
                val date = Date(now)
                val sdf = SimpleDateFormat("yyyy-MM-dd--hh-mm")
                jsonObject.put("time", sdf.format(date))
                jsonObject.put("room_id",key_id)
                text.setText("")
                mSocket.emit("chat message", jsonObject)
            }
        }


        mSocket.on("chat message", Emitter.Listener { args ->
            getActivity()?.runOnUiThread {
                val data = args[0] as JSONObject
                val name = data.getString("name")
                val text = data.getString("text")
                val time = data.getString("time")
                val key_id = data.getString("key_id")
                madapter.addItem(ChatModel(name, key_id, text, time))
                madapter.notifyDataSetChanged()
            }
        })
        return activity
    }


}