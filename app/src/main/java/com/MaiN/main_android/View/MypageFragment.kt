package com.MaiN.main_android.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.MaiN.main_android.MainActivity
import com.MaiN.main_android.R
import com.MaiN.main_android.SharedPreference.MyApplication
import com.MaiN.main_android.SharedPreference.PreferenceUtil


class MypageFragment : Fragment() {
    private lateinit var schoolNumberTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.mypage_fragment, container, false)
        schoolNumberTextView = view.findViewById(R.id.textSchoolNumber)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = PreferenceUtil(requireContext())
        val schoolNumber = prefs.getSchoolNumber("schoolNumber", "None")
        schoolNumberTextView.text = schoolNumber
        setLogoutButton()
    }

    //로그아웃 버튼
    private fun setLogoutButton() {
        val logoutButton: Button? = view?.findViewById(R.id.logout_button)
        logoutButton?.setOnClickListener {
            //저장된 학번 정보 삭제
            MyApplication.prefs.delSchoolNumber()
            MyApplication.prefs.setIsLogin("isLogin", false)

            //모든 액티비티 스택 클리어 후 첫 화면으로 돌아감
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}