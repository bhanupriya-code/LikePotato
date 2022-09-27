package com.example.gullutesting3.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gullutesting3.R;

public class MessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        ImageView button = (ImageView) view.findViewById(R.id.button2);
        EditText editText = (EditText)view.findViewById(R.id.editTextTextPersonName);
        ImageView button1 = (ImageView) view.findViewById(R.id.button3);
        EditText editText1 = (EditText)view.findViewById(R.id.editTextTextPersonName2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You are the best!", Toast.LENGTH_SHORT).show();
                String whatsAppMessage = editText.getText().toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
                sendIntent.setType("text/plain");
                // Do not forget to add this to open whatsApp App specifically
                sendIntent.setPackage("com.whatsapp");
                if ( sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity( sendIntent);
                }else{
                    // this else block will be executed when your intent is not working
                    Toast.makeText(getActivity(), "nai krra kaam", Toast.LENGTH_SHORT).show();
                }

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Ooo La La La", Toast.LENGTH_SHORT).show();
                String mail = editText1.getText().toString();
                String[] addresses = {"gmail.com"};
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Wahi aur kya!!");
                intent.putExtra(Intent.EXTRA_TEXT, mail);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }else{
                    // this else block will be executed when your intent is not working
                    Toast.makeText(getActivity(), "nai krra kaam", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}