/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author maike_p_santos
 */
public class PauseScreen extends BasicGameState{
    
    public static final int ID = 7;
    StateBasedGame game;
    GameContainer gc;
    
    String[] options = {"Retornar ao Jogo","Pokedex", "Opções", "Retornar ao Menu", "Sair"};
    int selected;
    Sound somSelect;
    Sound somMove;
    
    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        this.gc = gc;
        this.somSelect = new Sound("resources/sounds/misc/select.wav");
        this.somMove = new Sound("resources/sounds/misc/move.wav");
        System.out.println("PauseScreen loaded.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        
        g.drawString("JOGO PAUSADO", gc.getWidth()/2-g.getFont().getWidth("JOGO PAUSADO")/2, 200);
        
        for(int i = 0; i < this.options.length; i++){
            g.drawString(this.options[i], gc.getWidth()/2-g.getFont().getWidth(this.options[i])/2, 300+(50*i));
            if(this.selected == i){
                g.drawRect(gc.getWidth()/2 - 100, 285+(50*i), 200, 50);
            }
        }
    }
    
    public void keyPressed(int key, char c){
        if(key == Input.KEY_P){
            this.somSelect.play();
            this.game.enterState(Fase1.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        
        if(key == Input.KEY_DOWN){
            this.somMove.play();
            this.selected ++;
            if(this.selected >= this.options.length){
                this.selected = 0;
            }
        }
        if(key == Input.KEY_UP){
            this.somMove.play();
            this.selected --;
            if(this.selected < 0){
                this.selected = this.options.length - 1;
            }
        }
        
        if(key == Input.KEY_ENTER){
            this.somMove.play();
            if(this.options[this.selected].equals("Retornar ao Jogo")){
                this.game.enterState(Fase1.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Pokedex")){
                this.game.enterState(Pokedex.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Opções")){
                Options.idPreviousGameState = this.getID();
                Options.options = new String[]{"Audio", "Video", "Aceitar"};
                this.game.enterState(Options.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Retornar ao Menu")){
                this.game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Sair")){
                this.gc.exit();
            }
        }
    }
    
}
