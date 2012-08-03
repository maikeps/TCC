/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc;

import GameStateController.*;
import javaPlay2.GameEngine;


/**
 *
 * @author maike_p_santos
 */
public class TCC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String p1 = "ABC";
        CharacterSelect CharSelect = new CharacterSelect(p1);
        
        GameEngine.getInstance().addGameStateController(1, new MainMenu());
        GameEngine.getInstance().addGameStateController(2, CharSelect);
        GameEngine.getInstance().addGameStateController(3, new Fase1(CharSelect));
        
        GameEngine.getInstance().setStartingGameStateController(1);
        
        GameEngine.getInstance().setFramesPerSecond(60);
        GameEngine.getInstance().run();
        
    }
}
