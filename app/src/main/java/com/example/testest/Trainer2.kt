package com.example.testest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class Trainer2() : Fragment() {

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
        val activity =  inflater.inflate(R.layout.fragment_trainer2, container, false)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.chat_recyclerView)
        val trainees = ArrayList<Trainee>()
        recyclerView.adapter = ChatAdapter(context1, trainees)
        val url = "http://172.10.18.125:80/get_trainee_info"
        var params = HashMap<String,String>()
        val jsonArray = JSONArray(Gson().toJson(params))
        val request = object : JsonArrayRequest(
            Request.Method.POST,
            url,null, Response.Listener {
                for (i in 0 until it.length()){
                    val jsonObject = it.getJSONObject(i)
                    val name = jsonObject.getString("name")
                    val age = jsonObject.getString("age")
                    val kg = jsonObject.getString("weight")
                    trainees.add(Trainee(name, age, kg))
                }
                (recyclerView.adapter as ChatAdapter).notifyDataSetChanged()
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
        MainActivity.requestQueue?.add(request)







        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
class Trainee(val name: String, val age: String, val kg: String){

}

class ChatAdapter(val context: Context, val traineeList: ArrayList<Trainee>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onBindViewHolder(tholder: RecyclerView.ViewHolder, position: Int) {
        (tholder as TraineeHolder).trainee_name.text = traineeList.get(position).name
        (tholder as TraineeHolder).age.text = traineeList.get(position).age
        (tholder as TraineeHolder).kg.text = traineeList.get(position).kg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)
        return TraineeHolder(view)
    }

    inner class TraineeHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val trainee_name = itemView?.findViewById<TextView>(R.id.item_name)
        val age = itemView?.findViewById<TextView>(R.id.item_age)
        val kg = itemView?.findViewById<TextView>(R.id.item_kg)
    }

    override fun getItemCount(): Int {
        return traineeList.size
    }
}