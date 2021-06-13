package com.eds.a2appstudiointerviewassignment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;

public class StatusDialog extends DialogFragment {
    public static String TAG = "StatusDialog";
    private final boolean isLoading;
    private final String status;

    public StatusDialog(boolean isLoading, String status) {
        this.isLoading = isLoading;
        this.status = status;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_status, null);
        LottieAnimationView lav = view.findViewById(R.id.statusAnimationView);
        TextView tvStatus = view.findViewById(R.id.textViewStatus);
        if (isLoading) {
            lav.setAnimation(R.raw.lottie_loading);
        } else {
            lav.setAnimation(R.raw.lottie_error);
        }
        tvStatus.setText(status);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
                .TRANSPARENT));
        return dialog;
    }
}
