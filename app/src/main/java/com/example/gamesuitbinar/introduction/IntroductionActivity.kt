package com.example.gamesuitbinar.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.gamesuitbinar.MenuActivity
import com.example.gamesuitbinar.databinding.ActivityIntroductionBinding
import com.example.gamesuitbinar.introduction.adapter.ViewPagerAdapter
import com.example.gamesuitbinar.introduction.content.ThirdIntroductionFragment
import com.google.android.material.tabs.TabLayoutMediator

class IntroductionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroductionBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpAction()
        setupView()
    }

    private fun setupView() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.apply {
            viewPagerIntro.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayoutIntro, viewPagerIntro) { _, _ -> }.attach()
            setupOnSlideChanged()
        }
    }

    private fun setUpAction() {
        binding.apply {
            buttonPlay.setOnClickListener {
                val intent = Intent(this@IntroductionActivity, MenuActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
                finish()
            }
        }
    }

    fun setEnableButtonPlay(boolean: Boolean) {
        binding.apply {
            if (boolean) {
                buttonPlay.isEnabled = true
                buttonPlay.isClickable = true
                buttonPlay.visibility = View.VISIBLE
            } else {
                buttonPlay.isEnabled = false
                buttonPlay.isClickable = false
                buttonPlay.visibility = View.INVISIBLE
            }
        }
        Log.d("INTRO ACT", "Test function called!")
    }

    fun setName(name: String) {
        this.name = name
    }

    private fun setupOnSlideChanged() {
        binding.apply {
            viewPagerIntro.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> buttonPlay.visibility = View.INVISIBLE
                        1 -> buttonPlay.visibility = View.INVISIBLE
                        2 -> {
                            val fragment =
                                viewPagerAdapter.getRegisteredFragment(viewPagerIntro.currentItem)
                            if (fragment is ThirdIntroductionFragment) {
                                if (fragment.getInputName().isNotBlank()) {
                                    buttonPlay.visibility = View.VISIBLE
                                } else {
                                    buttonPlay.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }

                    super.onPageSelected(position)
                }
            })
        }
    }

}
