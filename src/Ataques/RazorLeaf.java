/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import DAO.AtaqueDAO;
import javax.swing.JOptionPane;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import tcc.Personagem;



/**
 *
 * @author Maike
 */
public class RazorLeaf extends Ataque {

    int frameElapsed;
    int frame;
    Animation animation;
    
    public RazorLeaf(int x, int y, int destX, int destY, float angulo, Personagem personagem){
        
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

        this.angulo = (float) angulo;

        this.desativado = false;
        this.x = x - (this.personagem.spriteAtual.getWidth() / 2 + 20);
        this.y = y - (this.personagem.spriteAtual.getHeight() / 2 + 50);
        this.frame = 0;
        try {
            this.sprite = new SpriteSheet("resources/ataques/" + name + "/" + name + ".png", 214, 200);
        } catch (SlickException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: "+ex.getMessage());
        }
        this.animation = new Animation();
        for (int i = 0; i < 8; i++) {
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
        if(this.animation.isStopped()){
            return;
        }
        g.rotate(this.animation.getCurrentFrame().getCenterOfRotationX() + this.x, this.animation.getCurrentFrame().getCenterOfRotationY() + this.y, this.angulo);
        this.animation.draw(this.x, this.y);
        g.rotate(this.animation.getCurrentFrame().getCenterOfRotationX() + this.x, this.animation.getCurrentFrame().getCenterOfRotationY() + this.y, -this.angulo);

    }
    
        public Rectangle getRetangulo() {
            return new Rectangle(this.x, this.y, 220, 85);
    }

    
    public boolean temColisao(Rectangle retangulo) {
        if (this.desativado || this.frame == 9) {
            return false;
        }

        if (this.getRetangulo().intersects(retangulo)) {
            this.desativado = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean estaAtivo() {
        return (this.desativado == false);
    }

    public int getFrames() {
        return this.frameElapsed;
    }


}
