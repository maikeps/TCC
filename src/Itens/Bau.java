/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Itens;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import tcc.GameObject;

/**
 *
 * @author maike_p_santos
 */
public class Bau extends GameObject {

    Image image;
    public Efeito efeito;
    public boolean abriu;
    public int xItem;
    public int yItem;

    public Bau(int x, int y) {
        this.x = x;
        this.y = y;

        int chance = new Random().nextInt(100);
        efeito = Efeito.CURA;
        if (chance <= 45) {//45%
            efeito = Efeito.CURA;
        } else if (chance > 45 && chance > 70) {//25%
            efeito = Efeito.ENVENENA;
        } else if(chance > 70 && chance < 100){//30%
            efeito = Efeito.POTION_VAZIA;
        }
       
        try {
            this.image = new Image("resources/itens/bau.png");
        } catch (SlickException ex) {
            Logger.getLogger(Bau.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.largura = this.image.getWidth();

        this.altura = this.image.getHeight();
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
        if (abriu) {
            this.xItem = util.Util.random(200) + this.x - 100;
            this.yItem = util.Util.random(200) + this.y - 100;
            return;
        }
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if (abriu) {
            return;
        }
        this.image.draw(this.x, this.y);
    }
}
