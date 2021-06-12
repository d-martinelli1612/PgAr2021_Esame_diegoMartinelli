package it.unibs.fp.EsameArnaldo;
import it.unibs.fp.mylib.InputDati;

import java.util.ArrayList;

public class Mossa {
    private static final String messaggioTipoTruppa = "Inserire il tipo di truppa\n\tInserimento: ";
    private static final String messaggioLuogoPartenza
            = "Indicare il territorio in cui la truppa e' schierata\n\tInserimento: ";
    private static final String messaggioLuogoDestinazione
            = "Indicare il territorio di destinazione o ordinare H (hold)\n\tInserimento: ";
    private static final String messaggioInserimentoMosse = "Se si desidera compiere altre mosse digitare 'continua'," +
            "\n\tse si desidera terminare l'inserimento digitare 'fine'\n\tInserimento: ";


    private String tipoTruppa;
    private String partenza;
    private String destinazione;

    public Mossa() {
    }

    public String getTipoTruppa() {
        return tipoTruppa;
    }

    public String getPartenza() {
        return partenza;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setTipoTruppa(String tipoTruppa) {
        this.tipoTruppa = tipoTruppa;
    }

    public void setPartenza(String partenza) {
        this.partenza = partenza;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public void inserimentoMossa(){
        this.tipoTruppa = InputDati.leggiStringa(messaggioTipoTruppa);
        this.partenza = InputDati.leggiStringa(messaggioLuogoPartenza);
        this.destinazione = InputDati.leggiStringa(messaggioLuogoDestinazione);
        System.out.println();
    }

    public static ArrayList<Mossa> inserimentoMosse(){
        String inserimento = "";
        Mossa mossa;
        ArrayList<Mossa> mosseTurno = new ArrayList<Mossa>();
        while (!inserimento.equals("fine")){
            inserimento = InputDati.leggiStringa(messaggioInserimentoMosse);

            if (inserimento.equals("continua")) {
                mossa = new Mossa();
                mossa.inserimentoMossa();
                mosseTurno.add(mossa);
            }
        }
        return mosseTurno;
    }
}
