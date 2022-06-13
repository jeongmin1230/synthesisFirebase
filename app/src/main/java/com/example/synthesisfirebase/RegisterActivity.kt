package com.example.synthesisfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
    }
    private fun createUser(email : String, password : String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "회원가입 성공\n다시 로그인해주세요.", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)


                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
            .addOnFailureListener {
//                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                if(password.toString().length < 8) {
                    Toast.makeText(this, "비밀번호를 8글자 이상으로 설정 해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        return
    }

    private fun updateUI(user : FirebaseUser?) {
        user?.let{
            tvResult.text = "Email : ${user.email}\nUid : ${user.uid}"
        }
    }

/* --------------------onClick 함수-------------------- */

    fun onClickRegistration(view: View) {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        createUser(email, password)
    }
}