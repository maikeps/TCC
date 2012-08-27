package javaPlay2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class Imagem {

    protected  Image image;
    private static int MAX_COUNT = 100;
    protected String filename;

    public Imagem(String filename) throws Exception
    {
        this.filename = filename;
        image = Toolkit.getDefaultToolkit().getImage(filename);

        int count = 0;

        while(image.getWidth(null) == -1) {
            Thread.sleep(1);
            count++;

            if(count == MAX_COUNT) {
                JOptionPane.showMessageDialog(null, "Imagem \""+filename+"\" não pode ser carregada");
                System.exit(1);
                //throw new Exception("Imagem \""+filename+"\" nï¿½o pode ser carregada");
            }
        }        
    }

    // Gets the Width of this sprite
    public int pegaLargura() {
        return image.getWidth(null);
    }

    // Gets the Height of this sprite
    public int pegaAltura() {
        return image.getHeight(null);
    }

    // Allows easy drawing of this sprite at any position
    public void draw(Graphics graphics, int x, int y) {
        graphics.drawImage(image, x, y, null);
    }
    
    //arrumar, mas acho que com o x-w/2, y-h/2 deveria ficar mais certo
    public void drawTranslated(Graphics g, int x, int y){
        
        int w = this.pegaLargura();
        int h = this.pegaAltura();
        //g.translate(x-w/2, y-h/2);
        g.translate(w/2, h/2);
        g.drawImage(image, x, y, null);
        
        g.translate(-w/2, -h/2);
    }

    public void drawFlipped(Graphics graphics, int x, int y) {
        graphics.drawImage(image, image.getWidth(null) + x, y, x,
                image.getHeight(null) + y, 0, 0, image.getWidth(null),
                image.getHeight(null), null);
    }

    // Rotaciona a imagem em um determinado angulo e a desenha
    public void drawRotated(Graphics graphics, int x, int y, double angle) {
        int w = this.pegaLargura();
        int h = this.pegaAltura();

        Graphics2D g2d = (Graphics2D)graphics;
        
        AffineTransform tx = new AffineTransform();
        
        tx.translate(x, y);
        tx.rotate(Math.toRadians(-angle));        

        g2d.drawImage(image, tx, null);
    }
    
    public void drawZoomed(Graphics graphics, int x, int y, int zoom){
        
        Graphics2D g2d = (Graphics2D)graphics;
        
        AffineTransform tx = new AffineTransform();
        
        tx.translate(x, y);
        tx.scale(zoom, zoom);
        
        g2d.drawImage(image, tx, null);
        
        
    }
}
