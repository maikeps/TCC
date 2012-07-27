package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;

//TESTAR SE FUNCIONA O TRY-CATCH NA CLASSE PERSONAGEM AO INVES DE AQUI

public class Blastoise extends Personagem {

    public Blastoise() {
        this.setNome("Blastoise");

        this.atk = 83;
        this.def = 100;
        this.spd = 78;
        
        this.ataque = Ataques.WATER_PULSE;
        
//        try {
//            this.spriteRight = new Imagem("resources/personagens/Blastoise/Blastoise_Right.gif");
//            this.spriteLeft = new Imagem("resources/personagens/Blastoise/Blastoise_Left.gif");
//            this.spriteDown = new Imagem("resources/personagens/Blastoise/Blastoise_Down.gif");
//            this.spriteUp = new Imagem("resources/personagens/Blastoise/Blastoise_Up.gif");
//            this.spriteAtual = this.spriteDown;
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
//            System.exit(1);
//        }

    }

    public void step(long timeElapsed) {
        super.step(timeElapsed);
    }

    public void draw(Graphics g) {
        super.draw(g);
    }
}
