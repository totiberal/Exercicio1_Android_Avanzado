package com.example.a14albertoab.ud_a1a_a14albertoab;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class CargarDatos extends AppCompatActivity {

    private Datos baseDatos;
    Spinner spinner;
    TextView textView;
    Button btnGardar;
    int numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_datos);
        baseDatos=new Datos(this);
        baseDatos.sqlLiteDB=baseDatos.getReadableDatabase();
        spinner=(Spinner) findViewById(R.id.idSpinner);
        textView=(TextView) findViewById(R.id.idTV);
        btnGardar=(Button) findViewById(R.id.gardar);
        final ArrayList<Persoa> arrayL=baseDatos.obterPersoas();
        ArrayList<String> arrayS=new ArrayList<>();
        //Creo un arraylist solo cos nomes a partir da consulta anterior para meter solo os nomes no spinner
        for(int x=0;x<arrayL.size();x++){
            Persoa p=arrayL.get(x);
            arrayS.add(p.getNome());
        }
        //Creo o adaptador
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayS);
        spinner.setAdapter(adaptador);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Persoa p = arrayL.get(position);
                textView.setText(p.getDescricion());
                numero=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Este try impide que se peche a aplicacion se non hai datos no spinner
                try{
                    //Comprobo que a sd esta montada
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String valor_por_defecto="/DATOS/";
                        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        //Collo a key das preferencias
                        String lugarGardar = preferencias.getString("chave", valor_por_defecto);
                        File ruta = new File(Environment.getExternalStorageDirectory(), lugarGardar);
                        if (ruta.exists() == false) ruta.mkdirs();
                        Persoa p = arrayL.get(numero);
                        //Creo o txt co nome da persoa
                        File ficheiroTexto = new File(ruta, p.getNome() + ".txt");
                        try {
                            //Meto o nome e a descricion da persoa
                            FileOutputStream fos = new FileOutputStream(ficheiroTexto);
                            OutputStreamWriter osr = new OutputStreamWriter(fos);
                            osr.write(p.getNome() + ": " + p.getDescricion() + "\n");
                            osr.close();
                            Toast.makeText(getApplicationContext(),"Gardado: "+p.getNome(),Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {
                            Log.e("CARGAR DATOS", "ERRO GARDANDO EN .TXT");
                        }
                    }else Toast.makeText(getApplicationContext(),"Non hai SD Card",Toast.LENGTH_LONG).show();
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Primeiro tes que introducir datos",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
