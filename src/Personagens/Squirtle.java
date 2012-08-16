package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Squirtle extends Personagem {

    public Squirtle() {

        this.atk = 48;
        this.def = 65;
        this.spd = 43;

        this.ataque = Ataques.BUBBLES;

    /*    try {
            this.spriteRight = new Imagem("resources/personagens/Squirtle/Squirtle_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Squirtle/Squirtle_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Squirtle/Squirtle_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Squirtle/Squirtle_Up.gif");
            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
            System.exit(1);
        }*/

    }

    public void step(long timeElapsed) {
        super.step(timeElapsed);
    }

    public void draw(Graphics g) {
        super.draw(g);
    }
}
