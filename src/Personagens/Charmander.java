package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

public class Charmander extends Personagem {

    public Charmander() {

        this.atk = 52;
        this.def = 43;
        this.spd = 65;
        
        this.ataque = Ataques.DRAGON_RAGE;

        try {
            this.spriteRight = new Imagem("resources/personagens/Charmander/Charmander_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Charmander/Charmander_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Charmander/Charmander_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Charmander/Charmander_Up.gif");
            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
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
