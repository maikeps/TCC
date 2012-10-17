package tcc;

import java.awt.Point;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

//fazer o DAO de acordo com o personagem recebido como parametro
//stats entao so pegar do DAO
public class Player extends GameObject {

    public Personagem personagem; //personagem escolhido pelo player
//    public int vida;
//    protected int velocidade = 1;
//    protected int velocidadeInicial = 1;
//    protected Imagem spriteAtual;
//    protected int cooldownAtaque;
//    public Rectangle campoDeVisao;
//
//    
    public float angulo; //angulo formado entre o personagem e o mouse
    public boolean atacou; // se o player atacou
    public int destX; // ponto x do clique do mouse
    public int destY; // ponto y do clique do mouse
    int controleTiro; //revisar isso
    int framesControleTiro; //revisar isso
    public int xMouse;
    public int yMouse;
    public int offsetx;
    public int offsety;
    int velocidade = 5;

    public Player(Personagem personagem, int xSpawn, int ySpawn) {


        this.destX = 0;
        this.destY = 0;
        this.atacou = false;

        this.personagem = personagem;

        this.personagem.setDirecao(Direcao.DIREITA);

      //  this.personagem.x = xSpawn;
      //  this.personagem.y = ySpawn;

        this.personagem.x=((800 / 2 - personagem.spriteAtual.getWidth() / 2) + xSpawn);
        this.personagem.y =((600 / 2 - personagem.spriteAtual.getHeight() / 2) + ySpawn);


        this.x = xSpawn;
        this.y = ySpawn;


        this.offsetx = -xSpawn;
        this.offsety = -ySpawn;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        this.personagem.update(gc, game, delta);

        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_D) && input.isKeyDown(Input.KEY_W)) {
            this.personagem.direcao = Direcao.DIREITA_CIMA;
            int i = (int) Math.floor(Math.sqrt(Math.pow(this.velocidade, 2) / 2));
            this.offsetx -= i;
            this.offsety += i;
            this.moveDireitaCima(this.velocidade);
            this.personagem.spriteAtual = this.personagem.spriteRight;

        } else if (input.isKeyDown(Input.KEY_D) && input.isKeyDown(Input.KEY_S)) {
            this.personagem.direcao = Direcao.DIREITA_BAIXO;
            int i = (int) Math.floor(Math.sqrt(Math.pow(this.velocidade, 2) / 2));
            this.offsetx -= i;
            this.offsety -= i;
            this.moveDireitaBaixo(this.velocidade);
            this.personagem.spriteAtual = this.personagem.spriteRight;

        } else if (input.isKeyDown(Input.KEY_A) && input.isKeyDown(Input.KEY_W)) {
            this.personagem.direcao = Direcao.ESQUERDA_CIMA;
            int i = (int) Math.floor(Math.sqrt(Math.pow(this.velocidade, 2) / 2));
            this.offsetx += i;
            this.offsety += i;
            this.moveEsquerdaCima(this.velocidade);
            this.personagem.spriteAtual = this.personagem.spriteLeft;

        } else if (input.isKeyDown(Input.KEY_A) && input.isKeyDown(Input.KEY_S)) {
            this.personagem.direcao = Direcao.ESQUERDA_BAIXO;
            int i = (int) Math.floor(Math.sqrt(Math.pow(this.velocidade, 2) / 2));
            this.offsetx += i;
            this.offsety -= i;
            this.moveEsquerdaBaixo(this.velocidade);
            this.personagem.spriteAtual = this.personagem.spriteLeft;

        } else if (input.isKeyDown(Input.KEY_W)) {
            this.personagem.direcao = Direcao.CIMA;
            this.offsety += this.velocidade;
            this.moveCima(this.velocidade);
            this.personagem.spriteAtual = this.personagem.spriteUp;
        } else if (input.isKeyDown(Input.KEY_S)) {
            this.personagem.direcao = Direcao.BAIXO;
            this.offsety -= this.velocidade;
            this.moveBaixo(this.velocidade);
            this.personagem.spriteAtual = this.personagem.spriteDown;
        } else if (input.isKeyDown(Input.KEY_A)) {
            this.personagem.direcao = Direcao.ESQUERDA;
            this.offsetx += this.velocidade;
            this.moveEsquerda(this.velocidade);
            this.personagem.spriteAtual = this.personagem.spriteLeft;
        } else if (input.isKeyDown(Input.KEY_D)) {
            this.personagem.direcao = Direcao.DIREITA;
            this.offsetx -= this.velocidade;
            this.moveDireita(this.velocidade);
            this.personagem.spriteAtual = this.personagem.spriteRight;
        }


        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            Point ponto = new Point(input.getMouseX(), input.getMouseY());
            this.destX = ponto.x;
            this.destY = ponto.y;
            this.angulo = (float) util.Util.calculaAngulo(destX, gc.getWidth()/2, destY, gc.getHeight()/2);
            this.atacou = true;
        }

        this.personagem.x = gc.getWidth()/2-this.offsetx - personagem.spriteAtual.getWidth() / 2;
        this.personagem.y = gc.getHeight()/2-this.offsety - personagem.spriteAtual.getHeight() / 2;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        this.personagem.render(gc, game, g);
        g.drawString(this.angulo + "", 200, 200);
        g.drawString(this.destX + " - " + this.x, 200 - offsetx, 250 - offsety);
        g.drawString(this.destY + " - " + this.y, 200 - offsetx , 300 - offsety);
        g.drawString(this.personagem.podeAtirar() + " - " + this.personagem.cooldownAtual, 500, 400);
    }

    public Personagem getPersonagem() {
        return this.personagem;
    }

    public void setPersonagem(Personagem p) {
        this.personagem = p;
    }

    public int getHp() {
        return this.personagem.getHp();
    }

    public float getAngulo() {
        return this.angulo;
    }

    public int getDestX() {
        return this.destX;
    }

    public int getDestY() {
        return this.destY;
    }

    public int getX() {
        return this.personagem.x;
    }

    public int getY() {
        return this.personagem.y;
    }

    public float getXMouse() {
        return this.xMouse;
    }

    public float getYMouse() {
        return this.yMouse;
    }

    public int getVelocidade() {
        return this.velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }
}