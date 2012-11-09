package Ataques;

import DAO.AtaqueDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import tcc.Personagem;

public class Hurricane extends Ataque {

    int frameElapsed;
    int frame;

    public Hurricane(int x, int y, int destX, int destY, float angulo, Personagem personagem) {
        this.personagensAcertados = new ArrayList<Personagem>();
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
        this.frame = 0;
        this.angulo = (float) angulo;
        this.desativado = false;

        try {
            this.sprite = new SpriteSheet("resources/ataques/" + name + "/" + name + ".png", 72, 70);
        } catch (SlickException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
        }
        this.animation = new Animation();
        for (int i = 0; i < 2; i++) {
            animation.addFrame(sprite.getSprite(i, 0), 100);
        }

        deltaX = Math.abs(this.x - this.destX);
        deltaY = Math.abs(this.y - this.destY);
        this.dx = Math.cos(Math.toRadians(angulo)) * velocidade;
        this.dy = -Math.sin(Math.toRadians(angulo)) * velocidade;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        if (this.desativado) {
            this.contadorDano++;
            return;
        }
        this.x += this.dx;
        this.y += this.dy;
        if (acertou == true) {
            this.contadorDano++;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
            this.animation.draw(this.x, this.y);
    }

    @Override
    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, this.animation.getWidth(), this.animation.getHeight());
    }

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
