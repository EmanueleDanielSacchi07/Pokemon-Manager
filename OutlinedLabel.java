import javax.swing.JLabel;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;

public class OutlinedLabel extends JLabel {

    private Color coloreContorno;
    private float spessore;

    public OutlinedLabel(String testo, Color coloreContorno, float spessore) {
        super(testo);
        this.coloreContorno = coloreContorno;
        this.spessore = spessore;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout layout = new TextLayout(getText(), getFont(), frc);
        Shape outline = layout.getOutline(AffineTransform.getTranslateInstance(0, layout.getAscent()));

        // Contorno
        g2.setColor(coloreContorno);
        g2.setStroke(new BasicStroke(spessore * 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(outline);

        // Testo principale
        g2.setColor(getForeground());
        g2.fill(outline);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        int w = fm.stringWidth(getText()) + (int)(spessore * 2) + 4;
        int h = fm.getHeight() + (int)(spessore * 2) + 4;
        return new Dimension(w, h);
    }
}