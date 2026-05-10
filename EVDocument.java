/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import javax.swing.text.*;
import javax.swing.*;

public class EVDocument extends PlainDocument { // Spiegazione uguale a IvDocumet

    private JTextField[] allEvFields;
    private int maxTotale = 508;
    private int maxSingolo = 252;

    EVDocument(JTextField[] allEvFields) {
        this.allEvFields = allEvFields;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) return;

        String current = getText(0, getLength());
        String result = current.substring(0, offs) + str + current.substring(offs);

        try {
            int nuovoValore = Integer.parseInt(result);

            if (nuovoValore < 0 || nuovoValore > maxSingolo) return;

            int totaleAltri = 0;
            for (JTextField field : allEvFields) {
                if (field == null) continue;
                // Salta il campo che contiene questo documento
                if (field.getDocument() == this) continue;
                try {
                    Document doc = field.getDocument();
                    String testo = doc.getText(0, doc.getLength());
                    if (!testo.isEmpty()) {
                        totaleAltri += Integer.parseInt(testo);
                    }
                } catch (BadLocationException | NumberFormatException ex) {
                    // campo non leggibile, lo saltiamo
                }
            }

            if (totaleAltri + nuovoValore > maxTotale) return;

            super.insertString(offs, str, a);

        } catch (NumberFormatException e) {
            // Non è un numero, non inserisce nulla
        }
    }
}