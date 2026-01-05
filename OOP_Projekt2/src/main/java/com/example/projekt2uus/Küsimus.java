package com.example.projekt2uus;

import java.util.List;

public class Küsimus {
    private String tekst; // Küsimus
    private List<String> valikud; // Valikvastused küsimusele
    private int õige; // Õige vastuse indeks

    public Küsimus(String tekst, List<String> valikud, int õige) {
        this.tekst = tekst;
        this.valikud = valikud;
        this.õige = õige;
    }

    public String getTekst() {
        return tekst;
    }

    public List<String> getValikud() {
        return valikud;
    }

    public int getÕige() {
        return õige;
    }
}
