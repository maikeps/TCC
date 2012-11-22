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
public class Instructions extends BasicGameState{
    
    public static final int ID = 3;
    StateBasedGame game;
    Sound som;
    Image setas;
    Image mouse;
    
    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        this.som = new Sound("resources/sounds/misc/select.wav");
        this.setas = new Image("resources/Setas.png");
        this.mouse = new Image("resources/Mouse.png");
        System.out.println("Instructions loaded.");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        
        g.drawString("Use os Botoes W,A,S,D para movimentar o Pokémon", 100, 50);
        this.setas.draw(300, 100);
        
        g.drawString("Utilize o botão esquerdo do mouse para poder atacar", 100, 250);
        this.mouse.draw(300,300);
        
        g.drawString("Mate todos os inimigos para poder liberá-los", 100, 400);
        g.drawString("Tente Capturar Todos !!", 100, 450);
        
        g.drawString("Pressione o botão 'ENTER' para voltar", gc.getWidth()/2-g.getFont().getWidth("Pressione o botão 'ENTER' para voltar")/2, 550);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
        
    }
    
    public void keyPressed(int key, char c){
        if(key == Input.KEY_ENTER){
            this.som.play();
            this.game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }
}
