package com.example.notesmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
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

public class editnote extends AppCompatActivity {

    int noteIndex1;
    String ddata;
    String tvv;
    EditText title1;
    ImageButton ebtn1;
    EditText des1;
    String dt;
    Vector<String> dictionary;
    EditText mm;
    static boolean flg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);

        title1 = findViewById(R.id.title1);
        ebtn1 = findViewById(R.id.sbutton1);
        des1 = findViewById(R.id.description1);
        //mm.findViewById(R.id.mistakes1);

        Intent yy = getIntent();
        noteIndex1 = yy.getIntExtra("nii", -1);
        tvv = yy.getStringExtra("tv");
        ddata = yy.getStringExtra("ddv");

        des1.setText(ddata);
        title1.setText(tvv);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss", Locale.getDefault());
        dt = "Date: " + sdf.format(new Date());




        dictionary = new Vector<String>();
        flg = true;
        /*

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



        Thread tkk = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flg)
                {
                    // get text from input
                    String txtInput = des1.getText().toString();
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
                                    mm.setText(mm.getText().toString() + "\n" + myword);

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
                                mm.setText("");
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
        tkk.start();
        */












        ebtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    flg = false;
                    Save(noteIndex1 + "", dt);
                    Save(title1.getText().toString() + noteIndex1, des1.getText().toString());
                    arrayList.set(noteIndex1, tvv);
                    //Intent fh = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(fh);
                    finish();
            }
        });
    } //onCreate











    public void Save(String fileName, String s) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(fileName, 0));
            out.write(s);
            out.close();
            Toast.makeText(this, "Note Edited!", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean FileExists(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public String Open(String fileName) {
        String content = "";
        if (FileExists(fileName)) {
            try {
                InputStream in = openFileInput(fileName);
                if ( in != null) {
                    InputStreamReader tmp = new InputStreamReader( in );
                    BufferedReader reader = new BufferedReader(tmp);
                    String str;
                    StringBuilder buf = new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    } in .close();
                    content = buf.toString();
                }
            } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
                Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return content;
    }




}
