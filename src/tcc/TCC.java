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
        GameEngine.getInstance().addGameStateController(1, new Fase1());
        
        GameEngine.getInstance().setStartingGameStateController(1);
        
        GameEngine.getInstance().setFramesPerSecond(60);
        GameEngine.getInstance().run();
        
    }
}
