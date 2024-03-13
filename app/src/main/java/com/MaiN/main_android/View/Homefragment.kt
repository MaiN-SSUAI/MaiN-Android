package com.MaiN.main_android.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.MaiN.main_android.R
import com.MaiN.main_android.View.Notice.AinotiFragment
import com.MaiN.main_android.View.Notice.FunsysFragment
import com.MaiN.main_android.View.Notice.SsucatchFragment

class home_fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveToAinoti()
        moveToSsucatch()
        moveToFunsys()
    }

    //클릭시 ai융합 공지사항으로 이동
    private fun moveToAinoti() {
        val AinotiCard = view?.findViewById<CardView>(R.id.aiCardView)
        AinotiCard?.setOnClickListener {
            val newFragment = AinotiFragment() // 이동하려는 프래그먼트
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(
                R.id.main_content,
                newFragment
            ) // 'fragment_container'는 교체할 레이아웃의 ID입니다.
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    //클릭시 슈캐치 공지로 이동
    private fun moveToSsucatch() {
        val SsucatchCard = view?.findViewById<CardView>(R.id.ssucatchCardView)
        SsucatchCard?.setOnClickListener {
            val newFragment = SsucatchFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_content, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    //클릭시 펀시스템 공지로 이동
    private fun moveToFunsys() {
        val FunsysCard = view?.findViewById<CardView>(R.id.funsysCardView)
        FunsysCard?.setOnClickListener {
            val newFragment = FunsysFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_content, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}