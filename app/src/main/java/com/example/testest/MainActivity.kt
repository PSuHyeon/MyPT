package com.example.testest

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import org.json.JSONObject

var id = ""
var password = ""
class MainActivity : AppCompatActivity() {

    companion object{
        var requestQueue: RequestQueue? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val sign_in_button = findViewById<Button>(R.id.sign_in)
        val sign_up_button = findViewById<TextView>(R.id.sign_up)
        val naver_login = findViewById<Button>(R.id.naver_login_button)
        val id_text = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.id_text)
        val pass_text = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.password_text)


        requestQueue = Volley.newRequestQueue(applicationContext)

        var text_from_server = ""

        val naverClientId = getString(R.string.social_login_info_naver_client_id)
        val naverClientSecret = getString(R.string.social_login_info_naver_client_secret)
        val naverClientName = getString(R.string.social_login_info_naver_client_name)

        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)

        naver_login.setOnClickListener {
            startNaverLogin()
        }


        sign_in_button.setOnClickListener {

            val id = findViewById<TextView>(R.id.id_text)
            val pw = findViewById<TextView>(R.id.password_text)
            val url = "http://172.10.18.125:80/login/" +id.text.toString() + "," + pw.text.toString()
            val request = object : StringRequest(
                Request.Method.GET,
                url, {
                    text_from_server = it
                    //val booklist = Gson().fromJson(text_from_server, Array<Person>::class.java)
                    if (it != "login failed"){
                        val temp = it.split(",")
                        if (temp.get(2) == "TRAINEE"){
                            val intent = Intent(this@MainActivity, Menu::class.java)
                            intent.putExtra("keyid", temp.get(0))
                            intent.putExtra("name", temp.get(1))
                            Toast.makeText(this@MainActivity, "${temp.get(1)} 회원님 환영합니다", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                        else{
                            val intent = Intent(this@MainActivity, TrainerMenu::class.java)
                            intent.putExtra("keyid", temp.get(0))
                            intent.putExtra("name", temp.get(1))
                            Toast.makeText(this@MainActivity, "${temp.get(1)} 코치님 환영합니다", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }
                    }
                }, null
            ) {

            }

            request.setShouldCache(false)
            requestQueue?.add(request)

        }

        sign_up_button.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivityForResult(intent, 200)
        }

        // 트레이너 로그인
        val trainer_sign_in_button = findViewById<Button>(R.id.trainer_sign_in)
        trainer_sign_in_button.setOnClickListener{
            val intent = Intent(this@MainActivity, TrainerMenu::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            val id = data?.getStringExtra("id")
            val password = data?.getStringExtra("pw")
            val name = data?.getStringExtra("name")
            val position = data?.getStringExtra("who")
            var params = HashMap<String,String>()
            params["id"] = id!!
            params["password"] = password!!
            params["name"] = name!!
            params["position"] = position!!
            val jsonObject = JSONObject(Gson().toJson(params))

            val url = "http://172.10.18.125:80/sign_up"
            val request = object : JsonObjectRequest(
                Request.Method.POST,
                url,null, Response.Listener {

                }, Response.ErrorListener {
                }
            ) {
                override fun getBody(): ByteArray {
                    return jsonObject.toString().toByteArray()
                }
            }
            request.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                // 0 means no retry
                0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            // Add the volley post request to the request queue
            VolleySingleton.getInstance(this).addToRequestQueue(request)
    }



    private fun startNaverLogin(){
        var naverToken :String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
                val username = response.profile?.name
                naverToken?.replace("/", "")

                val checkUrl = "http://172.10.18.125:80/checkid/" + userId
                val check_request = object : StringRequest(
                    Request.Method.GET,
                    checkUrl, {
                        if (it == "ok"){
                            var keyid = ""
                            val url = "http://172.10.18.125:80/sign_up"
                            var params = HashMap<String,String>()
                            params["id"] = userId!!
                            params["password"] = "naver"
                            params["name"] = username!!
                            params["position"] = "TRAINEE"
                            val jsonObject = JSONObject(Gson().toJson(params))
                            val request = object : JsonObjectRequest(
                                Request.Method.POST,
                                url,null, Response.Listener {
                                    keyid = "" + it.get("keyid")
                                }, Response.ErrorListener {

                                }
                            ) {
                                override fun getBody(): ByteArray {
                                    return jsonObject.toString().toByteArray()
                                }
                            }
                            request.retryPolicy = DefaultRetryPolicy(
                                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                                // 0 means no retry
                                0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                                1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                            )
                            requestQueue?.add(request)
                            val intent = Intent(this@MainActivity, Menu::class.java)
                            intent.putExtra("keyid", keyid)
                            intent.putExtra("name", username)
                            startActivity(intent)
                            Toast.makeText(this@MainActivity, "네이버 아이디 회원가입 성공", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            val temp = it.split(",")
                            Log.d("array", "" + temp)
                            if (temp.get(2) == "TRAINEE" || temp.get(2) == "trainee"){
                                val intent = Intent(this@MainActivity, Menu::class.java)
                                intent.putExtra("keyid", temp.get(0))
                                intent.putExtra("name", temp.get(1))
                                Toast.makeText(this@MainActivity, "${temp.get(1)} 회원님 환영합니다", Toast.LENGTH_SHORT).show()
                                startActivity(intent)
                            }
                            else{
                                val intent = Intent(this@MainActivity, TrainerMenu::class.java)
                                intent.putExtra("keyid", temp.get(0))
                                intent.putExtra("name", temp.get(1))
                                Toast.makeText(this@MainActivity, "${temp.get(1)} 코치님 환영합니다", Toast.LENGTH_SHORT).show()
                                startActivity(intent)
                            }

                        }
                    }, null
                ) {

                }

                check_request.setShouldCache(false)
                requestQueue?.add(check_request)




                // Add the volley post request to the request queue

            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@MainActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@MainActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }
}


class VolleySingleton constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: VolleySingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VolleySingleton(context).also {
                    INSTANCE = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}
