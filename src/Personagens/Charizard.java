package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Charizard extends Personagem {

    public Charizard() {

        this.atk = 84;
        this.def = 78;
        this.spd = 100;

        this.ataque = Ataques.FLAME_BURST;

       /* try {
            this.spriteRight = new Imagem("resources/personagens/Charizard/Charizard_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Charizard/Charizard_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Charizard/Charizard_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Charizard/Charizard_Up.gif");
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
