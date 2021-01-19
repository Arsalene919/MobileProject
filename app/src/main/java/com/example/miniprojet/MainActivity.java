package com.example.miniprojet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.example.miniprojet.SharedHelper.sha256;

public class MainActivity extends AppCompatActivity {

    EditText _txtLogin,_txtPassword;
    Button _btnConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _txtLogin = (EditText) findViewById(R.id.txtlogin);
        _txtPassword = (EditText) findViewById(R.id.txtPassword);
        _btnConnection = (Button) findViewById(R.id.btnConnection);

        _btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Login = _txtLogin.getText().toString();
                String Password = _txtPassword.getText().toString();
                BaseD background = new BaseD(MainActivity.this);
                background.execute(Login,Password);
            }
        });


    }

    private class BaseD extends AsyncTask <String,Void,String>{

        AlertDialog dialog;
        Context c;
        public BaseD(Context context){
            this.c = context;
        }

        @Override
        protected void onPreExecute() {

            dialog = new AlertDialog.Builder(c).create();
            dialog.setTitle("Etat de connexion");
        }



        @Override
        protected String doInBackground(String... strings) {
            String result  ="";
            String Login = strings[0];
            String Password = strings[1];
            String connstr = "http://ipaddress/AndroidProjet/login.php";

            try {

                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                System.out.println("***************************************************************************");
                OutputStream ops = http.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data = URLEncoder.encode("Login","UTF-8") + "=" + URLEncoder.encode(Login,"UTF-8") +
                        "&&" + URLEncoder.encode("Password", "UTF-8")+ "=" + URLEncoder.encode(Password,"UTF-8");

                writer.write(data);
                writer.flush();
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String ligne ="";
                while ((ligne = reader.readLine())!= null){
                    result = result + ligne;
                    // ou bien result += ligne;


                }
                reader.close();
                ips.close();
                http.disconnect();

                return result;
            } catch (Exception e) {

                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.setMessage(s);
            try {
                dialog.show();
            } catch (Exception e){
                Log.e("errorpost",e.getMessage());
            }

            if (s.contains("succes")){
                Intent i = new Intent();
                i.setClass(c.getApplicationContext(),bdContacts.class);
                startActivity(i);
            }
        }
    }
}