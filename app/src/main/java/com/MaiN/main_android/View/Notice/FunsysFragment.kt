package com.MaiN.main_android.View.Notice

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.MaiN.main_android.Adapter.FunsysAdapter
import com.MaiN.main_android.R
import com.MaiN.main_android.SharedPreference.MyApplication
import com.MaiN.main_android.retrofit.FunsysAPIService
import com.MaiN.main_android.retrofit.RetrofitConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FunsysFragment : Fragment() {

    private lateinit var adapter: FunsysAdapter
    private val retrofitAPI = RetrofitConnection.getInstance().create(FunsysAPIService::class.java)

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.funsys_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.funsys_recyclerView)
        adapter = FunsysAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val switch: SwitchCompat = view.findViewById(R.id.switch2)
        switch.setOnCheckedChangeListener{ _, isCecked->
            if(isCecked) {
                adapter.filterFavorites()
            }
            else{
                adapter.clearFilter()
            }
        }

        progressBar = view.findViewById(R.id.progressBar2)
        progressBar.isIndeterminate = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            progressBar.indeterminateDrawable.colorFilter = BlendModeColorFilter(Color.parseColor("#03A9F4"), BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            progressBar.indeterminateDrawable.setColorFilter(Color.parseColor("#03A9F4"), PorterDuff.Mode.SRC_ATOP)
        }

        fetchNotifications()

        val BackButton = view.findViewById<ImageView>(R.id.imageView5)
        BackButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }

    private fun fetchNotifications() {
        lifecycleScope.launch{
            progressBar.visibility = View.VISIBLE
            val studentID = MyApplication.prefs.getSchoolNumber("schoolNumber","")
            val response = withContext(Dispatchers.IO) {retrofitAPI.getFunsysNotiAll(studentID).execute()}
            if (response.isSuccessful) {
                val notifications = response.body()
                notifications?.let { adapter.setItems(it)}
            }
            progressBar.visibility = View.GONE // 데이터 로딩이 끝나면 ProgressBar를 사라지게 함
        }
    }

}