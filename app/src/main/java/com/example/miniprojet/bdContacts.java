package com.example.miniprojet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class bdContacts extends AppCompatActivity {

    String ms ="";
    LinearLayout LayoutNavigation, LayoutResearch, LayoutData;
    EditText _txtID, _txtName,_txtAddress,_txtTel1, _txtTel2, _txtCompany,_txtResearchContacts;
    ImageButton _btnResearch;
    Button _btnPrevious,_btnNext,_btnCall;
    Button _btnAdd,_btnUpdate,_btnDelete;
    Button _btnCancel,_btnSave;
    int op = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd_contacts);

        LayoutNavigation = (LinearLayout) findViewById(R.id.LayoutNavigation);
        LayoutResearch= (LinearLayout) findViewById(R.id.LayoutResearch);
        LayoutData= (LinearLayout) findViewById(R.id.LayoutData);


        _txtResearchContacts = (EditText) findViewById(R.id.txtResearchContact);
        _txtID = (EditText) findViewById(R.id.txtID);
        _txtName = (EditText) findViewById(R.id.txtName);
        _txtAddress = (EditText) findViewById(R.id.txtAddress);
        _txtTel1 = (EditText) findViewById(R.id.txtTel1);
        _txtTel2 = (EditText) findViewById(R.id.txtTel2);
        _txtCompany = (EditText) findViewById(R.id.txtCompany);

        _btnAdd = (Button) findViewById(R.id.btnAdd);
        _btnCall = (Button) findViewById(R.id.btnCall);
        _btnPrevious = (Button) findViewById(R.id.btnPrevious);
        _btnNext = (Button) findViewById(R.id.btnNext);

        _btnCancel = (Button) findViewById(R.id.btnCancel);
        _btnSave = (Button) findViewById(R.id.btnSave);

        _btnResearch = (ImageButton) findViewById(R.id.btnResearch);


        LayoutNavigation.setVisibility(View.INVISIBLE);
        _btnSave.setVisibility(View.INVISIBLE);
        _btnCancel.setVisibility(View.INVISIBLE);

        _btnResearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // bg_selectContacts bgs =new bg_selectContacts(getApplicationContext());
                //bgs.execute();

            }
        });

        _btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                op=1;
                _txtName.setText("");
                _txtAddress.setText("");
                _txtTel1.setText("");
                _txtTel2.setText("");
                _txtCompany.setText("");

                _btnSave.setVisibility(View.VISIBLE);
                _btnCancel.setVisibility(View.VISIBLE);
                _btnUpdate.setVisibility(View.INVISIBLE);
                _btnDelete.setVisibility(View.INVISIBLE);
                _btnAdd.setEnabled(false);
                LayoutNavigation.setVisibility(View.INVISIBLE);
                LayoutResearch.setVisibility(View.INVISIBLE);
            }
        });
        _btnCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog dial = MyOptions();
                    dial.show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Sélectionner",Toast.LENGTH_SHORT).show();
                }
            }
        });

        _btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String ID = _txtID.getText().toString();
                String Name = _txtName.getText().toString();
                String Address = _txtAddress.getText().toString();
                String Phone1 = _txtTel1.getText().toString();
                String Phone2 = _txtTel2.getText().toString();
                String Company = _txtCompany.getText().toString();
                if (op == 1){
                    bg_insertion_contact bg = new bg_insertion_contact(bdContacts.this);
                    bg.execute(ID,Name,Address,Phone1,Phone2,Company);
                } else if (op == 2) {

                }

                _btnSave.setVisibility(View.INVISIBLE);
                _btnCancel.setVisibility(View.INVISIBLE);
                //_btnUpdate.setVisibility(View.VISIBLE);
                //_btnDelete.setVisibility(View.VISIBLE);

                _btnSave.setVisibility(View.VISIBLE);
                _btnSave.setEnabled(true);
                //_btnUpdate.setEnabled(true);
                _btnResearch.performClick();
                LayoutResearch.setVisibility(View.VISIBLE);
            }
        });
        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 0;

                _btnSave.setVisibility(View.INVISIBLE);
                _btnCancel.setVisibility(View.INVISIBLE);


                _btnSave.setVisibility(View.VISIBLE);
                _btnSave.setEnabled(true);

                LayoutResearch.setVisibility(View.VISIBLE);
            }
        });


    }

    private  class bg_insertion_contact extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;

        public bg_insertion_contact(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(context).create();
            dialog.setTitle("Etat de connexion");
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            String ID = strings[0];
            String Name = strings[1];
            String Address = strings[2];
            String Phone1 = strings[3];
            String Phone2 = strings[4];
            String Company = strings[5];
            String connstr = "http:// ipaddress/Android/bdContacts.php";

            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");
                data += "&"+ URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                data += "&"+ URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(Address, "UTF-8");
                data += "&"+ URLEncoder.encode("Phone1", "UTF-8") + "=" + URLEncoder.encode(Phone1, "UTF-8");
                data += "&"+ URLEncoder.encode("Phone2", "UTF-8") + "=" + URLEncoder.encode(Phone2, "UTF-8");
                data += "&"+ URLEncoder.encode("Company", "UTF-8") + "=" + URLEncoder.encode(Company, "UTF-8");
                writer.write(data);
                Log.v("bdContacts", data);
                writer.flush();
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.setMessage(s);
            dialog.show();
            if (s.contains("success insertion")) {
                Toast.makeText(context, "Contact inséré avec succès.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Problème d'insertion.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void callVisibility(){
        if (((_txtTel1.getText().length() ==0) && (_txtTel2.getText().length() == 0)) ){

            LayoutNavigation.setVisibility(View.INVISIBLE);

        } else  {
            LayoutNavigation.setVisibility(View.VISIBLE);

        }

    }

    private AlertDialog MyOptions(){
        AlertDialog MiDia = new AlertDialog.Builder(this)
                .setTitle("confirmation")
                .setMessage("Séléctionnez le numéro que vous allez appeler")

                .setPositiveButton("Phone1", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //selectionnez le num 1 et appeller
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("Phone:"+_txtTel1.getText().toString().trim()));
                        startActivity(callIntent);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Phone2", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //selectionner le tel2 et appllez
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("Phone:"+_txtTel2.getText().toString().trim()));
                        startActivity(callIntent);
                        dialogInterface.dismiss();
                    }

                })
                .create();
        return MiDia;
    }

        }




