package com.example.testest

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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
        val id_text = findViewById<TextView>(R.id.id_text)
        val pass_text = findViewById<TextView>(R.id.password_text)


        requestQueue = Volley.newRequestQueue(applicationContext)

        var text_from_server = ""

        val naverClientId = getString(R.string.social_login_info_naver_client_id)
        val naverClientSecret = getString(R.string.social_login_info_naver_client_secret)
        val naverClientName = getString(R.string.social_login_info_naver_client_name)

        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)

        naver_login.setOnClickListener {
            startNaverLogin()
        }


        sign_in_button.setOnClickListener {     //버튼으로 서버에서 데이터 가져오는 코드
            val id = id_text.text.toString()
            val password = pass_text.text.toString()
            val url = "http://172.10.5.119:80/login/" +id + "," + password
            val request = object : StringRequest(
                Request.Method.GET,
                url, {
                    text_from_server = it
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                    if (it == "login success"){
                        val intent = Intent(this@MainActivity, Menu::class.java)
                        startActivity(intent)
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

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == 200){
            super.onActivityResult(requestCode, resultCode, data)
            val id = data?.getStringExtra("id")
            val password = data?.getStringExtra("password")
            var params = HashMap<String,String>()
            params["id"] = id!!
            params["password"] = password!!
            val jsonObject = JSONObject(params as Map<*, *>?)
            val url = "http://172.10.5.119:80/signup"
            val request = object : JsonObjectRequest(
                Request.Method.POST,
                url, jsonObject , Response.Listener {
                    Toast.makeText(this@MainActivity, ""+it, Toast.LENGTH_SHORT).show()
                    Log.d("checkec", ""+it)
                }, null
            ) {

            }

            request.setShouldCache(false)
            requestQueue?.add(request)

        }

    }



    private fun startNaverLogin(){
        var naverToken :String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
                val test_text = findViewById<TextView>(R.id.test_text)
                naverToken?.replace("/", "")
                Log.d("naver", ""+ userId)
                val url = "http://172.10.5.119:80/login/" +userId + "," + "naver"
                val request = object : StringRequest(
                    Request.Method.GET,
                    url, {
                        Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                        if (it == "login success"){
                            val intent = Intent(this@MainActivity, Menu::class.java)
                            startActivity(intent)
                        }
                    }, null
                ) {

                }
                request.setShouldCache(false)
                requestQueue?.add(request)
                Toast.makeText(this@MainActivity, "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show()
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

class Person(val name: String, val age: Int, val position: Int, val id: Int){

}