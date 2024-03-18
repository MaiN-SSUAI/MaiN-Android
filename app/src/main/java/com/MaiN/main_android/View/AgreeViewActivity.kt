package com.MaiN.main_android.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.MaiN.main_android.R

class AgreeViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agree_view)
        setAgreeButton()

    }

    //버튼 클릭시 로그인화면 이동
    @SuppressLint("MissingInflatedId")
    private fun setAgreeButton() {
        val agreeButton: Button = findViewById(R.id.agree_button)
        agreeButton.setOnClickListener {

            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(
                "url",
                "https://smartid.ssu.ac.kr/Symtra_sso/smln.asp?apiReturnUrl=https%3A%2F%2Fsaint.ssu.ac.kr%2FwebSSO%2Fsso.jsp"
            )
            startActivity(intent)
            finish()
        }
    }
}