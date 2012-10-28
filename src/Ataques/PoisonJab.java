package Ataques;

import DAO.AtaqueDAO;
import javax.swing.JOptionPane;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import tcc.Personagem;



public class PoisonJab extends Ataque {

    public PoisonJab(int x, int y, int destX, int destY, float angulo, Personagem personagem) {

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

        this.angulo = angulo;

        try {
            this.imagem = new Image("resources/ataques/"+name+"/"+name+".png");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }

        deltaX = Math.abs(this.x - this.destX);
        deltaY = Math.abs(this.y - this.destY);

        this.dx = Math.cos(Math.toRadians(angulo)) * velocidade;
        this.dy = -Math.sin(Math.toRadians(angulo)) * velocidade;


        this.imagem.rotate(-this.angulo);

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
        this.imagem.draw(this.x, this.y);
    }
}
