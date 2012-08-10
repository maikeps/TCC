/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import java.awt.Color;
import java.awt.Graphics;
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
public class MainMenu implements GameStateController{

    private Imagem imagem;
    
    @Override
    public void load() {
       // AudioPlayer.play("resources/sounds/Pokemon Opening.wav");
        
        try {
            this.imagem = new Imagem("resources/Title.png");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: "+ex.getMessage());
            System.exit(1);
        }
        
    }

    @Override
    public void step(long timeElapsed) { 
        
        
        
        Keyboard teclado = GameEngine.getInstance().getKeyboard();

        if(teclado.keyDown(Keys.ESPACO)) {
              AudioPlayer.play( "resources/sounds/comeon.wav" );
              GameEngine.getInstance().setNextGameStateController(2);  
        }
    }
        
    

    @Override
    public void draw(Graphics g) {  
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 700);
        this.imagem.draw(g, 195, 50);
        
        
        
    }
    
    @Override
    public void unload() {    }

    @Override
    public void start() {    }

    @Override
    public void stop() {    }
    
}
