/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import Ataques.Ataque;
import Ataques.DragonRage;
import Personagens.Charmander;
import java.awt.Graphics;
import java.util.ArrayList;
import javaPlay2.GameStateController;
import tcc.Inimigo;
import tcc.Player;

/**
 *
 * @author maike_p_santos
 */
public class Fase1 implements GameStateController{
    
    Player player;
    Inimigo inimigo;
    ArrayList<Ataque> ataques;
    
    Charmander charmander;
    Charmander charmander2;
    
    public void load(){
        this.charmander = new Charmander();
        this.charmander2 = new Charmander();
        this.player = new Player(this.charmander);
        this.inimigo = new Inimigo(this.charmander2, this.player);
        this.ataques = new ArrayList<Ataque>();
    }
    
    public void step(long timeElapsed){
        this.player.step(timeElapsed);
        this.inimigo.step(timeElapsed);
        
        for(Ataque a : this.ataques){
            a.step(timeElapsed);
        }
        
        this.lancaAtaques();
    }
    
    public void draw(Graphics g){
        g.fillRect(0, 0, 1000, 1000);
        
        this.player.draw(g);
        this.inimigo.draw(g);
        
        for(Ataque a : this.ataques){
            a.draw(g);
        }
    }
    
    public void start(){}
    public void stop(){}
    public void unload() {}
    
    
    
    
    public void lancaAtaques(){
        if(this.player.atacou == true){
            this.ataques.add(new DragonRage(this.player.getX(), this.player.getY(), this.player.getDestX(), this.player.getDestY(), this.player.getAngulo(), this.charmander));
        }
        this.player.atacou = false;
    
        if(this.inimigo.atacou == true){
            this.ataques.add(new DragonRage(this.inimigo.getX(), this.inimigo.getY(), this.player.getX(), this.player.getY(), this.inimigo.getAngulo(), this.charmander2));
        }
    }
    

    
    
    
    
    
    
    
    
    
}
