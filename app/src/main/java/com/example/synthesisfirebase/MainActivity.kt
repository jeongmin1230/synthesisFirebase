package com.example.synthesisfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
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

        rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvList.adapter = adapter
    }

/* --------------------기능함수-------------------- */

    // 앱이 시작되자마자 로그인 한 계정을 상단에 띄워주기 위해 계정 읽어서 보여주는 함수
    private fun loadAccount() {
        tvAccount.append(intent.getStringExtra("userEmail"))
    }

    // 엡이 시작되자마자 FireStore database 의 데이터를 불러오기 위한 함수
    private fun loadData() {
        db.collection("synthesis") // 작업할 컬렉션
            .get()
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList.clear()
                for(document in result) { // 가져온 문서들은 result 에 들어감
                    val item = ListLayout(document["thing"] as String, document["needCount"] as String)
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged() // 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.d("jeongmin", "Error getting documents : $exception")
            }
    }
    // 새로고침 버튼 기능
    private fun refresh() {
        db.collection("synthesis") // 작업할 컬렉션
            .get() // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList.clear()
                for(document in result) { // 가져온 문서들은 result 에 들어감
                    val item = ListLayout(document["thing"] as String, document["needCount"] as String)
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged() // 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.d("jeongmin", "Error getting documents : $exception")
            }
    }

    // 추가 다이얼로그 생성하는 함수
    private fun addDialog() {
        // 대화상자 생성
        val builder = AlertDialog.Builder(this)

        // 대화상자에 텍스트 입력 필드 추가
        val tvName = TextView(this)
        tvName.text = "픔목"
        val tvNumber = TextView(this)
        tvNumber.text = "필요 갯수"
        val etName = EditText(this)
        etName.isSingleLine = true
        val etNumber = EditText(this)
        etNumber.isSingleLine = true
        val mLayout = LinearLayout(this)
        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.setPadding(16)
        mLayout.addView(tvName)
        mLayout.addView(etName)
        mLayout.addView(tvNumber)
        mLayout.addView(etNumber)
        builder.setView(mLayout)

        builder.setTitle("데이터 추가")
        builder.setPositiveButton("추가") { dialog, which ->
            // EditText 에서 문자열을 가져와 hashMap 으로 만듦
            val data = hashMapOf(
                "thing" to etName.text.toString(),
                "needCount" to etNumber.text.toString()
            )
            // Contacts 컬렉션에 data 를 자동 이름으로 저장
            db.collection("synthesis")
                .add(data)
                .addOnSuccessListener {
                    // 성공할 경우
                    Toast.makeText(this, "데이터가 추가되었습니다", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우
                    Log.w("MainActivity", "Error getting documents: $exception")
                }
        }
        builder.setNegativeButton("취소") { dialog, which ->

        }
        builder.show()
    }

/* --------------------onClick 함수-------------------- */

    fun onClickRefresh(view: View) {
        refresh()
    }
    fun onClickAdd(view: View) {
        addDialog()
    }
}