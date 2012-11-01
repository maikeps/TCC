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
    
    String[] options = {"Return to Game", "Options", "Return to Main Menu", "Exit"};
    int selected;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        this.gc = gc;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("GAME PAUSED", gc.getWidth()/2-g.getFont().getWidth("GAME PAUSED")/2, 200);
        
        for(int i = 0; i < this.options.length; i++){
            g.drawString(this.options[i], gc.getWidth()/2-g.getFont().getWidth(this.options[i])/2, 300+(50*i));
            if(this.selected == i){
                g.drawRect(gc.getWidth()/2 - 100, 285+(50*i), 200, 50);
            }
        }
    }
    
    public void keyPressed(int key, char c){
        if(key == Input.KEY_P){
            this.game.enterState(Fase1.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        
        if(key == Input.KEY_DOWN){
            this.selected ++;
            if(this.selected >= this.options.length){
                this.selected = 0;
            }
        }
        if(key == Input.KEY_UP){
            this.selected --;
            if(this.selected < 0){
                this.selected = this.options.length - 1;
            }
        }
        
        if(key == Input.KEY_ENTER){
            if(this.options[this.selected].equals("Return to Game")){
                this.game.enterState(Fase1.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Options")){
                Options.idPreviousGameState = this.getID();
                Options.options = new String[]{"Stats", "Pokedex", "Full Screen", "Accept"};
                this.game.enterState(Options.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Return to Main Menu")){
                this.game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Exit")){
                this.gc.exit();
            }
        }
    }
    
}
