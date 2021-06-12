package it.unibs.fp.EsameArnaldo;

import java.util.ArrayList;

public class Territorio {

    private String nome;
    private String tipo;
    private String nazione;
    private boolean capitale;
    private Armata armata;
    private ArrayList<String> confini;

    public Territorio() {
        this.confini = new ArrayList<String>();
        this.armata = new Armata();
        this.nazione = "non appartenente";
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNazione() {
        return nazione;
    }

    public Armata getArmata() {
        return armata;
    }

    public ArrayList<String> getConfini() {
        return confini;
    }

    public boolean isCapitale() {
        return capitale;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public void setArmata(Armata armata) {
        this.armata = armata;
    }

    public void setConfini(ArrayList<String> confini) {
        this.confini = confini;
    }

    public void setCapitale(boolean capitale) {
        this.capitale = capitale;
    }

    public void aggiungiConfine(String confine){
        this.confini.add(confine);
    }
}