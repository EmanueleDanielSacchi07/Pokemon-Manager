import javax.swing.text.*;

public class IVDocument extends PlainDocument {
    
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) return;
        
        // Costruisce la stringa risultante dopo l'inserimento
        String current = getText(0, getLength());
        String result = current.substring(0, offs) + str + current.substring(offs);
        
        try {
            int value = Integer.parseInt(result);
            if (value >= 0 && value <= 31) {
                super.insertString(offs, str, a);
            }
        } catch (NumberFormatException e) {
            // Non è un numero, non inserisce nulla
        }
    }
}