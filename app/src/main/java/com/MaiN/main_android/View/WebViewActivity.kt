package com.MaiN.main_android.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.MaiN.main_android.SharedPreference.MyApplication
import com.MaiN.main_android.retrofit.RetrofitConnection
import com.MaiN.main_android.retrofit.UserAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var isFirstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        //intent로 부터 url 을 받아오거나 없다면 기본값으로 https://www.example.com 사용
        val url = intent.getStringExtra("url") ?: "https://www.example.com"
        webView.settings.javaScriptEnabled = true //javascript 활성화

        //webclient 설정
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if (!isFirstLoad) { //페이지가 처음 로드되었는지 확인
                    //페이지가 처음 로드되지 않았다면 다시 로드
                    view?.loadUrl("https://saint.ssu.ac.kr/webSSUMain/main_student.jsp")

                    //javascript로 텍스트 가져와서 학부 확인
                    val jsDepartment = "document.querySelector('body > div > div.main_wrap > div.main_left > div.main_box09 > div.main_box09_con_w > ul > li:nth-child(2) > dl > dd > a > strong').innerText"
                    view?.evaluateJavascript(jsDepartment) { value ->
                        if (value == "\"AI융합학부\"") { //가져온 text가 ai융합학부면 HomeActivity로 이동
                            val jsSchoolNumber = "document.querySelector('body > div > div.main_wrap > div.main_left > div.main_box09 > div.main_box09_con_w > ul > li:nth-child(1) > dl > dd > a > strong').innerText"
                            view.evaluateJavascript(jsSchoolNumber) {schoolNumber->
                                //학번을 shared preferences 에 저장
                                val schoolNumberWithoutQuotes = schoolNumber.replace("\"","")
                                MyApplication.prefs.setSchoolNumber("schoolNumber",schoolNumberWithoutQuotes)
                                addUser(schoolNumberWithoutQuotes)
                                MyApplication.prefs.setIsLogin("isLogin",true) //로그인 상태 true 로 저장
                            }
                            navigateToHome()
                            Log.d("navigateToHome","홈화면으로 이동 메소드 호출")
                        }
                    }
                } else {
                    isFirstLoad = false //페이지가 처음 로드되었다면 플래그를 false 로 설정
                }
            }
        }
        //webview 에 url 로드
        webView.loadUrl(url)
    }

    //home activity 로 이동하는 함수
    private fun navigateToHome() {
        isFirstLoad = true
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


    //addUser API 호출 (mysql 에 저장)
    private fun addUser(value:String) {
        val retrofit = RetrofitConnection.getInstance()
        val service = retrofit.create(UserAPIService::class.java)

        val user = UserAPIService.User(value)
        val call = service.addUser(user)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("UserAPI", "데이터베이스에 학번 추가 성공")
                } else {
                    Log.d("UserAPI", "데이터베이스에 학번 추가 실패")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("UserAPI", "데이터베이스에 학번 추가 실패")
            }
        })

    }
}
