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

public class RazorLeaf extends Ataque {

    public RazorLeaf(int x, int y, int destX, int destY, float angulo, Personagem personagem) {
        this.personagensAcertados = new ArrayList<Personagem>();
        this.setContador(0);
        this.personagem = personagem;
        String name = this.toString();
        if (name.lastIndexOf('.') > 0) {
            name = name.substring(name.lastIndexOf('.') + 1, name.indexOf('@'));
        }
        model.Ataque a = AtaqueDAO.getAtaque(name);
        this.setDanoBruto(a.getAtk());

        this.desativado = false;
        this.xInicial = x;
        this.yInicial = y;
        this.x = x;
        this.y = y;
        this.destX = destX;
        this.destY = destY;
        this.angulo = angulo;

        try {
            this.sprite = new SpriteSheet("resources/ataques/" + name + "/" + name + ".png", 220, 85);
        } catch (SlickException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: " + ex.getMessage());
        }
        this.animation = new Animation();
        for (int i = 0; i < 8; i++) {
            animation.addFrame(sprite.getSprite(i, 0), 100);
        }
        this.animation.setLooping(false);
        
        this.xRotate = this.x;
        this.yRotate = this.y + this.animation.getHeight() / 2;
        
        this.x = x + (this.personagem.animacaoAtual.getImage().getWidth() / 2);
        this.y = y + (this.personagem.animacaoAtual.getImage().getHeight() / 2) - this.animation.getHeight() / 2;
    
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        if(this.contadorDano > 35){
            return;
        }
        if (this.desativado && this.acertou == true) {
            this.contadorDano++;
        }
        if (animation.isStopped()) {
            this.desativado = true;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if (this.animation.isStopped()) {
            return;
        }
        g.rotate(this.x, this.y + this.animation.getHeight() / 2, -this.angulo);
        this.animation.draw(this.x, this.y);
        g.rotate(this.x, this.y + this.animation.getHeight() / 2, this.angulo);
    }
}
