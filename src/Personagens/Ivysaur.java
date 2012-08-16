package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Ivysaur extends Personagem {

    public Ivysaur() {

        this.atk = 62;
        this.def = 63;
        this.spd = 60;
        
        this.ataque = Ataques.LEECH_LIFE;

       /* try {
            this.spriteRight = new Imagem("resources/personagens/Ivysaur/Ivysaur_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Ivysaur/Ivysaur_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Ivysaur/Ivysaur_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Ivysaur/Ivysaur_Up.gif");
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
