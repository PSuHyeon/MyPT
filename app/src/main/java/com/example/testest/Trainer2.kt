package com.example.testest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class Trainer2 : Fragment() {

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
        super.onCreate(savedInstanceState)
        val activity = inflater.inflate(R.layout.fragment_trainer2, container, false)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.chat_recyclerView)
        val trainees = ArrayList<Trainee>()
        val text = activity.findViewById<TextView>(R.id.test)

        val url = "http://172.10.18.125:80/get_trainee_info"

        val request = object : JsonArrayRequest(
            Request.Method.GET,
            url,null, Response.Listener {
                for (i in 0 until it.length()){
                    val jsonObject = it.getJSONObject(i)
                    val name = jsonObject.getString("name")
                    val age = jsonObject.getString("age")
                    val kg = jsonObject.getString("weight")
                    val keyId = jsonObject.getString("keyid")
                    Log.d("checc", ""+name+age+kg)
                    trainees.add(Trainee(name, age, kg, keyId))
                }
                recyclerView.adapter = ChatAdapter(context1, trainees)
                recyclerView.layoutManager = LinearLayoutManager(context1)
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
        return activity
    }

    }


class Trainee(val name: String, val age: String, val kg: String, val key_id: String){

}

class ChatAdapter(val context: Context, val traineeList: ArrayList<Trainee>): RecyclerView.Adapter<ChatAdapter.TraineeHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.TraineeHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)
        return TraineeHolder(view)
    }

    inner class TraineeHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val trainee_name : TextView
        val age : TextView
        val kg : TextView
        val id : TextView
        init{
            trainee_name = itemView?.findViewById<TextView>(R.id.item_name)
            age = itemView?.findViewById<TextView>(R.id.item_age)
            kg = itemView?.findViewById<TextView>(R.id.item_kg)
            id = itemView?.findViewById<TextView>(R.id.item_id)
            itemView?.setOnClickListener {
                val intent = Intent(context, chatroom::class.java)
                intent.putExtra("name", trainee_name.text.toString())
                intent.putExtra("age", age.text.toString())
                intent.putExtra("kg", kg.text.toString())
                intent.putExtra("id", id.text.toString())
                context.startActivity(intent)
            }
        }


    }

    override fun onBindViewHolder(holder: TraineeHolder, position: Int) {
        holder.trainee_name.text = traineeList.get(position).name
        holder.age.text = traineeList.get(position).age
        holder.kg.text = traineeList.get(position).kg
        holder.id.text = traineeList.get(position).key_id
    }

    override fun getItemCount(): Int {
        return traineeList.size
    }


}