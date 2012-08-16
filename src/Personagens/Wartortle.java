package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Wartortle extends Personagem {

    public Wartortle() {

        this.atk = 63;
        this.def = 80;
        this.spd = 58;

        this.ataque = Ataques.WATER_GUN;

       /* try {
            this.spriteRight = new Imagem("resources/personagens/Wartortle/Wartortle_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Wartortle/Wartortle_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Wartortle/Wartortle_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Wartortle/Wartortle_Up.gif");
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
