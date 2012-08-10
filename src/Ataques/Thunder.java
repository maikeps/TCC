/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.Sprite;
import javaPlayExtras.AudioPlayer;
import javax.swing.JOptionPane;

/**
 *
 * @author Maike
 */
public class Thunder extends Ataque {

    int frameElapsed;
    int frame;
    Sprite imagem;

    public Thunder(int x, int y, Personagem alvo) {
        this.personagem = alvo;
        this.setDano(15);

        AudioPlayer.play("resources/sounds/Sound 1.wav");
        this.desativado = false;
        this.x = this.personagem.getX() - 50;
        this.y = this.personagem.getY() - 280;
        this.frame = 0;
        try {
            this.imagem = new Sprite("resources/ataques/Thunder/Thunder.png", 7, 165, 315);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }


    }

    public void step(long timeElapsed) {
        if (this.frame >= 7) {
            return;
        }

        this.frameElapsed += 1;
        if (this.frameElapsed > 5) {
            this.frame++;
            this.imagem.setCurrAnimFrame(this.frame);
            this.frameElapsed -= 5;
        }
    }

    public void draw(Graphics g) {
        this.imagem.draw(g, this.x, this.y);
    }

    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, 165, 315);
    }

    public boolean temColisao(Rectangle retangulo) {
        if (this.desativado) {
            return false;
        }

        if (this.getRetangulo().intersects(retangulo)) {
            // AudioPlayer.play("resources/sounds/Sound 2.wav");
            this.desativado = true;
            return true;
        } else {
            return false;
        }
    }

    public int getFrames() {
        return this.frameElapsed;
    }
}
