package com.example.ap2_ex3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ServerIPDialogFragment extends DialogFragment {

    private EditText ipEditText;
    private EditText portEditText;

    private OnServerIPChangedListener listener;

    public interface OnServerIPChangedListener {
        void onServerIPChanged(String serverIP, String serverPort);
    }

    public void setOnServerIPChangedListener(OnServerIPChangedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Change Server IP");

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_server_ip, null);
        builder.setView(dialogView);

        ipEditText = dialogView.findViewById(R.id.editTextIP);
        portEditText = dialogView.findViewById(R.id.editTextPort);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String serverIP = ipEditText.getText().toString();
                String serverPort = portEditText.getText().toString();

                if (listener != null) {
                    listener.onServerIPChanged(serverIP, serverPort);
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }
}

