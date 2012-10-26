/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Options extends BasicGameState {

    public static final int ID = 4;
    StateBasedGame game;
    GameContainer gc;
    public static String[] options = {"Stats", "Full Screen", "Accept"};
    private int selected;
    boolean fullScreen = false;
    public static int idPreviousGameState;

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
//        if(this.idPreviousGameState == MainMenu.ID){
//            this.options = new String[]{"Full Screen", "Accept"};
//        } else {
//            this.options = new String[]{"Stats", "Full Screen", "Accept"};
//        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        for (int i = 0; i < this.options.length; i++) {
            g.drawString(options[i], gc.getWidth() / 2 - g.getFont().getWidth(this.options[i]) / 2, 400 + (50 * i));
            String string = "";
            if(options[i].equals("Full Screen")){
                string = " "+fullScreen;
            }else{
                string = "";
            }
            g.drawString(string, gc.getWidth() / 2 + 125, 400 + (50 * i));
            if (i == this.selected) {
                g.drawRect(gc.getWidth() / 2 - 100, 385 + (50 * i), 200, 50);
            }
        }
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_DOWN) {
            this.selected++;
            if (this.selected >= this.options.length) {
                this.selected = 0;
            }
        }
        if (key == Input.KEY_UP) {
            this.selected--;
            if (this.selected < 0) {
                this.selected = this.options.length - 1;
            }
        }

        if (key == Input.KEY_ENTER) {
            if(this.options[this.selected].equals("Stats")){
                this.game.enterState(Stats.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
            if (this.options[this.selected].equals("Full Screen")) {
                if (this.fullScreen == true) {
                    fullScreen = false;
                } else {
                    fullScreen = true;
                }
            }
            if (this.options[this.selected].equals("Accept")) {
                try {
                    gc.setFullscreen(fullScreen);
                } catch (SlickException ex) {
                    Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.game.enterState(this.idPreviousGameState, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
    }
}
