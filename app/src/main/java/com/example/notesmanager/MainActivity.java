package com.example.notesmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    static int i = -1;
    ListView lv;
    FloatingActionButton pbtn;
    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static String[] titles = new String[20];
    //boolean f = true;

    //static Set<String> set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listview);




        pbtn = findViewById(R.id.plus);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(arrayAdapter);


        SharedPreferences pref = getSharedPreferences("savingdata", 0);
        i = pref.getInt("ivalue", -1);
        if (i != -1) // if none available
        {
            for (int j = 0; j <= i; j++)
            {
                String temp = pref.getString("sstr" + j, "Sample note");
                arrayList.add(temp);
            }
        }




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                Intent d = new Intent(getApplicationContext(), viewscreen.class);
                d.putExtra("index", index);
                d.putExtra("titles_array", arrayList.get(index));
                //startActivityForResult(d, 2);
                startActivity(d);

            }


        });

        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = i + 1;
                arrayList.add("random Title");
                Intent j = new Intent(getApplicationContext(), a2.class);
                j.putExtra("index", i); //Optional parameters
                startActivityForResult(j, 1);



            }
        });






    }// onCreate

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref = getSharedPreferences("savingdata", 0);
        SharedPreferences.Editor edit= pref.edit();

        edit.putInt("ivalue", i);
        for (int j = 0; j <= i; j++)
        {
            edit.putString("sstr" + j, arrayList.get(j));

        }
        edit.commit();
    }

  /*
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("savingdata", 0);
        //String e = pref.getString("email", "");
        //String s = pref.getString("story", "");
        //email.setText(e);
        //story.setText(s);
        i = pref.getInt("ivalue", -1);
        if (i != 0)
        {
            for (int j = 0; j <= i; j++)
            {
                String temp = pref.getString("sstr" + i, "Sample note");
                arrayList.add(temp);
            }

        }

    }
    */



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
                //Update List
                String ty = data.getStringExtra("titlee");
                arrayList.set(i, ty);
        }
        if (requestCode == 2)
        {
            int indx = data.getIntExtra("ni", -1);
            if (indx != -1)
            {
                arrayList.remove(indx);
                i = i - 1;
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();





            }

        }


    }//onActivityResult




    }



