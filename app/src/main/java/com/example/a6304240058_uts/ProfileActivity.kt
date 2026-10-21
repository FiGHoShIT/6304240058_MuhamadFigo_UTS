package com.example.a6304240058_uts

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<TextView>(R.id.tvName).text =
            "Muhamad Figo"

        findViewById<TextView>(R.id.tvEmail).text =
            "mfigo8486@gmail.com"

        findViewById<TextView>(R.id.tvPhone).text =
            "No. HP: 083173807591"

        findViewById<TextView>(R.id.tvProdi).text =
            "Program Studi: Rekayasa Perangkat Lunak"

        findViewById<TextView>(R.id.tvJurusan).text =
            "Jurusan: Teknik Informatika"

        findViewById<TextView>(R.id.tvCampus).text =
            "Politeknik Negeri Bengkalis"

        val bottomNavigation =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.selectedItemId = R.id.nav_profile

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_products -> {
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    finish()
                    true
                }

                R.id.nav_profile -> true

                else -> false
            }
        }
    }
}