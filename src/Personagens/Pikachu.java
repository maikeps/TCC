package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Pikachu extends Personagem {

    public Pikachu() {

        this.atk = 55;
        this.def = 30;
        this.spd = 90;

        this.ataque = Ataques.ELECTRO_BALL;

     /*   try {
            this.spriteRight = new Imagem("resources/personagens/Pikachu/Pikachu_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Pikachu/Pikachu_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Pikachu/Pikachu_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Pikachu/Pikachu_Up.gif");
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
