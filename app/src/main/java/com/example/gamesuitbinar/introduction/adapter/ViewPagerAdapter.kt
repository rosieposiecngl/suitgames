package com.example.gamesuitbinar.introduction.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gamesuitbinar.introduction.content.FirstIntroductionFragment
import com.example.gamesuitbinar.introduction.content.SecondIntroductionFragment
import com.example.gamesuitbinar.introduction.content.ThirdIntroductionFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentData = listOf(
        FirstIntroductionFragment.newInstance(),
        SecondIntroductionFragment.newInstance(),
        ThirdIntroductionFragment.newInstance()
    )

    override fun getItemCount(): Int {
        return fragmentData.size
    }

    override fun createFragment(position: Int): Fragment = fragmentData[position]


    fun getRegisteredFragment(position: Int): Fragment = fragmentData[position]
}