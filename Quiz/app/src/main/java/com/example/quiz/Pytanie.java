package com.example.quiz;

import java.util.ArrayList;
import java.util.List;

public class Pytanie {

/*    public Pytanie() {
        this.odpowiedzi = new ArrayList<>();
    }*/
    private String tresc;
    private List<Odpowiedz> odpowiedzi;
    private String poprawnaOdp;

    public String getTresc() {
        return tresc;
    }
    public List<Odpowiedz> getOdpowiedzi() {
        return odpowiedzi;
    }
    public String getPoprawnaOdp() {
        return poprawnaOdp;
    }


}
