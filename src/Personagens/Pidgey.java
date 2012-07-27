package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Pidgey extends Personagem {

    public Pidgey() {

        this.atk = 45;
        this.def = 40;
        this.spd = 56;
        
        this.ataque = Ataques.WING_ATTACK;

        try {
            this.spriteRight = new Imagem("resources/personagens/Pidgey/Pidgey_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Pidgey/Pidgey_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Pidgey/Pidgey_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Pidgey/Pidgey_Up.gif");
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
