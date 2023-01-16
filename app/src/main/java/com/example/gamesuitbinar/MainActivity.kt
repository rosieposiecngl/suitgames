package com.example.gamesuitbinar

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gamesuitbinar.databinding.ActivityMainBinding
import com.example.gamesuitbinar.dialog.GameDialog
import com.example.gamesuitbinar.model.MatchResult
import com.example.gamesuitbinar.model.Player
import com.example.gamesuitbinar.util.AppConfiguration.MODE_VS_COM
import com.example.gamesuitbinar.util.AppConfiguration.MODE_VS_PLAYER

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var gameMode: String
    private lateinit var name: String
    private var dialog: GameDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpGame()
        setUpView()
        setUpAction()
    }

    private fun setUpView() {
        val text = if (gameMode == MODE_VS_PLAYER) "Player 2" else "CPU"
        binding.apply {
            tvPlayer2.text = text
            tvPlayer1.text = name

            Glide.with(this@MainActivity)
                .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.appLogo)
        }
    }

    private fun setUpGame() {
        player1 = Player()
        player2 = Player()
        gameMode = intent.getStringExtra("gameMode").toString()
        name = intent.getStringExtra("name").toString()
    }

    private fun setUpAction() {
        binding.apply {
            btnRockPlayer.setOnClickListener {
                onPlayerSelect(ROCK)
            }
            btnPaperPlayer.setOnClickListener {
                onPlayerSelect(PAPER)
            }
            btnScissorPlayer.setOnClickListener {
                onPlayerSelect(SCISSOR)
            }
            btnRefresh.setOnClickListener {
                reset()
            }
            btnClose.setOnClickListener {
                finish()
            }
        }
    }

    private fun onPlayerSelect(choice: String) {
        // Set player selection
        player1.choice = choice
        playerOneSelectedButton(choice)

        when (gameMode) {
            MODE_VS_PLAYER -> {
                getPlayerTwoChoice()
            }
            MODE_VS_COM -> {
                getComChoice()
            }
        }
    }

    private fun getPlayerTwoChoice() {
        binding.apply {
            btnPaperPlayerTwo.apply {
                isClickable = true
                setOnClickListener {
                    playerTwoSelectedButton(PAPER)
                    player2.choice = PAPER
                    getWinnerVsPlayer()
                }

            }

            btnRockPlayerTwo.apply {
                isClickable = true
                setOnClickListener {
                    playerTwoSelectedButton(ROCK)
                    player2.choice = ROCK
                    getWinnerVsPlayer()
                }
            }

            btnScissorPlayerTwo.apply {
                isClickable = true
                setOnClickListener {
                    playerTwoSelectedButton(SCISSOR)
                    player2.choice = SCISSOR
                    getWinnerVsPlayer()
                }
            }
        }
    }

    private fun getComChoice() {
        // Get Random choice from com & set selected button based on choice
        player2.choice = listChoice.random()
        comSelectedButton(player2.choice!!)

        // Show log player and com choices
        Log.d("PLAYER", player1.choice!!)
        Log.d("COM", player2.choice!!)

        getWinnerVsCom()
    }

    private fun getWinnerVsCom() {
        when (player1.choice) {
            ROCK -> {
                when (player2.choice) {
                    ROCK -> setMatchResult(MatchResult.DRAW)
                    PAPER -> setMatchResult(MatchResult.COM_WINS)
                    SCISSOR -> setMatchResult(MatchResult.PLAYER_ONE_WINS)
                }
            }
            PAPER -> {
                when (player2.choice) {
                    ROCK -> setMatchResult(MatchResult.PLAYER_ONE_WINS)
                    PAPER -> setMatchResult(MatchResult.DRAW)
                    SCISSOR -> setMatchResult(MatchResult.COM_WINS)
                }
            }
            SCISSOR -> {
                when (player2.choice) {
                    ROCK -> setMatchResult(MatchResult.COM_WINS)
                    PAPER -> setMatchResult(MatchResult.PLAYER_ONE_WINS)
                    SCISSOR -> setMatchResult(MatchResult.DRAW)
                }
            }
        }
        player2.choice
    }

    private fun getWinnerVsPlayer() {
        when (player1.choice) {
            ROCK -> {
                when (player2.choice) {
                    ROCK -> setMatchResult(MatchResult.DRAW)
                    PAPER -> setMatchResult(MatchResult.PLAYER_TWO_WINS)
                    SCISSOR -> setMatchResult(MatchResult.PLAYER_ONE_WINS)
                }
            }
            PAPER -> {
                when (player2.choice) {
                    ROCK -> setMatchResult(MatchResult.PLAYER_ONE_WINS)
                    PAPER -> setMatchResult(MatchResult.DRAW)
                    SCISSOR -> setMatchResult(MatchResult.PLAYER_TWO_WINS)
                }
            }
            SCISSOR -> {
                when (player2.choice) {
                    ROCK -> setMatchResult(MatchResult.PLAYER_TWO_WINS)
                    PAPER -> setMatchResult(MatchResult.PLAYER_ONE_WINS)
                    SCISSOR -> setMatchResult(MatchResult.DRAW)
                }
            }
        }
        player2.choice
    }

    private fun setMatchResult(result: MatchResult) {
        // Change Announcements words
        when (result) {
            MatchResult.PLAYER_ONE_WINS -> {
                setWinnerAnnouncementDialog("Player One wins!")
            }
            MatchResult.PLAYER_TWO_WINS -> {
                setWinnerAnnouncementDialog("Player Two wins!")
            }
            MatchResult.COM_WINS -> {
                setWinnerAnnouncementDialog("Com wins!")
            }
            MatchResult.DRAW -> {
                setWinnerAnnouncementDialog("Draw!")
            }
        }
    }

    private fun setWinnerAnnouncementDialog(message: String) {
        dialog = GameDialog(
            result = message,
            onHomeBtnClicked = {
                Log.d("MAIN ACT", "home clicked!")
                dialog?.dismiss()
                reset()
                finish()
            },
            onRematchBtnClicked = {
                Log.d("MAIN ACT", "rematch clicked!")
                reset()
                dialog?.dismiss()
            }
        )
        dialog?.show(supportFragmentManager, "customDialog")
    }

    private fun reset() {
        resetButton()
        resetChoice()
        resetAnnouncment()
    }

    private fun resetAnnouncment() {
        binding.tvAnnouncement.apply {
            textSize = 48.0f
            setTextColor(AppCompatResources.getColorStateList(this@MainActivity, R.color.red))
            backgroundTintList =
                AppCompatResources.getColorStateList(this@MainActivity, R.color.yellow)
        }
    }

    private fun resetChoice() {
        player1.choice = null
        player2.choice = null
    }

    private fun resetButton() {
        binding.apply {
            // Reset player choice buttons
            btnRockPlayer.apply {
                backgroundTintList =
                    AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                isClickable = true
            }
            btnPaperPlayer.apply {
                backgroundTintList =
                    AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                isClickable = true
            }
            btnScissorPlayer.apply {
                backgroundTintList =
                    AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                isClickable = true
            }

            // Reset com choice buttons
            btnRockPlayerTwo.apply {
                backgroundTintList =
                    AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                isClickable = false
            }
            btnPaperPlayerTwo.apply {
                backgroundTintList =
                    AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                isClickable = false
            }
            btnScissorPlayerTwo.apply {
                backgroundTintList =
                    AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                isClickable = false
            }

        }
    }

    private fun playerOneSelectedButton(choice: String) {
        binding.apply {
            when (choice) {
                ROCK -> {
                    btnRockPlayer.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity,
                            androidx.appcompat.R.color.material_grey_300
                        )
                    btnPaperPlayer.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                    btnScissorPlayer.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                }

                PAPER -> {
                    btnRockPlayer.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                    btnPaperPlayer.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity,
                            androidx.appcompat.R.color.material_grey_300
                        )
                    btnScissorPlayer.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                }

                SCISSOR -> {
                    btnRockPlayer.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                    btnPaperPlayer.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                    btnScissorPlayer.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity,
                            androidx.appcompat.R.color.material_grey_300
                        )
                }
            }
            btnRockPlayer.isClickable = false
            btnPaperPlayer.isClickable = false
            btnScissorPlayer.isClickable = false
        }
    }

    private fun playerTwoSelectedButton(choice: String) {
        binding.apply {
            when (choice) {
                ROCK -> {
                    btnRockPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity,
                            androidx.appcompat.R.color.material_grey_300
                        )
                    btnPaperPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                    btnScissorPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                }

                PAPER -> {
                    btnRockPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                    btnPaperPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity,
                            androidx.appcompat.R.color.material_grey_300
                        )
                    btnScissorPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                }

                SCISSOR -> {
                    btnRockPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                    btnPaperPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(this@MainActivity, R.color.white)
                    btnScissorPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity,
                            androidx.appcompat.R.color.material_grey_300
                        )
                }
            }
            btnRockPlayerTwo.isClickable = false
            btnPaperPlayerTwo.isClickable = false
            btnScissorPlayerTwo.isClickable = false
        }
    }

    private fun comSelectedButton(choice: String) {
        binding.apply {
            when (choice) {
                ROCK -> {
                    btnRockPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity, androidx.appcompat.R.color.material_grey_300
                        )
                    btnPaperPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity, R.color.white
                        )
                    btnScissorPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity, R.color.white
                        )
                }

                PAPER -> {
                    btnRockPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity, R.color.white
                        )
                    btnPaperPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity, androidx.appcompat.R.color.material_grey_300
                        )
                    btnScissorPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity, R.color.white
                        )
                }

                SCISSOR -> {
                    btnRockPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity, R.color.white
                        )
                    btnPaperPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity, R.color.white
                        )
                    btnScissorPlayerTwo.backgroundTintList =
                        AppCompatResources.getColorStateList(
                            this@MainActivity, androidx.appcompat.R.color.material_grey_300
                        )
                }
            }
        }
    }

    companion object {
        const val SCISSOR = "SCISSOR"
        const val ROCK = "ROCK"
        const val PAPER = "PAPER"

        val listChoice = listOf(ROCK, SCISSOR, PAPER)
    }
}