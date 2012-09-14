package Personagens;

import java.awt.Graphics;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import pixelPerfect.GameObjectImagePixelPerfect;
import tcc.Ataques;

public class Charmeleon extends Personagem {

    public Charmeleon() {

        this.atk = 64;
        this.def = 58;
        this.spd = 80;
        
        this.ataque = Ataques.FLAME_THROWER;

         //Modo Normal de colisão
          try {
            this.spriteRight = new Imagem("resources/personagens/Charmeleon/Charmeleon_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Charmeleon/Charmeleon_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Charmeleon/Charmeleon_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Charmeleon/Charmeleon_Up.gif");
            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
            System.exit(1);
        }
        
       /* try {
            this.spriteRight = new GameObjectImagePixelPerfect("resources/personagens/Charmeleon/Charmeleon_Right.gif");
            this.spriteLeft = new GameObjectImagePixelPerfect("resources/personagens/Charmeleon/Charmeleon_Left.gif");
            this.spriteDown = new GameObjectImagePixelPerfect("resources/personagens/Charmeleon/Charmeleon_Down.gif");
            this.spriteUp = new GameObjectImagePixelPerfect("resources/personagens/Charmeleon/Charmeleon_Up.gif");
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
