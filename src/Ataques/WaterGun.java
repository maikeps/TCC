package Ataques;

import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javaPlay2.Imagem;
import javaPlayExtras.AudioPlayer;
import javax.swing.JOptionPane;

public class WaterGun extends Ataque {

    double angulo;
    int destX;
    int destY;
    double deltaX, deltaY, dx, dy;

    public WaterGun(int x, int y, int destX, int destY, double angulo, Personagem personagem) {

        this.setDano(5);
        this.desativado = false;
        this.xInicial = x;
        this.yInicial = y;
        this.x = x;
        this.y = y;
        this.destX = destX;
        this.destY = destY;
        this.velocidade = 10;

        this.angulo = angulo;

        try {
            this.imagem = new Imagem("resources/ataques/Water Gun/WaterGun_Right.png");

            //  this.spriteAtual = spriteVazio;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
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

        Graphics2D g2d = (Graphics2D) g;
        this.imagem.drawRotated(g, g2d, this.x, this.y, angulo);

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
