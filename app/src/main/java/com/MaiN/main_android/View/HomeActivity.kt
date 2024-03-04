package com.MaiN.main_android.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.MaiN.main_android.R
import com.MaiN.main_android.View.Reserv.ReservationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab_view)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        supportFragmentManager.beginTransaction().replace(R.id.main_content, home_fragment()).commit()

        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment : Fragment? = null

            when (item.itemId) {
                R.id.tab_menu_noti -> {
                    selectedFragment = home_fragment()
                }
                R.id.tab_menu_mypage -> {
                    selectedFragment = MypageFragment()
                }
                R.id.tab_menu_reserv -> {
                    selectedFragment = ReservationFragment()
                }
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.main_content, selectedFragment).commit()
            }

            true
        }
    }
}