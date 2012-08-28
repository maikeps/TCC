package Ataques;

import DAO.AtaqueDAO;
import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.Sprite;
import javaPlayExtras.AudioPlayer;
import javax.swing.JOptionPane;

public class WaterPulse extends Ataque {

    int frameElapsed;
    int frame;
    Sprite sprite;
    Sprite spriteVazio;
    Sprite spriteAtual;

    public WaterPulse(int x, int y, int destX, int destY, double angulo, Personagem personagem) {
        
        String name = this.toString();
        if (name.lastIndexOf('.') > 0) {
            name = name.substring(name.lastIndexOf('.') + 1, name.indexOf('@'));
        }
        model.Ataque a = AtaqueDAO.getAtaque(name);
        this.setDano(a.getAtk());
        
        this.personagem = personagem;
        AudioPlayer.play("resources/sounds/Sound 1.wav");
        this.desativado = false;
        this.x = x - (this.personagem.spriteAtual.pegaLargura() + 70);
        this.y = y - (this.personagem.spriteAtual.pegaAltura() + 85);
        this.frame = 0;

        try {
            this.sprite = new Sprite("resources/ataques/"+name+"/"+name+".png", 4, 270, 250);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }



    }

    @Override
    public void step(long timeElapsed) {
        if (this.frame >= 4) {
            return;
        }

        this.frameElapsed += 1;
        if (this.frameElapsed > 4) {
            this.frame++;
            this.sprite.setCurrAnimFrame(this.frame);
            this.frameElapsed -= 4;
        }
    }

    @Override
    public void draw(Graphics g) {
        this.sprite.draw(g, this.x, this.y);
    }

    @Override
    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, 261, 248);
    }

    @Override
    public boolean temColisao(Rectangle retangulo) {
        if (this.desativado || this.frame == 4) {
            return false;
        }

        if (this.getRetangulo().intersects(retangulo)) {
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
