package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import pixelPerfect.GameObjectImagePixelPerfect;
import tcc.Ataques;

public class Bulbasaur extends Personagem {

    public Bulbasaur() {

        this.atk = 49;
        this.def = 49;
        this.spd = 45;

        this.ataque = Ataques.VINE_WHIP;

        /*try {
            this.spriteRight = new Imagem("resources/personagens/Bulbasaur/Bulbasaur_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Bulbasaur/Bulbasaur_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Bulbasaur/Bulbasaur_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Bulbasaur/Bulbasaur_Up.gif");
            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
            System.exit(1);
        }*/

         try {
            this.spriteRight = new GameObjectImagePixelPerfect("resources/personagens/Bulbasaur/Bulbasaur_Right.gif");
            this.spriteLeft = new GameObjectImagePixelPerfect("resources/personagens/Bulbasaur/Bulbasaur_Left.gif");
            this.spriteDown = new GameObjectImagePixelPerfect("resources/personagens/Bulbasaur/Bulbasaur_Down.gif");
            this.spriteUp = new GameObjectImagePixelPerfect("resources/personagens/Bulbasaur/Bulbasaur_Up.gif");
            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
            System.exit(1);
        }
    }

    public void step(long timeElapsed) {
        super.step(timeElapsed);
    }

    public void draw(Graphics g) {
        super.draw(g);
    }
}
