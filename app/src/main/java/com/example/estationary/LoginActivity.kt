package com.example.estationary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import com.example.estationary.db.loginDB
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //menghilangkan actionbar
        supportActionBar?.hide()

        //show or hide password
        checkbox.setOnClickListener {
            if (checkbox.isChecked) {
                password.inputType = 1
            } else
                password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        //koneksi com.example.estationary.db
        var helper = loginDB(applicationContext)
        var db = helper.readableDatabase

        //action untuk tombol login
        imageButton.setOnClickListener {
            var args = listOf<String>(username.text.toString(), password.text.toString()).toTypedArray()
            var rs = db.rawQuery("SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?",args)
            if (rs.moveToNext()) {
                Toast.makeText(applicationContext, "Yeay, Selamat Datang...",Toast.LENGTH_LONG).show()
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
                Toast.makeText(applicationContext, "Yahhh, Ada yang salah nih...",Toast.LENGTH_LONG).show()
        }
    }
}