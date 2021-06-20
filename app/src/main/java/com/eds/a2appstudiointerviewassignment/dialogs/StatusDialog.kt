package com.eds.a2appstudiointerviewassignment.dialogs

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.eds.a2appstudiointerviewassignment.R

class StatusDialog(private val status: String, private val loading: Boolean): DialogFragment() {

    private lateinit var lav: LottieAnimationView
    private lateinit var textViewStatus: TextView

    companion object {
        val TAG = "StatusDialog"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_status, null)

        lav = view.findViewById(R.id.statusAnimationView)
        textViewStatus = view.findViewById(R.id.textViewStatus)

        lav.apply {
            if (loading)
                setAnimation(R.raw.lottie_loading)
            else
                setAnimation(R.raw.lottie_error)
        }

        textViewStatus.text = status

        builder.setView(view)

        return builder.create().also {
            it.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        }
    }
}