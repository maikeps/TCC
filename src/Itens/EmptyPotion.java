/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Itens;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author lucas_macedo
 */
public class EmptyPotion extends Item {

    public EmptyPotion(int x, int y) {
        this.x = x;
        this.y = y;
        
        this.raridade = 25;
        this.forca = 0;
        
        this.efeito = Efeito.POTION_VAZIA;

        try {
            this.image = new Image("resources/itens/emptyPotion.png");
        } catch (SlickException ex) {
            Logger.getLogger(Potion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setLargura(this.image.getWidth());
        this.setAltura(this.image.getHeight());
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
        if(this.pegou){
            this.contador ++;
        }
        if(this.contador >= 25){
            //return;
        }
        this.tempoDesdeCriacao ++;
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if(this.pegou){
            return;
        }
        this.image.draw(this.x, this.y);
    }
}
