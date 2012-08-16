/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import DAO.AtaqueDAO;
import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.Sprite;
import javax.swing.JOptionPane;

/**
 *
 * @author Maike
 */
public class HydroPump extends Ataque {

    int frameElapsed;
    int frame;
    Sprite sprite;

    public HydroPump(int x, int y, int destX, int destY, double angulo, Personagem personagem) {
        
        String name = this.toString();
        if (name.lastIndexOf('.') > 0) {
            name = name.substring(name.lastIndexOf('.') + 1, name.indexOf('@'));
        }
        model.Ataque a = AtaqueDAO.getAtaque(name);
        this.setDano(a.getAtk());
        
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
            this.sprite = new Sprite("resources/ataques/HydroPump/HydroPump_Right.png", 14, 220, 120);
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

        this.frameElapsed++;

        if (this.frameElapsed > 5) {
            this.frame++;
            this.sprite.setCurrAnimFrame(this.frame);
            this.frameElapsed -= 5;

        }
    }

    @Override
    public void draw(Graphics g) {
       // this.imagem.drawRotated(g, this.x, this.y, this.angulo);
        this.sprite.drawRotated(g, this.x, this.y, this.angulo);
        
       //this.spriteAtual.draw(g, this.x, this.y);
    }

    @Override
    public Rectangle getRetangulo() {
        Rectangle r = new Rectangle(this.x, this.y, 215, 65);
        
        
        
        return r;
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
