package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Pidgeotto extends Personagem {

    public Pidgeotto() {

        this.atk = 60;
        this.def = 55;
        this.spd = 71;

        this.ataque = Ataques.WING_ATTACK;

       /* try {
            this.spriteRight = new Imagem("resources/personagens/Pidgeotto/Pidgeotto_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Pidgeotto/Pidgeotto_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Pidgeotto/Pidgeotto_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Pidgeotto/Pidgeotto_Up.gif");
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
