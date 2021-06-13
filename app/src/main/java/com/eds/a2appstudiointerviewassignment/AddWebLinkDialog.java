package com.eds.a2appstudiointerviewassignment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class AddWebLinkDialog extends DialogFragment{

    public static String TAG = "AddWebLinkDialog";

    public interface AddWebLinkDialogListener {
        void clickPositiveButton(String url);
    }

    AddWebLinkDialogListener listener;

    public AddWebLinkDialog() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddWebLinkDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement AddWebLinkDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_weblink, null);
        TextInputEditText editText = view.findViewById(R.id.textInputEditText);
        Button buttonPositive = view.findViewById(R.id.buttonPositive);
        Button buttonNegative = view.findViewById(R.id.buttonNegative);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.length() > 5 && !URLUtil.isValidUrl(text)) {
                    editText.setError("Must be valid URL");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buttonPositive.setOnClickListener(v -> {
            String userData = editText.getText().toString();
            if (URLUtil.isValidUrl(userData)) {
                listener.clickPositiveButton(userData);
                dismiss();
            }
        });
        buttonNegative.setOnClickListener(v -> {
            dismiss();
        });
        builder.setTitle("Add").setView(view);
        return builder.create();
    }
}
