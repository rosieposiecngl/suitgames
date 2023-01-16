package com.example.gamesuitbinar.introduction.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gamesuitbinar.R

class FirstIntroductionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_introduction, container, false)
    }

    companion object{
        fun newInstance() = FirstIntroductionFragment()
    }
}