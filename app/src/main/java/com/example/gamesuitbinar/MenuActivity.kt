package com.example.gamesuitbinar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.gamesuitbinar.databinding.ActivityMenuBinding
import com.example.gamesuitbinar.util.AppConfiguration.MODE_VS_COM
import com.example.gamesuitbinar.util.AppConfiguration.MODE_VS_PLAYER
import com.google.android.material.snackbar.Snackbar

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        getIntentArg()
        setContentView(view)

        setupView()
        setupAction()
        showWelcomeSnackbar()
    }

    private fun setupView() {
        binding.apply {
            // Setup text player vs player
            val textVsPlayer = "$name vs Player"
            tvVsPemain.text = textVsPlayer

            // Setup text player vs computer
            val textVsCom = "$name vs CPU"
            tvVsComputer.text = textVsCom
        }
    }

    private fun setupAction() {
        binding.apply {
            menuPlayerVsPlayer.setOnClickListener {
                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                intent.putExtra("gameMode", MODE_VS_PLAYER)
                intent.putExtra("name", name)

                startActivity(intent)
            }

            menuPlayerVsCom.setOnClickListener {
                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                intent.putExtra("gameMode", MODE_VS_COM)
                intent.putExtra("name", name)

                startActivity(intent)
            }
        }
    }

    private fun showWelcomeSnackbar() {
        val snackbar = Snackbar.make(binding.root, "Selamat datang $name", Snackbar.LENGTH_SHORT)
        snackbar.setAction("Tutup", {
            Log.d("SNACKBAR", "Tutup clicked!")
        })
        snackbar.show()
    }

    private fun getIntentArg() {
        name = intent.getStringExtra("name")
    }
}