package tcc;


import GameState.CharacterSelect;
import GameState.Fase1;
import GameState.Instructions;
import GameState.MainMenu;
import GameState.Options;
import GameState.PauseScreen;
import GameState.Pokedex;
import GameState.StartMenu;
import GameState.Stats;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maike
 */
public class Main extends StateBasedGame{
    
    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        CharacterSelect cs = new CharacterSelect("Bulbasaur");
        
        this.addState(new StartMenu());
        this.addState(new MainMenu());
        this.addState(new Instructions());
        this.addState(new Options());
        this.addState(cs);
        this.addState(new Fase1(cs));
        this.addState(new PauseScreen());
        this.addState(new Stats());
        this.addState(new Pokedex());
    }
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main("PokeProject"));
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(60);
        app.start();
    }
    
    public Main(String title){
        super(title);
    }


    
}
