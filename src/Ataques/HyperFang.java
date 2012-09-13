package Ataques;

import DAO.AtaqueDAO;
import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javaPlay2.Imagem;
import javaPlayExtras.AudioPlayer;
import javax.swing.JOptionPane;
import pixelPerfect.GameObjectImagePixelPerfect;

public class HyperFang extends Ataque {

    public HyperFang(int x, int y, int destX, int destY, double angulo, Personagem personagem) {

        String name = this.toString();
        if (name.lastIndexOf('.') > 0) {
            name = name.substring(name.lastIndexOf('.') + 1, name.indexOf('@'));
        }
        model.Ataque a = AtaqueDAO.getAtaque(name);
        this.setDano(a.getAtk());
        
        this.desativado = false;
        this.xInicial = x;
        this.yInicial = y;
        this.x = x;
        this.y = y;
        this.destX = destX;
        this.destY = destY;

        this.angulo = 0;

        try {
            this.imagem = new Imagem("resources/ataques/"+name+"/"+name+".gif");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }

        deltaX = Math.abs(this.x - this.destX);
        deltaY = Math.abs(this.y - this.destY);

        this.dx = Math.cos(Math.toRadians(angulo)) * velocidade;
        this.dy = -Math.sin(Math.toRadians(angulo)) * velocidade;



    }

    public void step(long timeElapsed) {
        if (this.desativado) {
            return;
        }
        this.x += this.dx;
        this.y += this.dy;


    }

    @Override
    public void draw(Graphics g) {
        if (this.desativado) {
            return;
        }
        this.imagem.drawRotated(g, this.x, this.y, this.angulo);

    }

    @Override
    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, this.imagem.pegaLargura(), this.imagem.pegaAltura());
    }

    @Override
    public boolean temColisao(Rectangle retangulo) {
        if (this.desativado) {
            return false;
        }

        if (this.getRetangulo().intersects(retangulo)) {
            AudioPlayer.play("resources/sounds/Sound 2.wav");
            this.desativado = true;
            return true;
        } else {
            return false;
        }
    }

    public void ajustaAtaque() {
        switch (this.direcao) {
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
                this.y -= this.imagem.pegaAltura() - 30;
                break;
            case BAIXO:
                this.x += this.personagem.spriteAtual.pegaLargura() / 2 - 20;
                this.y += 40;
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
