package com.example.datenbeispiel;

import java.util.Map;

public class Restaurant {

    private Daten daten;
    private Map<String, Speisekarte> speisekarte;

    public Restaurant(Daten daten, Map<String, Speisekarte> speisekarte) {
        this.daten = daten;
        this.speisekarte = speisekarte;
    }

    public Restaurant() {
    }

    public Daten getDaten() {
        return daten;
    }

    public void setDaten(Daten daten) {
        this.daten = daten;
    }

    public Map<String, Speisekarte> getSpeisekarte() {
        return speisekarte;
    }

    public void setSpeisekarte(Map<String, Speisekarte> speisekarte) {
        this.speisekarte = speisekarte;
    }
}