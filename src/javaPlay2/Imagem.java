package javaPlay2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Imagem {

    protected  Image image;
    private static int MAX_COUNT = 50;
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
                throw new Exception("Imagem \""+filename+"\" nï¿½o pode ser carregada");
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
