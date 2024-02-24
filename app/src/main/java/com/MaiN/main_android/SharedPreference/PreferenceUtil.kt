package com.MaiN.main_android.SharedPreference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferenceUtil(context: Context) {

    //shared preference 객체 초기화
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE)

    //저장된 학번을 불러오는 메소드
    fun getSchoolNumber(key: String, defValue:String):String {
        return prefs.getString(key,defValue)?:defValue
    }

    //학번을 저장하는 메소드
    fun setSchoolNumber(key:String,value:String) {
        prefs.edit().putString(key, value).apply() //sharedpreferences 에 저장
    }

    //삭제 메소드
    fun delSchoolNumber(){
        Log.d("delSchoolNumber","학번 삭제 됨 ")
        prefs.edit().clear().apply()
    }

    //로그인 상태 불러오기
    fun getIsLogin(key:String,defValue: Boolean) : Boolean{
        return prefs.getBoolean(key,defValue)
    }

    //로그인 상태 저장 메서드
    fun setIsLogin(key:String,value: Boolean){
        Log.d("setIsLogin","로그인 상태 변경 됨")
        prefs.edit().putBoolean(key,value).apply()
    }


}