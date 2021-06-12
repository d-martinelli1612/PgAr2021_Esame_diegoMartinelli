package it.unibs.fp.EsameArnaldo;

public class Armata {

    private String tipo;
    private String posizione;
    private String nazione;
    private boolean mosso;

    public Armata(String tipo, String posizione, String nazione) {
        this.tipo = tipo;
        this.posizione = posizione;
        this.nazione = nazione;
        this.mosso = false;
    }

    public Armata(){
        this.tipo = "assente";
        this.posizione = "non schierata";
        this.nazione = "non appartenente";
        this.mosso = false;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPosizione() {
        return posizione;
    }

    public String getNazione() {
        return nazione;
    }

    public boolean isMosso() {
        return mosso;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public void setMosso(boolean mosso) {
        this.mosso = mosso;
    }
}
