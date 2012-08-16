package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Caterpie extends Personagem {

    public Caterpie() {

        this.atk = 30;
        this.def = 35;
        this.spd = 45;
        
       /* try {
            this.spriteRight = new Imagem("resources/personagens/Caterpie/Caterpie_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Caterpie/Caterpie_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Caterpie/Caterpie_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Caterpie/Caterpie_Up.gif");
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
