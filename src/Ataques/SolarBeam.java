package Ataques;

import DAO.AtaqueDAO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import tcc.Personagem;

public class SolarBeam extends Ataque {

    int contador;

    public SolarBeam(int x, int y, int destX, int destY, float angulo, Personagem personagem) {
        this.personagensAcertados = new ArrayList<Personagem>();
        this.setContador(0);
        this.personagem = personagem;
        String name = this.toString();
        if (name.lastIndexOf('.') > 0) {
            name = name.substring(name.lastIndexOf('.') + 1, name.indexOf('@'));
        }
        model.Ataque a = AtaqueDAO.getAtaque(name);
        this.setDanoBruto(a.getAtk());

        this.angulo = angulo;
        
        this.desativado = false;
        this.x = x;
        this.y = y;
        try {
            this.imagem = new Image("resources/ataques/" + name + "/" + name + ".png");
        } catch (SlickException ex) {
            Logger.getLogger(FlameBurst.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        this.contador++;
        if (this.desativado) {
            this.contadorDano++;
        }
        if (acertou == true) {
            this.contadorDano++;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if (this.contador <= 5) {
            g.rotate(this.x, this.y, -this.angulo);
            this.imagem.draw(this.x, this.y);
            g.rotate(this.x, this.y, this.angulo);
        }
    }
}