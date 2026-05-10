/*
                        --- Progetto Java Swing --- 
                              POKEMON MANAGER
                  Autori: Sacchi Emanuele, Angelomaria Gurraj
                  Classe: 4G - Gruppo 10
                  Mese e Anno: Maggio 2026

*/
import javax.swing.text.*;

// Documento personalizzato per i campi IV che accetta solo numeri interi
// tra 0 e 31.
// PlainDocument è la classe base usata da JTextField per gestire il
// testo internamente —
// estendendola possiamo controllare ogni inserimento prima che venga effettivamente
// scritto nel campo.
public class IVDocument extends PlainDocument {
    
    // insertString viene chiamato automaticamente ogni volta
    // che l'utente digita un carattere
    // o incolla del testo nel campo. 
    // Sovrascrivendolo decidiamo noi se accettare o rifiutare l'input.
    // offs = posizione del cursore, str = testo da inserire, 
    // a = attributi del testo (stile ecc.)
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) return;
        
        // Simula il risultato dell'inserimento senza ancora scriverlo nel documento:
        // prende il testo attuale, inserisce str nella posizione offs e controlla se il risultato è valido
        String current = getText(0, getLength());
        String result = current.substring(0, offs) + str + current.substring(offs);
        
        try {
            int value = Integer.parseInt(result);
            // Solo se il valore è tra 0 e 31 chiama il metodo originale che scrive davvero nel campo
            if (value >= 0 && value <= 31) {
                super.insertString(offs, str, a);
            }
            // Se fuori range non fa nulla — il carattere viene silenziosamente ignorato
        } catch (NumberFormatException e) {
            // Se il risultato non è un numero (es. l'utente ha scritto una lettera) non inserisce nulla
        }
    }
}