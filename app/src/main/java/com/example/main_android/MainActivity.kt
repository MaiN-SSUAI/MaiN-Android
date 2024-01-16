package com.example.main_android

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //로그인 버튼
        var login_button : Button = findViewById(R.id.login_button)
        //클릭시 usaint로그인 페이지로 이동
        login_button.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://smartid.ssu.ac.kr/Symtra_sso/smln.asp?apiReturnUrl=https%3A%2F%2Fsaint.ssu.ac.kr%2FwebSSO%2Fsso.jsp"))
            startActivity(intent)
        }

    }

} 