package com.example.a14albertoab.ud_a1a_a14albertoab;

/**
 * Created by alberto on 13/11/2015.
 */
public class Persoa {
    private String nome;
    private String descricion;
    public Persoa(String nome, String descricion){
        this.nome=nome;
        this.descricion=descricion;
    }
    public String getNome(){
        return nome;
    }
    public String getDescricion(){
        return descricion;
    }

    //Os setters non se utilizan nesta aplicacion
    public void setNome(String nome){
        this.nome=nome;
    }
    public void setDescricion(String descricion){
        this.descricion=descricion;
    }
}
