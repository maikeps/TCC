package Ataques;

import DAO.AtaqueDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import tcc.Personagem;



public class Bite extends Ataque {

    public Bite(int x, int y, int destX, int destY, float angulo, Personagem personagem){

        this.setContador(0);
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

        this.angulo = 0;
        try {
            this.imagem = new Image("resources/ataques/"+name+"/"+name+".gif");
        } catch (SlickException ex) {
            Logger.getLogger(Bite.class.getName()).log(Level.SEVERE, null, ex);
        }


        deltaX = Math.abs(this.x - this.destX);
        deltaY = Math.abs(this.y - this.destY);

        this.dx = Math.cos(Math.toRadians(angulo)) * velocidade;
        this.dy = -Math.sin(Math.toRadians(angulo)) * velocidade;



    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        if(this.desativado == true){
            this.contadorDano ++;
            return;
        }
        
        this.x += this.dx;
        this.y += this.dy;
        
        if(this.getAcertou() == true){
            this.contadorDano ++;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if(this.desativado == true){
            return;
        }
        this.imagem.rotate(this.angulo);
        this.imagem.draw(this.x, this.y);
    }
}
