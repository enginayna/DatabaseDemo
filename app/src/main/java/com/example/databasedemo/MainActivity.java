package com.example.databasedemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editTexting;
    private EditText editTexttr;
    private Button buttonSave;
    private Button buttonLists;
    private  Button buttonDelete;
    private Button buttonUpdate;
    private EditText editTextList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTexting = findViewById(R.id.editTexting);
        editTexttr = findViewById(R.id.editTexttr);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        editTextList = findViewById(R.id.editTextList);

        buttonSave = findViewById(R.id.buttonSave);
        buttonLists = findViewById(R.id.buttonLists);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ing = editTexting.getText().toString();
                String tr = editTexttr.getText().toString();

                if(ing.isEmpty() || tr.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Alanlar bos birakilamaz" , Toast.LENGTH_SHORT).show();

                }
                else{Dictionary dictioanary = new Dictionary(ing , tr);
                    Database db = new Database(getApplicationContext());
                    long word_id = db.addWord(dictioanary);

                    if (word_id > 0){
                        Toast.makeText(getApplicationContext() , "Kayit basarili ID = " + word_id ,
                                Toast.LENGTH_SHORT ).show();
                        editTexting.setText("");
                        editTexttr.setText("");
                    }
                    else{
                        Toast.makeText(getApplicationContext() , "Kayit Basarisiz" , Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

      buttonDelete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            View design =getLayoutInflater().inflate(R.layout.alert_design , null);
            final EditText editTextNumber = design.findViewById(R.id.editTextNumber);

              AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
              alert.setMessage("Delete Word Id");
              alert.setView(design);
              alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      String i =  editTextNumber.getText().toString();
                      if(i.isEmpty()){
                          Toast.makeText(getApplicationContext() , "its was not entered number" , Toast.LENGTH_SHORT).show();
                      }
                      else{
                          Database database = new Database(getApplicationContext());
                          database.delete(Integer.valueOf(i));
                          Toast.makeText(getApplicationContext() , "Deleted word id" + i , Toast.LENGTH_SHORT).show();
                      }

                  }
              });
              alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      Toast.makeText(getApplicationContext() , "Cacelled" , Toast.LENGTH_SHORT).show();
                  }
              });
                alert.create().show();
          }
      });

      buttonLists.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ArrayList<Dictionary> list = new ArrayList<Dictionary>();
              Database db = new Database(getApplicationContext());
              list = db.allWords();
              StringBuilder sb = new StringBuilder();
              for (Dictionary dictionary : list){
                String text = "";
                text = "ID : " + dictionary.getWord_id() + " Ingilizce : " + dictionary.getIngilizce() + " Turkce : " + dictionary.getTurkce() + "\n\n";
                sb.append(text);
              }
              editTextList.setText(sb);

          }
      });

      buttonUpdate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            View design = getLayoutInflater().inflate(R.layout.update_alert , null);
            final EditText editText = design.findViewById(R.id.editText);
            final EditText editText2 = design.findViewById(R.id.editText2);
            final EditText editText3 = design.findViewById(R.id.editText3);
            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setMessage("Update");
            alert.setView(design);
            alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(editText.getText().toString().isEmpty()|| editText2.getText().toString().isEmpty()|| editText3.getText().toString().isEmpty()){
                      Toast.makeText(getApplicationContext() , "Alanlari bos birakmayin" , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Database database = new Database(getApplicationContext());
                        database.update(Integer.valueOf(editText.getText().toString()) , editText2.getText().toString() , editText3.getText().toString());
                        Toast.makeText(getApplicationContext() , "Updated Word ID : " +Integer.valueOf(editText.getText().toString()) , Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext() , "Cacelled" , Toast.LENGTH_SHORT).show();
                }
            });
            alert.create().show();

          }
      });
    }
}
