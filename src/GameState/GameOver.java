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

/**
 *
 * @author Maike
 */
public class GameOver extends BasicGameState {

    public static final int ID = 13;
    StateBasedGame game;
    GameContainer gc;
    String[] options = {"Retry", "Character Select", "Main Menu", "Exit"};
    private int selected;
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
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        
        g.drawString("GAME OVER", gc.getWidth()/2-g.getFont().getWidth("GAME OVER")/2, 200);
        
        for(int i = 0; i < this.options.length; i++){
            g.drawString(options[i], gc.getWidth()/2-g.getFont().getWidth(this.options[i])/2, 400+(50*i));
            if(i == this.selected){
                g.drawRect(gc.getWidth()/2-100, 385+(50*i), 200, 50);
            }
        }
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_DOWN) {
            this.somMove.play();
            this.selected++;
            if(this.selected >= this.options.length){
                this.selected = 0;
            }
        }
        if (key == Input.KEY_UP) {
            this.somMove.play();
            this.selected--;
            if(this.selected < 0){
                this.selected = this.options.length - 1;
            }
        }
        
        if(key == Input.KEY_ENTER){
            this.somSelect.play();
            if(this.options[this.selected].equals("Retry")){
                //recria toda a fase1
                this.game.enterState(Fase1.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Character Select")){
                //recria todo o character select E a fase1
                this.game.enterState(CharacterSelect.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Main Menu")){
                //recria todo o main menu, o character select E a fase1
                this.game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if(this.options[this.selected].equals("Exit")){
                this.gc.exit();
            }
        }
    }
}
