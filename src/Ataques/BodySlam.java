package Ataques;

import DAO.AtaqueDAO;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import tcc.Personagem;

public class BodySlam extends Ataque {

    public BodySlam(int x, int y, int destX, int destY, float angulo, Personagem personagem) {
        this.personagensAcertados = new ArrayList<Personagem>();
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

        //    this.imagem = new Image("resources/personagens/" + personagem.getId()+" - "+personagem.getNome() + "/" + personagem.getNome() + "_Right.png");
        this.imagem = personagem.animacaoUp.getImage();
        if (this.angulo >= 45 && this.angulo < 135) {
            this.imagem = personagem.animacaoUp.getImage();
        } else if (this.angulo >= 135 && this.angulo < 225) {
            this.imagem = personagem.animacaoLeft.getImage();
        } else if (this.angulo >= 225 && this.angulo < 315) {
            this.imagem = personagem.animacaoDown.getImage();
        } else if (this.angulo >= 315 || this.angulo < 45) {
            this.imagem = personagem.animacaoRight.getImage();
        }

        deltaX = Math.abs(this.x - this.destX);
        deltaY = Math.abs(this.y - this.destY);
        this.dx = Math.cos(Math.toRadians(angulo)) * velocidade;
        this.dy = -Math.sin(Math.toRadians(angulo)) * velocidade;

        // this.imagem.rotate(-angulo);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        if (this.desativado && acertou == true) {
            this.contadorDano++;
            return;
        }
        if(this.acertou){
            this.desativado = true;
        }
        this.x += this.dx;
        this.y += this.dy;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if (this.desativado == true) {
            return;
        }
        Color cor = new Color(255, 255, 255, 200);
        this.imagem.drawFlash(this.x, this.y, this.imagem.getWidth(), this.imagem.getHeight(), cor);
    }
}
