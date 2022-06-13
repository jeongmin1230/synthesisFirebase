package com.example.synthesisfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    private val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열
    private val adapter = ListAdapter(itemList)         // 리사이클러 뷰 어댑터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadAccount()
        loadData()
    }

/* --------------------기능함수-------------------- */

    // 앱이 시작되자마자 로그인 한 계정을 상단에 띄워주기 위해 계정 읽어서 보여주는 함수
    private fun loadAccount() {
        tvAccount.append(intent.getStringExtra("userEmail"))
    }

    // 엡이 시작되자마자 FireStore database 의 데이터를 불러오기 위한 함수
    private fun loadData() {
        db.collection("Todo") // 작업할 컬렉션
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList.clear()
                for(document in result) { // 가져온 문서들은 result 에 들어감
                    val item = ListLayout(document["thing"] as String, document["deadline"] as String)
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged() // 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.d("jeongmin", "Error getting documents : $exception")
            }
    }

/* --------------------onClick 함수-------------------- */

    fun onClickRefresh(view: View) {}
    fun onClickAdd(view: View) {}
}