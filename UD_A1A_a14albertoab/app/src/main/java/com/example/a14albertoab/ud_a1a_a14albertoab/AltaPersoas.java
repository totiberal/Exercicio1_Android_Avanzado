package com.example.a14albertoab.ud_a1a_a14albertoab;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLClientInfoException;

public class AltaPersoas extends AppCompatActivity {

    private Datos baseDatos;
    Button btnEngadir;
    EditText nome;
    EditText descricion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_persoas);
        nome=(EditText) findViewById(R.id.idNome);
        descricion=(EditText) findViewById(R.id.idDescriccion);
        btnEngadir=(Button) findViewById(R.id.idEngadir);
        btnEngadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n=nome.getText().toString();
                String d=descricion.getText().toString();
                if(n.equalsIgnoreCase("") || d.equalsIgnoreCase("")) Toast.makeText(getApplicationContext(),"Cubre os 2 campos",Toast.LENGTH_SHORT).show();
                else{
                    baseDatos.engadirPersoa(new Persoa(n,d));
                    Toast.makeText(getApplicationContext(),"Engadido: "+n,Toast.LENGTH_SHORT).show();
                }
                nome.setText("");
                descricion.setText("");
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        if(baseDatos==null){
            baseDatos=new Datos(this);
            baseDatos.sqlLiteDB=baseDatos.getWritableDatabase();
        }
    }
}
