package it.unibs.fp.EsameArnaldo;

import it.unibs.fp.mylib.InputDati;

import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;

public class Mappa {

    private static final String continuare = "Se si desidera procedere con un nuovo turno digitare 'continua'," +
            "\n\tse ci si vuole fermare digitare 'fine'\n\tInserimento: ";

    private ArrayList<Territorio> territori;

    public Mappa() {
        this.territori = new ArrayList<Territorio>();
    }

    public ArrayList<Territorio> getTerritori() {
        return territori;
    }

    public static void partita(){
        Mappa mappa = new Mappa();
        boolean continua = true;

        try {
            mappa = LettoreXML.leggiXml();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        mappa.stampa();

        while (continua) {
            mappa.movimentiTurno();
            mappa.stampa();

            String interruttore = InputDati.leggiStringa(continuare);

            if (interruttore.equals("fine"))
                continua = false;
        }
    }

    public void stampa(){
        for(int i=0; i < this.getTerritori().size(); i++){
            System.out.println("Nome territorio: " + this.getTerritori().get(i).getNome() +
                    "; Tipo territorio: " + this.getTerritori().get(i).getTipo() +
                    "; Nazione di appartenenza: " + this.getTerritori().get(i).getNazione() + ";");

            System.out.println("\tTipo truppa: " + this.getTerritori().get(i).getArmata().getTipo() +
                    "; Nazione truppa: " + this.getTerritori().get(i).getArmata().getNazione() + ";\n");
        }
    }


    /**Avvia il processo di inserimento mosse, verifica la validita' delle mosse inserite ed esegue
     * quelle che lo sono*/
    public void movimentiTurno(){
        ArrayList<Mossa> mosseTurno = Mossa.inserimentoMosse();
        Mossa mossaAttuale;
        int indicePartenza = 0;
        int indiceDestinazione = 0;
        ArrayList<String> destinazioni = new ArrayList<String>();
        ArrayList<String> destinazioniDaRimuovere = new ArrayList<String>();

        //Imposta tutte le truppe come ancora da muovere
        for (int i=0; i < this.territori.size(); i++){
            this.territori.get(i).getArmata().setMosso(false);
        }

        for (int i=0; i < mosseTurno.size(); i++){
            //verifica la correttezza sintattica di una mossa, oltre alla correttezza dei territori indicati
            if (!this.sintassiCorretta(mosseTurno.get(i))) {
                mosseTurno.remove(i);
                i--;
            }
        }

        for (int i=0; i < mosseTurno.size(); i++) {
            //Se la destinazione indicata non e' gia' stata indicata
            // viene aggiunta alla lista di destinazioni selezionate
            if (!destinazioni.contains(mosseTurno.get(i).getDestinazione())) {
                destinazioni.add(mosseTurno.get(i).getDestinazione());
            } //Se la destinazione e' gia' stata indicata viene indicata come quelle da rimuovere
            else {
                destinazioniDaRimuovere.add(mosseTurno.get(i).getDestinazione());
            }
        }

        //Rimuove i movimenti verso la stessa destinazione
        for (int i=0; i < mosseTurno.size(); i++){
            //Se la destinazione della mossa e' tra quelle da rimuovere viene rimossa direttamente la mossa
            if (destinazioniDaRimuovere.contains(mosseTurno.get(i).getDestinazione())) {
                mosseTurno.remove(i);
                i--;
            }
        }

        for (int i=0; i < mosseTurno.size(); i++){
            mossaAttuale = mosseTurno.get(i);

            for (int j = 0; j < this.territori.size(); j++) {
                if (this.territori.get(j).getNome().equals(mossaAttuale.getPartenza())){
                    indicePartenza = j;
                }

                if (this.territori.get(j).getNome().equals(mossaAttuale.getDestinazione())) {
                    indiceDestinazione = j;
                }
            }

            //verifica che non vi siano truppe
            if (this.territori.get(indiceDestinazione).getArmata().getTipo().equals("assente") &&
                    !this.territori.get(indicePartenza).getArmata().isMosso()){

                //segna che la truppa è stata mossa
                this.territori.get(indicePartenza).getArmata().setMosso(true);

                //Salva l'armata del nuovo territorio occupato
                this.territori.get(indiceDestinazione).setArmata(this.territori.get(indicePartenza).getArmata());
                this.territori.get(indicePartenza).setArmata(new Armata());
            }
        }
    }

    /**Verifica che:
     * La truppa indicata sia del tipo corretto
     * I territori di partenza e di arrivo esistano
     * La destinazione sia confinante alla partenza
     * La destinazione non sia territorio di mare*/
    private boolean sintassiCorretta(Mossa mossa){
        boolean partenzaPresente = false;
        boolean destinazionePresente = false;

        //Verifica che il tipo di truppa sia corretto
        if (!mossa.getTipoTruppa().equals("A"))
            return false;

        //Verifica che partenza e destinazione siano corrette, in caso la destinazione sia H
        // la elimina comunque poiché non causa differenze
        for (int i = 0; i < this.territori.size(); i++) {
            if (this.territori.get(i).getNome().equals(mossa.getPartenza())){
                partenzaPresente = true;

                //Verifica che la destinazione confini con la partenza
                if (!this.territori.get(i).getConfini().contains(mossa.getDestinazione()))
                    return false;
            }

            if (this.territori.get(i).getNome().equals(mossa.getDestinazione())) {
                destinazionePresente = true;

                //Se il territorio di destinazione e' di mare allora non e' valido
                if (this.territori.get(i).getTipo().equals("water"))
                    return false;
            }
        }

        //Se uno dei due territori non e' valido restituisce mossa non valida
        if (!(destinazionePresente && partenzaPresente))
            return false;

        return true;
    }
}
