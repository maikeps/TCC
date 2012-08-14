package Ataques;

import Personagens.Personagem;
import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.GameObject;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;

public class LeechLife extends Ataque {

    Imagem imagem;
    Imagem imagemAtual;

    public LeechLife(int x, int y, Personagem personagem) {
        this.setDano(4);
        this.velocidade = 5;
        this.desativado = false;
         this.personagem = personagem;

        this.x = x;
        this.y = y;
        try {
            this.imagem = new Imagem("resources/ataques/Leech Life/LeechLife.gif");
            this.imagemAtual = this.imagem;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Imagem não encontrada: " + ex.getMessage());
            System.exit(1);
        }
        
        this.ajustaAtaque();
    }

    public void step(long timeElapsed) {
    }

    @Override
    public void draw(Graphics g) {
        if (this.desativado) {
            return;
        }

        this.imagemAtual.draw(g, this.x, this.y);


    }

    public void persegue(GameObject objeto) {
        if (this.desativado) {
            return;
        }
        int xPerseguido = objeto.getX() + 30;
        int yPerseguido = objeto.getY() + 30;


        if (this.x < xPerseguido) {
            this.x += this.velocidade;
        }
        if (this.x > xPerseguido) {
            this.x -= this.velocidade;
        }
        if (this.y < yPerseguido) {
            this.y += this.velocidade;
        }
        if (this.y > yPerseguido) {
            this.y -= this.velocidade;
        }


    }

    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, this.imagemAtual.pegaLargura(), this.imagemAtual.pegaAltura());
    }

    public boolean temColisao(Rectangle retangulo) {
        if (this.desativado) {
            return false;
        }

        if (this.getRetangulo().intersects(retangulo)) {
            this.desativado = true;
            return true;
        } else {
            return false;
        }
    }
    
    public void ajustaAtaque() {
        switch(this.direcao){
            case DIREITA:
                this.x += this.personagem.spriteAtual.pegaLargura() - 5;
                this.y += this.personagem.spriteAtual.pegaAltura() / 3 - 20;
                break;
            case ESQUERDA:
                this.x -= this.personagem.spriteAtual.pegaLargura() + 25;
                this.y += this.personagem.spriteAtual.pegaAltura() / 2 - 20;
                break;
            case CIMA:
                this.x += this.personagem.spriteAtual.pegaLargura() / 2 - 22;
                this.y -= this.imagemAtual.pegaAltura();
                break;
            case BAIXO:
                this.x += this.personagem.spriteAtual.pegaLargura() / 2 - 20;
                this.y += this.personagem.spriteAtual.pegaAltura() / 3;
                break;
            case DIREITA_CIMA:
                this.x = this.x + this.personagem.spriteAtual.pegaLargura();
                this.y = this.y + this.personagem.spriteAtual.pegaAltura();
                break;
                
            case DIREITA_BAIXO:
                this.x = this.x + this.personagem.spriteAtual.pegaLargura();
                break;
            case ESQUERDA_CIMA:
                break;
            case ESQUERDA_BAIXO:
                this.y = this.y + this.personagem.spriteAtual.pegaAltura();
                break;
        }
    }

    
}
