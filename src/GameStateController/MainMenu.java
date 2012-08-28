/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;
import javaPlay2.GameEngine;
import javaPlay2.GameStateController;
import javaPlay2.Imagem;
import javaPlay2.Keyboard;
import javaPlay2.Keys;
import javaPlayExtras.AudioPlayer;
import javax.swing.JOptionPane;

/**
 *
 * @author maike_p_santos
 */
public class MainMenu implements GameStateController {

    private Imagem imagem;
    int cont = 0;
    Color cor = Color.white;
    int veloc = 1;

    @Override
    public void load() {
        // AudioPlayer.play("resources/sounds/Pokemon Opening.wav");

        try {
            this.imagem = new Imagem("resources/Title.png");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
            System.exit(1);
        }

    }

    @Override
    public void step(long timeElapsed) {



        Keyboard teclado = GameEngine.getInstance().getKeyboard();

        if (teclado.keyDown(Keys.ENTER)) {
            AudioPlayer.play("resources/sounds/comeon.wav");
            GameEngine.getInstance().setNextGameStateController(2);
        }
        
        this.cont += this.veloc;
        if(this.cont >= 30){
            this.veloc = -1;
        } else if(this.cont <= 0){
            this.veloc = 1;
        }
    }

    @Override
    public void draw(Graphics g) {
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("resources/fontes/PressStart2P.ttf"));
            f = f.deriveFont(16f);
            g.setFont(f);
        } catch (FontFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.exit(1);
        }
        
        
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 700);
        this.imagem.draw(g, 195, 50);

        if(this.veloc == 1){
            this.cor = Color.white;
        }else{
            this.cor = Color.black;
        }
        
        g.setColor(this.cor); 
        g.drawString("Press ENTER", 315, 550);


    }

    @Override
    public void unload() {
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}
