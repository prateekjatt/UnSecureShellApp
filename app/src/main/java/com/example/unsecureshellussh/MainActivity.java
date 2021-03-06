package com.example.unsecureshellussh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_server,btn_shell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_server = findViewById(R.id.button2);
        btn_shell = findViewById(R.id.button3);

        btn_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setBackgroundColor(0xFF000000);
                btn_shell.setBackgroundColor(0xFF535353);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,connection_details.class,null).commit();
            }
        });
        btn_shell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(0xFF000000);
                btn_server.setBackgroundColor(0xFF535353);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,shell.class,null).commit();
            }
        });
    }
}