package com.example.synthesisfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    //firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    //google client
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googleSignInOptions()

        firebaseAuth = FirebaseAuth.getInstance()//firebase auth 객체
    }
/* --------------------override 함수-------------------- */

    override fun onClick(p0: View?) {
    }

/* --------------------기능함수-------------------- */

    //Google 로그인 옵션 구성. requestIdToken 및 Email 요청
    private fun googleSignInOptions() {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("716839901747-a8m6mv3r49rj2rbulk42bkd1qnmv7iqi.apps.googleusercontent.com")
        .requestEmail()
        .build()

    googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn(email : String, password : String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) {task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("userEmail", email)
                        Log.i("jeongmin", "input email : $email")
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "이메일과 비밀번호 입력칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show()
        }
    }

/* --------------------onClick 함수-------------------- */

    fun onClickLogin(view: View) {
        signIn(etEnterEmail.text.toString(), etEnterPassword.text.toString())
    }

    fun onClickRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}