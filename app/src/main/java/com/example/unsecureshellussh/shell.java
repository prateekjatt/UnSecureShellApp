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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class shell extends Fragment {
    private String hostname;
    private String port;
    private String cwd="~";
    private Button exe;
    private TextView sa;
    private TextView oa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shell, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle b = getArguments();
        if(b != null) {
            hostname = b.getString("hostname");
            if(!hostname.contains("http")) hostname = "http://"+hostname;
            port = b.getString("port");
        }
        else
        {
            hostname="-";
            port = "-";
        }
        ((TextView) getActivity().findViewById(R.id.textView2)).setText("Hostname: "+hostname);
        ((TextView) getActivity().findViewById(R.id.textView3)).setText("Port: "+port);
        sa = getActivity().findViewById(R.id.shellarea);
        oa = getActivity().findViewById(R.id.textView4);
        exe = getActivity().findViewById(R.id.button4);
        exe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommands(sa.getText().toString());
            }
        });
        if(!hostname.equalsIgnoreCase("-") && !port.equalsIgnoreCase("-"))
            sendCommands("");
    }

    private void sendCommands(String command){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    URL url = new URL(hostname+":"+port);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setConnectTimeout(15000);
                    con.setReadTimeout(15000);
                    con.setRequestMethod("POST");

                    OutputStream out = con.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                    bw.write("cd \""+cwd+"\"\n"+command+"\npwd\n");
                    bw.flush();
                    bw.close();


                    InputStream in = con.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String res="";
                    if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                        String line;
                        while((line = br.readLine()) != null)
                        {
                            res += (line+"\n");
                        }
                    }
                    br.close();

                    String oldcwd = cwd;
                    String finalRes = updateCWD(res.split("\n"));
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            oa.append("\n"+oldcwd+">"+command+"\n");
                            oa.append(finalRes);
                        }
                    });
                }
                catch (IOException e){
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            oa.append("Connection Timeout!\n");
                        }
                    });
                }
            }
        }).start();
    }

    public String updateCWD(String[] s) {
        if(s.length != 0)
        {
            if(s[s.length-1].startsWith("Path ")) cwd = s[s.length-1].substring((s[s.length-1].indexOf(":"))+1).trim();
            else cwd = s[s.length-1].trim();
        }
        String res = "";
        for(int i=0;i<s.length-3;i++)
        {
            res += (s[i]+"\n");
        }
        return res;
    }
}