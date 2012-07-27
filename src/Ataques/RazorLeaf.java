/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.GameObject;
import javaPlay2.Sprite;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import pokemonbrawlreloaded.Direcao;
import pokemonbrawlreloaded.ObjetoComMovimento;

/**
 *
 * @author Maike
 */
public class RazorLeaf extends Ataque {

    int frameElapsed;
    int frame;
    Direcao direcao;
    Sprite spriteLeft;
    Sprite spriteRight;
    Sprite spriteUp;
    Sprite spriteDown;
    Sprite spriteAtual;
    Sprite vazio;

    public RazorLeaf(int x, int y, Direcao direcao, Personagem personagem) {
        this.setDano(10);
        this.personagem = personagem;
        this.direcao = direcao;

        this.desativado = false;
        this.x = x;
        this.y = y;

        int frame = 0;

        try {
            this.spriteLeft = new Sprite("resources/ataques/Razor Leaf/RazorLeaf_Left.png", 9, 220, 85);
            this.spriteRight = new Sprite("resources/ataques/Razor Leaf/RazorLeaf_Right.png", 9, 220, 85);
            this.spriteUp = new Sprite("resources/ataques/Razor Leaf/RazorLeaf_Up.png", 9, 85, 220);
            this.spriteDown = new Sprite("resources/ataques/Razor Leaf/RazorLeaf_Down.png", 9, 85, 220);
            this.vazio = new Sprite("resources/ataques/vazio.png", 1, 10, 10);
            this.spriteAtual = this.vazio;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }

        this.ajustaAtaque();

    }

    public void step(long timeElapsed) {
        if (this.frame >= 9) {
            return;
        }

        this.frameElapsed++;

        switch (this.direcao) {
            case DIREITA:
                this.spriteAtual = this.spriteRight;
                break;
            case ESQUERDA:
                this.spriteAtual = this.spriteLeft;
                break;
            case CIMA:
                this.spriteAtual = this.spriteUp;
                break;
            case BAIXO:
                this.spriteAtual = this.spriteDown;
                break;
        }

        if (this.frameElapsed > 5) {
            this.frame++;
            this.spriteAtual.setCurrAnimFrame(this.frame);
            this.frameElapsed -= 5;

        }



    }

    @Override
    public void draw(Graphics g) {
        this.spriteAtual.draw(g, this.x, this.y);
    }

    @Override
    public Rectangle getRetangulo() {
        if(this.direcao == Direcao.DIREITA || this.direcao == Direcao.ESQUERDA){
            return new Rectangle(this.x, this.y, 220, 85);
        } else {
            return new Rectangle(this.x, this.y, 85, 220);
        }
        
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
