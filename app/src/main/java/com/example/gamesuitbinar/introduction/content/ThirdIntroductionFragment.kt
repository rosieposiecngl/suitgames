package com.example.gamesuitbinar.introduction.content

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.gamesuitbinar.databinding.FragmentThirdIntroductionBinding
import com.example.gamesuitbinar.introduction.IntroductionActivity

class ThirdIntroductionFragment : Fragment() {

    private lateinit var binding: FragmentThirdIntroductionBinding
    private lateinit var parentActivity: IntroductionActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThirdIntroductionBinding.inflate(inflater)
        val view = binding.root
        parentActivity = requireActivity() as IntroductionActivity


        binding.editTextNama.doOnTextChanged { text, start, before, count ->
            Log.d("NAMA", text.toString())
            parentActivity.setName(text.toString())
            if (!text.isNullOrBlank()){
                parentActivity.setEnableButtonPlay(true)
            } else {
                parentActivity.setEnableButtonPlay(false)
            }
        }
        return view
    }

    fun getInputName() = binding.editTextNama.text.toString()

    companion object{
        fun newInstance() = ThirdIntroductionFragment()
    }
}