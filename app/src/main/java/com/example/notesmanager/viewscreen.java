package com.example.notesmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static com.example.notesmanager.MainActivity.arrayAdapter;
import static com.example.notesmanager.MainActivity.arrayList;
import static com.example.notesmanager.MainActivity.i;
import static com.example.notesmanager.MainActivity.titles;

public class viewscreen extends AppCompatActivity {

    int noteIndex;
    String title_vs;
    TextView tv;
    TextView dv;
    TextView dtt;
    ImageButton del;
    ImageButton edt;
    String ddata;
    String dtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewscreen);

        del = findViewById(R.id.dbutton);
        edt = findViewById(R.id.eebutton);
        tv = findViewById(R.id.titleview);
        dv = findViewById(R.id.descriptionview);
        dtt = findViewById(R.id.date);
        Intent intentt = getIntent();
        noteIndex = intentt.getIntExtra("index", -1);
        title_vs = intentt.getStringExtra("titles_array");



        if (noteIndex != -1)
        {
            tv.setText(title_vs);
            ddata = Open(title_vs + ""+noteIndex);
            dv.setText(ddata);
            dtime = Open(noteIndex + "");
            dtt.setText(dtime);




            edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kk = new Intent(getApplicationContext(), editnote.class);
                kk.putExtra("nii", noteIndex);
                kk.putExtra("tv", title_vs);
                kk.putExtra("ddv", ddata);
                startActivity(kk);
            }
            });


            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(viewscreen.this);
                    alert.setMessage("Confirm Deletion?");
                    alert.setTitle("Warning");
                    final LayoutInflater inf = getLayoutInflater();
                    View vvv = inf.inflate(R.layout.dialogcontent, null);
                    alert.setView(vvv);
                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            File dir = getFilesDir();
                            File file = new File(dir, title_vs + "" + noteIndex);
                            boolean deleted = file.delete();

                            if (noteIndex != -1)
                            {
                                arrayList.remove(noteIndex);
                                i = i - 1;
                                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try
                                        {
                                            //Thread.sleep(3000);
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
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                t.start();


                            }


                            //arrayList.remove(noteIndex);



                            //Intent in = new Intent();
                            //in.putExtra("ni", noteIndex);
                            //setResult(RESULT_OK, in);
                            finish();

                        }

                    });

                    alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();
                        }
                    });

                    alert.create();
                    alert.show();



                }

            });
        }
        else
        {
            // enter log
        }














    } // On create

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
