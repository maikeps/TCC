package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Pidgeot extends Personagem {

    public Pidgeot() {

        this.atk = 80;
        this.def = 75;
        this.spd = 91;

        this.ataque = Ataques.TWISTER;

        try {
            this.spriteRight = new Imagem("resources/personagens/Pidgeot/Pidgeot_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Pidgeot/Pidgeot_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Pidgeot/Pidgeot_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Pidgeot/Pidgeot_Up.gif");
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
