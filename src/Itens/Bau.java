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
public class Bau extends GameObject{

    Image image;
    
    public Efeito efeito;
    public boolean abriu;
    public int xItem;
    public int yItem;
    
    public Bau(int x, int y){
        this.x = x;
        this.y = y;
        
        //25% de chance de vir veneno
        boolean val = new Random().nextInt(100) <= 25;
        if (val) {
            efeito = Efeito.ENVENENA;
        }else{
        //50% de chance de vir potion
        //if (val) {
            efeito = Efeito.CURA;
        }
//        //35% de chance de nao vir nada
//        val = new Random().nextInt(100) <= 35;
//        if (val) {
//            efeito = null;
//        }
        
        
        try {
            this.image = new Image("resources/itens/bau.png");
        } catch (SlickException ex) {
            Logger.getLogger(Bau.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.largura = this.image.getWidth();
        this.altura = this.image.getHeight();
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
        if(abriu){
            this.xItem = util.Util.random(200)+this.x - 100;
            this.yItem = util.Util.random(200)+this.y - 100;
            return;
        }
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if(abriu){
            return;
        }
        this.image.draw(this.x, this.y);
    }
}
