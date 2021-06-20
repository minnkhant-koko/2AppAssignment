package com.eds.a2appstudiointerviewassignment.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.webkit.URLUtil
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.eds.a2appstudiointerviewassignment.R
import com.google.android.material.textfield.TextInputEditText

class AddWebLinkDialog(private val onPressedPositiveButton: (String) -> Unit): DialogFragment(){

    private lateinit var editText: TextInputEditText
    private lateinit var buttonPositive: Button
    private lateinit var buttonNegative: Button

    companion object {
        val TAG = "AddWebLinkDialog"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_add_weblink, null)

        editText = view.findViewById(R.id.textInputEditText)
        buttonPositive = view.findViewById(R.id.buttonPositive)
        buttonNegative = view.findViewById(R.id.buttonNegative)

        val onTextChanged = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                if (!URLUtil.isValidUrl(text)) editText.error = "Must be valid URL"
            }
        }

        editText.addTextChangedListener(onTextChanged)

        buttonPositive.setOnClickListener {
            if (URLUtil.isValidUrl(editText.text.toString())) {
                onPressedPositiveButton(editText.text.toString())
                dismiss()
            }
        }

        buttonNegative.setOnClickListener {
            dismiss()
        }

        builder.setTitle("Add WebLink").setView(view)

        return builder.create()
    }
}