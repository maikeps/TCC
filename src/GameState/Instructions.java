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
 * @author Maike
 */
public class Instructions extends BasicGameState{
    
    public static final int ID = 3;
    StateBasedGame game;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        
        g.drawString("Arrow keys to move the Pokemon", 100, 200);
        g.drawString("Left mouse button to attack", 100, 250);
        g.drawString("Kill the enemies to unlock them", 100, 300);
        g.drawString("Try to catch'em all!", 100, 350);
        
        g.drawString("Press enter to return", gc.getWidth()/2-g.getFont().getWidth("Press enter to return")/2, 550);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
        
    }
    
    public void keyPressed(int key, char c){
        if(key == Input.KEY_ENTER){
            this.game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }
}
