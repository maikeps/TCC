/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import tcc.Animacao;
import tcc.Personagem;

/**
 *
 * @author maike_p_santos
 */
public class Stats extends BasicGameState {

    public static final int ID = 8;
    StateBasedGame game;
    public static Personagem personagem;
    Image image;
    Animacao animacao;
    
    String nome;
    int hp;
    int atk;
    int def;
    int spd;
    int lvl;

    Sound somSelect;
    
    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        this.somSelect = new Sound("resources/sounds/misc/select.wav");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
        
        
        //this.personagem = Fase1.player.personagem;
        this.animacao = this.personagem.animacaoDown;

        this.hp = this.personagem.getHpInicial();
        this.atk = this.personagem.getAtk();
        this.def = this.personagem.getDef();
        this.spd = this.personagem.getSpd();
        this.lvl = this.personagem.getLvl();
        this.nome = this.personagem.getNome();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        
        g.drawString(""+this.nome, 100, 50);
        g.drawString("LEVEL: "+this.lvl, 100, 100);
        g.drawString("HP: "+this.hp, 100, 150);
        g.drawString("ATK: "+this.atk, 100, 200);
        g.drawString("DEF: "+this.def, 100, 250);
        g.drawString("SPD: "+this.spd, 100, 300);
        
//        g.drawImage(this.image, 200, 300);
    }
    
    public void keyPressed(int key, char c){
        if(key == Input.KEY_ENTER){
            this.somSelect.play();
            this.game.enterState(Options.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }
}
