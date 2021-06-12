package it.unibs.fp.EsameArnaldo;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;

public class LettoreXML {

    private static final String fileDiLettura = "standard_map.xml";

    public static Mappa leggiXml() throws XMLStreamException{

        Mappa mappa = new Mappa();
        Territorio regione = null;
        Armata truppa = null;
        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;

        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(fileDiLettura, new FileInputStream(fileDiLettura));
        }
        catch(Exception e){
            System.out.println("Errore nell'inzializzazione del reader");
            System.out.println(e.getMessage());
        }

        //Non capisco causa errore, sul progetto direttamente creato su PC non da' problemi
        //Nella versione salvata su github non trova l'XML
        while (xmlr.hasNext()){
            switch (xmlr.getEventType()){
                case XMLStreamConstants.START_ELEMENT:
                    if (xmlr.getLocalName().equals("nation")){
                        regione = new Territorio();

                        for (int i=0; i<xmlr.getAttributeCount(); i++){
                            if (xmlr.getAttributeLocalName(i).equals("name")) {
                                regione.setNome(xmlr.getAttributeValue(i));
                            }
                            else if (xmlr.getAttributeLocalName(i).equals("type")) {
                                regione.setTipo(xmlr.getAttributeValue(i));
                            }
                            else if (xmlr.getAttributeLocalName(i).equals("center")) {
                                if (xmlr.getAttributeValue(i).equals("yes"))
                                    regione.setCapitale(true);
                                else if(xmlr.getAttributeValue(i).equals("no"))
                                    regione.setCapitale(false);
                            }
                            else if (xmlr.getAttributeLocalName(i).equals("unit")) {
                                truppa = new Armata(xmlr.getAttributeValue(i), regione.getNome(), regione.getNazione());
                                regione.setArmata(truppa);
                            }
                            else if (xmlr.getAttributeLocalName(i).equals("owner")) {
                                regione.setNazione(xmlr.getAttributeValue(i));
                                regione.getArmata().setNazione(regione.getNazione());
                            }
                        }
                    }

                    else if (xmlr.getLocalName().equals("neighbour")){
                        if (xmlr.getAttributeLocalName(0).equals("name")) {
                            regione.aggiungiConfine(xmlr.getAttributeValue(0));
                        }
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    if (xmlr.getLocalName().equals("nation")){
                        mappa.getTerritori().add(regione);
                    }
                    break;

                default:
                    break;
            }
            xmlr.next();
        }

        return mappa;
    }
}