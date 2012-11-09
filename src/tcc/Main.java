package tcc;


import GameState.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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
        Image cursor = new Image("resources/Lock-On.png");
        this.getContainer().setMouseCursor(cursor, cursor.getWidth()/2, cursor.getHeight()/2);
                
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
        this.addState(new PokedexIndividual());
        this.addState(new VideoOptions());
        this.addState(new AudioOptions());
        this.addState(new GameOver());
    }
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main("PokeProject"));
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(60);
        app.setIcon("resources/pokeProjectIcon.png");
       
        app.start();
    }
    
    public Main(String title){
        super(title);
    }


    
}
