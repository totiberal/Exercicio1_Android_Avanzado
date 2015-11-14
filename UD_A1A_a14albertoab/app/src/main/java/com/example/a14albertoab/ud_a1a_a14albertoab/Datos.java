package com.example.a14albertoab.ud_a1a_a14albertoab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by a14albertoab on 11/11/15.
 */
public class Datos extends SQLiteOpenHelper {

    public SQLiteDatabase sqlLiteDB;
    public final static String NOME_BD="DATOS";
    public final static int VERSION_BD=1;

    //Constructor
    public Datos(Context context){
        super(context, NOME_BD, null, VERSION_BD);
    }

    //Executase a primeira vez
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE PERSOAS (nome varchar(50) PRIMARY KEY, descricion varchar(50))");
    }

    //Executase a partir da segunda vez
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS PERSOAS");
        onCreate(db);
    }

    //Metodo que engade unha nova persoa
    public void engadirPersoa(Persoa p){
        ContentValues valores=new ContentValues();
        valores.put("nome", p.getNome());
        valores.put("descricion", p.getDescricion());
        long id= sqlLiteDB.insert("PERSOAS", null, valores);
    }

    //Metodo que devolve un arraylist con todas as persoas da base de datos
    public ArrayList<Persoa> obterPersoas(){
        ArrayList<Persoa> arrayL=new ArrayList<>();
        //Fago a consulta
        Cursor datosConsulta=sqlLiteDB.rawQuery("SELECT * FROM PERSOAS",null);
        if(datosConsulta.moveToFirst()){
            Persoa p;
            while(!datosConsulta.isAfterLast()){
                p=new Persoa(datosConsulta.getString(0), datosConsulta.getString(1));
                arrayL.add(p);
                datosConsulta.moveToNext();
            }
        }
        return arrayL;
    }
}
