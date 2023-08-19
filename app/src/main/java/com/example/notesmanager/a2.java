package com.example.notesmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;


import static com.example.notesmanager.MainActivity.arrayAdapter;
import static com.example.notesmanager.MainActivity.arrayList;


public class a2 extends AppCompatActivity  {

    int noteIndex;
    EditText title;
    ImageButton ebtn;
    EditText des;
    String dt;
    Vector<String> dictionary;
    static EditText m;
    static boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);


        title = findViewById(R.id.title);
        ebtn = findViewById(R.id.sb1);
        des = findViewById(R.id.description);
        m = findViewById(R.id.mistakes);
        Intent inten = getIntent();
        noteIndex = inten.getIntExtra("index", -1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss", Locale.getDefault());
        dt = "Date: " + sdf.format(new Date());

        dictionary = new Vector<String>();
        flag = true;

        try
        {

            InputStream istream = getResources().openRawResource(R.raw.words);
            BufferedReader fin = new BufferedReader(new InputStreamReader(istream));
            Log.d("***********","Code started");

            while (true)
            {
                String temp = fin.readLine();
                if (temp==null)
                {
                    break;
                }
                dictionary.add(temp);


            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.d("***********","****" +dictionary.size());



        Thread tk = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag)
                {
                    // get text from input
                    String txtInput = des.getText().toString();
                    //Lod.d("***", "*******" + st)


                    //break int into words
                    StringTokenizer st = new StringTokenizer(txtInput);
                    while(st.hasMoreTokens())
                    {
                        String word = st.nextToken();
                        Log.d("****", "word found" + word);
                        boolean wordfound = false;
                        for (int i = 0; i < dictionary.size(); i++)
                        {
                            if (dictionary.elementAt(i).equals(word))
                            {
                                Log.d("****","WOrd Found"+word);
                                wordfound = true;
                                break;

                            }
                        }
                        if (!wordfound)
                        {
                            Log.d("****", "WORD NOT FOUND" + word);
                            final String myword = word;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    m.setText(a2.m.getText().toString() + "\n" + myword);

                                }
                            });
                        }

                    }
                    //compare words into dictionary
                    //words not found added to mistakes
                    //sleep for 5 seconds
                    try
                    {
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                m.setText("");
                            }
                        });

                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arrayAdapter.notifyDataSetChanged();
                                arrayAdapter.notifyDataSetInvalidated();
                            }
                        });
                    }
                    catch (Exception e)
                    {

                    }

                }

            }
        });
        tk.start();










        ebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                if (noteIndex != -1)
                {
                    flag = false;
                    Save(title.getText().toString() + noteIndex, des.getText().toString());
                    Save( ""+ noteIndex, dt);
                    Intent intent = new Intent();
                    intent.putExtra("titlee", title.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();

                }





                //Intent returnIntent = new Intent();
                //setResult(RESULT_CANCELED, returnIntent);
                //finish();






            }
        });




    }// onCreate



    public void Save(String fileName, String s) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(fileName, 0));
            out.write(s);
            out.close();
            //Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }





}
