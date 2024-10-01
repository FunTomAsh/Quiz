package com.example.quiz;

import java.util.ArrayList;
import java.util.List;

public class Temat {

/*    public Temat() {
        this.pytania = new ArrayList<>();
    }*/

    private String nazwa;
    /*private String nazwaZdjecia;*/
    private String zdjecie;
    private List<Pytania> pytania;

   /* public String getNazwaZdjecia() {
        return nazwaZdjecia;
    }*/

    public String getNazwa() {
        return nazwa;
    }
    public String getZdjecie() {
        return zdjecie;
    }
    public List<Pytania> getPytania() {
        return pytania;
    }

}
