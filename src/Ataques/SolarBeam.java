package Ataques;

import DAO.AtaqueDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import tcc.Personagem;

public class SolarBeam extends Ataque {

    public SolarBeam(int x, int y, int destX, int destY, float angulo, Personagem personagem) {
        this.setContador(0);
        this.personagem = personagem;
        String name = this.toString();
        if (name.lastIndexOf('.') > 0) {
            name = name.substring(name.lastIndexOf('.') + 1, name.indexOf('@'));
        }
        model.Ataque a = AtaqueDAO.getAtaque(name);
        this.setDanoBruto(a.getAtk());

        this.desativado = false;
        this.x = x;
        this.y = y;
        try {
            this.sprite = new SpriteSheet("resources/ataques/" + name + "/" + name + ".png", 533,92);
        } catch (SlickException ex) {
            Logger.getLogger(FlameBurst.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.animation = new Animation();
        for (int i = 0; i < 1; i++) {
            animation.addFrame(sprite.getSprite(i, 0), 150);
        }
        this.animation.setLooping(false);

        this.x -= this.animation.getCurrentFrame().getWidth() / 2;
        this.y -= this.animation.getCurrentFrame().getHeight() / 2;
        this.x += this.personagem.spriteAtual.getWidth() / 2;
        this.y += this.personagem.spriteAtual.getHeight() / 2;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        if (this.desativado) {
            this.contadorDano++;
        }
        if (acertou == true) {
            this.contadorDano++;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if (!animation.isStopped()) {
            this.animation.draw(this.x, this.y);
        }
    }
}