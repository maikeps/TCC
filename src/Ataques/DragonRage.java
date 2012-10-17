package Ataques;

//revisar ajustaAtaque()

import javax.swing.JOptionPane;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import tcc.Personagem;

public class DragonRage extends Ataque {

    float angulo; // Angulo de inclinação, rotaciona a imagem
    int destX; // Posição do mouse
    int destY; // Posição do mouse
    double deltaX, deltaY, dx, dy;

    public DragonRage(int x, int y, int destX, int destY, float angulo, Personagem personagem) {
        //precisa saber qual personagem atacou para poder calcular
        //a posicao certa do lancamento do ataque



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
            this.imagem = new Image("resources/ataques/Dragon Rage/Rage_Right.png");

            //  this.spriteAtual = spriteVazio;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
            System.exit(1);
        }

        deltaX = Math.abs(this.x - this.destX);
        deltaY = Math.abs(this.y - this.destY);

        this.dx = Math.cos(Math.toRadians(angulo)) * velocidade;
        this.dy = -Math.sin(Math.toRadians(angulo)) * velocidade;

        //rotaciona a imagem uma unica vez para depois desenhar
       // this.imagem.rotate(-this.angulo);
        this.imagem.setRotation(-this.angulo);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        if (this.desativado == true) {
            this.contadorDano++;
            return;
        }

        this.x += this.dx;
        this.y += this.dy;

        if (this.getAcertou() == true) {
            this.contadorDano++;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if (this.desativado == true) {
            return;
        }
        
        g.fillRect(this.x, this.y, 50, 50);
        
        this.imagem.draw(this.x, this.y);
    }
}
