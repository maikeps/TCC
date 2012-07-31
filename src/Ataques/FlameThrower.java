/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.Imagem;
import javaPlay2.Sprite;
import javax.swing.JOptionPane;

/**
 *
 * @author Maike
 */
public class FlameThrower extends Ataque {

    int frameElapsed;
    int frame;
    Sprite spriteLeft;
    Sprite spriteRight;
    Sprite spriteUp;
    Sprite spriteDown;
    Sprite spriteAtual;
    Sprite vazio;
    double angulo;
    int destX;
    int destY;
    double deltaX, deltaY, dx, dy;

    public FlameThrower(int x, int y, int destX, int destY, double angulo, Personagem personagem) {
        this.setDano(15);
        this.personagem = personagem;

        this.desativado = false;
        this.xInicial = x;
        this.yInicial = y;
        this.x = x;
        this.y = y;
        this.destX = destX;
        this.destY = destY;
        this.velocidade = 10;

        this.angulo = angulo;

        int frame = 0;

        try {
            //    this.spriteLeft = new Sprite("resources/ataques/Flame Thrower/FlameThrower_Left.png", 8, 215, 65);
            this.imagem = new Imagem("resources/ataques/Flame Thrower/FlameThrower_Right.gif");
//            this.spriteRight = new Sprite("resources/ataques/Flame Thrower/FlameThrower_Right.png", 8, 215, 65);
            //  this.spriteUp = new Sprite("resources/ataques/Flame Thrower/FlameThrower_Up.png", 8, 65, 215);
            //this.spriteDown = new Sprite("resources/ataques/Flame Thrower/FlameThrower_Down.png", 8, 65, 215);
            // this.vazio = new Sprite("resources/ataques/vazio.png", 1, 10, 10);
            this.spriteAtual = this.spriteRight;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }

//        this.ajustaAtaque();

    }

    public void step(long timeElapsed) {
        if (this.frame >= 8) {
            return;
        }

        this.spriteAtual = this.spriteRight;

        this.frameElapsed++;
//
//        switch (this.direcao) {
//            case DIREITA:
//                this.spriteAtual = this.spriteRight;
//                break;
//            case ESQUERDA:
//                this.spriteAtual = this.spriteLeft;
//                break;
//            case CIMA:
//                this.spriteAtual = this.spriteUp;
//                break;
//            case BAIXO:
//                this.spriteAtual = this.spriteDown;
//                break;
//        }

////////        if (this.frameElapsed > 5) {
////////            this.frame++;
////////            this.spriteAtual.setCurrAnimFrame(this.frame);
////////            this.frameElapsed -= 5;
////////
////////        }



    }

    @Override
    public void draw(Graphics g) {
        this.imagem.drawRotated(g, this.x, this.y, this.angulo);
////////        this.spriteAtual.drawRotated(g, this.x, this.y, this.angulo);
////////        this.spriteAtual.draw(g, this.x, this.y);
    }

    @Override
    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, 215, 65);
    }

    @Override
    public boolean temColisao(Rectangle retangulo) {
        if (this.desativado || this.frame == 8) {
            return false;
        }

        if (this.getRetangulo().intersects(retangulo)) {
            this.desativado = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean estaAtivo() {
        return (this.desativado == false);
    }

    public int getFrames() {
        return this.frameElapsed;
    }

    public void ajustaAtaque() {
        switch (this.direcao) {
            case DIREITA:
                this.x = this.x + this.personagem.spriteAtual.pegaLargura() - 5;
                this.y = this.y - 30;
                break;
            case ESQUERDA:
                this.x = this.x - 225;
                this.y = this.y - 30;
                break;
            case CIMA:
                this.x += this.personagem.spriteAtual.pegaLargura() - 80;
                this.y -= 240;
                break;
            case BAIXO:
                this.x += this.personagem.spriteAtual.pegaLargura() - 80;
                this.y += this.personagem.spriteAtual.pegaAltura() - 50;
                break;
            case DIREITA_CIMA:
                this.x = this.x + this.personagem.spriteAtual.pegaLargura();
                break;
            case DIREITA_BAIXO:
                this.x = this.x + this.personagem.spriteAtual.pegaLargura();
                this.y = this.y + this.personagem.spriteAtual.pegaAltura();
                break;
            case ESQUERDA_CIMA:
                break;
            case ESQUERDA_BAIXO:
                this.y = this.y + this.personagem.spriteAtual.pegaAltura();
                break;
        }
    }
}
