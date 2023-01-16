package com.example.gamesuitbinar.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gamesuitbinar.R
import com.example.gamesuitbinar.databinding.DialogGameAnnouncementBinding

class GameDialog(val result: String, val onHomeBtnClicked: ()-> Unit, val onRematchBtnClicked: () -> Unit): DialogFragment() {

    private lateinit var binding: DialogGameAnnouncementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogGameAnnouncementBinding.inflate(inflater)
        val view = binding.root
        dialog!!.setCancelable(false)
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.background_dialog_rounded)

        setupAction()
        return view
    }

    private fun setupAction() {
        binding.apply {
            btnHome.setOnClickListener { onHomeBtnClicked() }
            btnRematch.setOnClickListener { onRematchBtnClicked() }
            tvResult.text = result
        }
    }

    override fun onStart() {
        super.onStart()

        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}