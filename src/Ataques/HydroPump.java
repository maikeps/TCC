package Ataques;

import DAO.AtaqueDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;
import tcc.Personagem;

public class HydroPump extends Ataque {

    public HydroPump(int x, int y, int destX, int destY, float angulo, Personagem personagem) {
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
        this.xInicial = x;
        this.yInicial = y;
        this.x = x;
        this.y = y;
        this.destX = destX;
        this.destY = destY;
        this.angulo = (float) angulo;
        this.desativado = false;
        this.x = x - (this.personagem.animacaoAtual.getImage().getWidth() / 2 + 20);
        this.y = y - (this.personagem.animacaoAtual.getImage().getHeight() / 2 + 50);

        try {
            this.sprite = new SpriteSheet("resources/ataques/" + name + "/" + name + ".png", 220, 120);
        } catch (SlickException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
        }
        this.animation = new Animation();
        for (int i = 0; i < 14; i++) {
            animation.addFrame(sprite.getSprite(i, 0), 150);
        }
        this.animation.setLooping(false);
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
        if (this.animation.isStopped()) {
            return;
        }
        g.rotate(this.animation.getCurrentFrame().getCenterOfRotationX() + this.x, this.animation.getCurrentFrame().getCenterOfRotationY() + this.y, -this.angulo);
        this.animation.draw(this.x, this.y);
        g.rotate(this.animation.getCurrentFrame().getCenterOfRotationX() + this.x, this.animation.getCurrentFrame().getCenterOfRotationY() + this.y, this.angulo);

    }

    public boolean estaAtivo() {
        return (this.desativado == false);
    }
}
