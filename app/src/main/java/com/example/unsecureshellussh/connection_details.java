package com.example.unsecureshellussh;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class connection_details extends Fragment {
    private Button save;
    private TextView hostname;
    private TextView port;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connection_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        save = getActivity().findViewById(R.id.button);
        hostname = getActivity().findViewById(R.id.hostname);
        port = getActivity().findViewById(R.id.port);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("hostname",hostname.getText().toString());
                b.putString("port",port.getText().toString());
                ((Button)getActivity().findViewById(R.id.button3)).setBackgroundColor(0xFF000000);
                ((Button)getActivity().findViewById(R.id.button2)).setBackgroundColor(0xFF535353);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,shell.class,b).commit();
            }
        });

    }
}