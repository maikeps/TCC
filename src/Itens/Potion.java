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
public class Potion extends Item {

    public Potion(int x, int y) {
        this.x = x;
        this.y = y;
        
        this.raridade = 25;

        try {
            this.image = new Image("resources/personagens/1 - Bulbasaur/Bulbasaur_Down.gif");
        } catch (SlickException ex) {
            Logger.getLogger(Potion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        this.image.draw(this.x, this.y);
    }
}
