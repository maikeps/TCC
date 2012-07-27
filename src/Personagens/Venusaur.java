package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Venusaur extends Personagem {

    public Venusaur() {

        this.atk = 82;
        this.def = 83;
        this.spd = 80;
        
        this.ataque = Ataques.RAZOR_LEAF;

        try {
            this.spriteRight = new Imagem("resources/personagens/Venusaur/Venusaur_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Venusaur/Venusaur_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Venusaur/Venusaur_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Venusaur/Venusaur_Up.gif");
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
