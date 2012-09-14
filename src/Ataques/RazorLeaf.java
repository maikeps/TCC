/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import DAO.AtaqueDAO;
import Personagens.Personagem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.Sprite;
import javax.swing.JOptionPane;

/**
 *
 * @author Maike
 */
public class RazorLeaf extends Ataque {

    int frameElapsed;
    int frame;

    public RazorLeaf(int x, int y, int destX, int destY, double angulo, Personagem personagem) {
        
        this.setContador(0);
        String name = this.toString();
        if (name.lastIndexOf('.') > 0) {
            name = name.substring(name.lastIndexOf('.') + 1, name.indexOf('@'));
        }
        model.Ataque a = AtaqueDAO.getAtaque(name);
        this.setDanoBruto(a.getAtk());
        
        this.personagem = personagem;

        this.desativado = false;
        this.x = x;
        this.y = y;

        this.angulo = angulo;

        this.frame = 0;

        try {
            this.sprite = new Sprite("resources/ataques/"+name+"/"+name+".png", 9, 220, 85);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }

      //  this.ajustaAtaque();

    }

    public void step(long timeElapsed) {
        if (this.frame >= 9) {
            return;
        }

        this.frameElapsed++;

        if (this.frameElapsed > 5) {
            this.frame++;
            this.sprite.setCurrAnimFrame(this.frame);
            this.frameElapsed -= 5;

        }
        
        if (this.desativado) {
            this.contadorDano++;
        }



    }

    @Override
    public void draw(Graphics g) {
        this.sprite.drawRotated(g, this.x, this.y, this.angulo);
    }

    @Override
    public Rectangle getRetangulo() {
            return new Rectangle(this.x, this.y, 220, 85);
    }

    @Override
    public boolean temColisao(Rectangle retangulo) {
        if (this.desativado || this.frame == 9) {
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
