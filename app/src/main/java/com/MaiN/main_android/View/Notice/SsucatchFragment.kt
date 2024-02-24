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
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.MaiN.main_android.Adapter.SsucatchAdapter
import com.MaiN.main_android.R
import com.MaiN.main_android.SharedPreference.MyApplication
import com.MaiN.main_android.retrofit.RetrofitConnection
import com.MaiN.main_android.retrofit.SsucatchAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SsucatchFragment : Fragment() {

    private  lateinit var adapter : SsucatchAdapter
    private val retrofitAPI = RetrofitConnection.getInstance().create(SsucatchAPIService::class.java)

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ssucatch_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedCategory:String="전체"

        val recyclerView = view.findViewById<RecyclerView>(R.id.ssucath_recyclerView)
        adapter = SsucatchAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val switch: SwitchCompat = view.findViewById(R.id.switch3)
        switch.setOnCheckedChangeListener{ _, isCecked->
            if(isCecked) {
                adapter.filterFavorites()
            }
            else{
                adapter.clearFilter()
            }
        }

        progressBar = view.findViewById(R.id.progressBar3)
        progressBar.isIndeterminate = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            progressBar.indeterminateDrawable.colorFilter = BlendModeColorFilter(Color.parseColor("#03A9F4"), BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            progressBar.indeterminateDrawable.setColorFilter(Color.parseColor("#03A9F4"), PorterDuff.Mode.SRC_ATOP)
        }

        fetchNotifications()

        val BackButton = view.findViewById<ImageView>(R.id.imageView9)
        BackButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }

        val categoryAll = view.findViewById<TextView>(R.id.categoryAll)
        val category1 = view.findViewById<TextView>(R.id.category1)
        val category2 = view.findViewById<TextView>(R.id.category2)
        val category3 = view.findViewById<TextView>(R.id.category3)
        val category4 = view.findViewById<TextView>(R.id.category4)
        val category5 = view.findViewById<TextView>(R.id.category5)
        val category6 = view.findViewById<TextView>(R.id.category6)
        val category7 = view.findViewById<TextView>(R.id.category7)
        val category8 = view.findViewById<TextView>(R.id.category8)
        val category9 = view.findViewById<TextView>(R.id.category9)
        val category10 = view.findViewById<TextView>(R.id.category10)
        val category11 = view.findViewById<TextView>(R.id.category11)


        //선택된 카테고리만 보기
        val categories = listOf(categoryAll,category1,category2,category3,category4,category5,category6,category7,category8,category9,category10,category11)

        fun updateCategoryViews() {
            for(category in categories){
                category.isSelected = (category.tag as String == selectedCategory)
                category.background = ContextCompat.getDrawable(requireContext(),R.drawable.category_background)
            }
        }

        for(category in categories){
            category.setOnClickListener{
                selectedCategory = it.tag as String
                category.isSelected = true
                if(selectedCategory=="전체"){
                    adapter.clearFilter()
                }else{
                    adapter.filterCategory(selectedCategory)
                }
                updateCategoryViews()
            }
        }
    }



    private fun fetchNotifications() {
        lifecycleScope.launch{
            progressBar.visibility = View.VISIBLE
            val studentID = MyApplication.prefs.getSchoolNumber("schoolNumber","")
            val response = withContext(Dispatchers.IO) {retrofitAPI.getSsucatchNotiAll(studentID).execute()}
            if (response.isSuccessful) {
                val notifications = response.body()
                notifications?.let { adapter.setItems(it)}
            }
            progressBar.visibility = View.GONE
        }
    }




}