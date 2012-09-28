package tcc;

import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javaPlay2.GameEngine;
import javaPlay2.Keyboard;
import javaPlay2.Mouse;

//fazer o DAO de acordo com o personagem recebido como parametro
//stats entao so pegar do DAO
public class Player extends ObjetoComMovimento {

    public Personagem personagem; //personagem escolhido pelo player
//    public int vida;
//    protected int velocidade = 1;
//    protected int velocidadeInicial = 1;
//    protected Imagem spriteAtual;
//    protected int cooldownAtaque;
//    public Rectangle campoDeVisao;
//
//    
    public double angulo; //angulo formado entre o personagem e o mouse
    public boolean atacou; // se o player atacou
    public int destX; // ponto x do clique do mouse
    public int destY; // ponto y do clique do mouse
    int controleTiro; //revisar isso
    int framesControleTiro; //revisar isso
    public Rectangle campoDeVisao;
    public int xMouse;
    public int yMouse;
    public int offsetx;
    public int offsety;
    public boolean apertouDireita;
    public boolean apertouEsquerda;
    public boolean apertouCima;
    public boolean apertouBaixo;
    public boolean apertouDireitaCima;
    public boolean apertouDireitaBaixo;
    public boolean apertouEsquerdaCima;
    public boolean apertouEsquerdaBaixo;
    int velocidade = 5;

    public Player(Personagem personagem, int xSpawn, int ySpawn) {


        this.destX = 0;
        this.destY = 0;
        this.atacou = false;

        this.personagem = personagem;

        this.personagem.setDirecao(Direcao.DIREITA);

        this.personagem.setX((GameEngine.getInstance().getGameCanvas().getWidth() / 2 - personagem.spriteAtual.pegaLargura() / 2)+xSpawn);
        this.personagem.setY((GameEngine.getInstance().getGameCanvas().getHeight() / 2 - personagem.spriteAtual.pegaAltura() / 2)+ySpawn);


        this.offsetx = -xSpawn;
        this.offsety = -ySpawn;

    }

    public void step(long timeElapsed) {
        personagem.step(timeElapsed);
        
          // Cima e Baixo
////////        if (this.apertouCima) {
//////////            this.personagem.moveCima(10);
////////        }
////////        if (this.apertouBaixo) {
////////            this.personagem.moveBaixo(10);
////////        }
////////
////////
////////        // Direção para a direita
////////        if (this.apertouDireita) {
////////            this.personagem.moveDireita(10);
////////        }
////////        if (this.apertouDireitaBaixo) {
////////            this.personagem.moveDireitaBaixo(10);
////////        }
////////        if (this.apertouDireitaCima) {
////////            this.personagem.moveDireitaCima(10);
////////        }
////////
////////        // Direção para a esquerda
////////        if (this.apertouEsquerda) {
////////            this.personagem.moveEsquerda(10);
////////        }
////////        if (this.apertouEsquerdaBaixo) {
////////            this.personagem.moveEsquerdaBaixo(10);
////////        }
////////        if (this.apertouEsquerdaCima) {
////////            this.personagem.moveEsquerdaCima(10);
////////        }
////////        
////////        
        
        
        
        
        

        Keyboard teclado = GameEngine.getInstance().getKeyboard();

        if (teclado.keyDown(Keys.A) && teclado.keyDown(Keys.W)) {
            this.personagem.direcao = Direcao.ESQUERDA_CIMA;
            this.personagem.spriteAtual = this.personagem.spriteLeft;
            this.apertouEsquerdaCima = true;
            int i = (int)Math.floor( Math.sqrt( Math.pow(this.velocidade, 2) / 2));
            this.offsetx += i;
            this.offsety += i;
            this.personagem.moveEsquerdaCima(this.velocidade);
//            this.apertouDireita = false;
//            this.apertouEsquerda = false;
//            this.apertouCima = false;
//            this.apertouBaixo = false;
//            this.apertouDireitaCima = false;
//            this.apertouDireitaBaixo = false;
//            this.apertouEsquerdaBaixo = false;
            //this.personagem.moveEsquerdaCima(7);

        } else if (teclado.keyDown(Keys.A) && teclado.keyDown(Keys.S)) {
            this.personagem.direcao = Direcao.ESQUERDA_BAIXO;
            this.personagem.spriteAtual = this.personagem.spriteLeft;
            this.apertouEsquerdaBaixo = true;
            int i = (int)Math.floor( Math.sqrt( Math.pow(this.velocidade, 2) / 2));
            this.offsetx += i;
            this.offsety -= i;
            this.personagem.moveEsquerdaBaixo(this.velocidade);
//            this.apertouDireita = false;
//            this.apertouEsquerda = false;
//            this.apertouCima = false;
//            this.apertouBaixo = false;
//            this.apertouDireitaCima = false;
//            this.apertouDireitaBaixo = false;
//            this.apertouEsquerdaCima = false;
            //this.personagem.moveEsquerdaBaixo(7);

        } else if (teclado.keyDown(Keys.D) && teclado.keyDown(Keys.W)) {
            this.personagem.direcao = Direcao.DIREITA_CIMA;
            this.personagem.spriteAtual = this.personagem.spriteRight;
            this.apertouDireitaCima = true;
            this.personagem.moveDireitaCima(this.velocidade);
            int i = (int)Math.floor( Math.sqrt( Math.pow(this.velocidade, 2) / 2));
            this.offsetx -= i;
            this.offsety += i;
//            this.apertouDireita = false;
//            this.apertouEsquerda = false;
//            this.apertouCima = false;
//            this.apertouBaixo = false;
//            this.apertouDireitaBaixo = false;
//            this.apertouEsquerdaCima = false;
//            this.apertouEsquerdaBaixo = false;
            //this.personagem.moveDireitaCima(7);

        } else if (teclado.keyDown(Keys.D) && teclado.keyDown(Keys.S)) {
            this.personagem.direcao = Direcao.DIREITA_BAIXO;
            this.personagem.spriteAtual = this.personagem.spriteRight;
            this.apertouDireitaBaixo = true;
            int i = (int)Math.floor( Math.sqrt( Math.pow(this.velocidade, 2) / 2));
            this.offsetx -= i;
            this.offsety -= i;
            this.personagem.moveDireitaBaixo(this.velocidade);
//            this.apertouDireita = false;
//            this.apertouEsquerda = false;
//            this.apertouCima = false;
//            this.apertouBaixo = false;
//            this.apertouDireitaCima = false;
//            this.apertouEsquerdaCima = false;
//            this.apertouEsquerdaBaixo = false;
            //this.personagem.moveDireitaBaixo(7);

        } else if (teclado.keyDown(Keys.D)) {
            this.personagem.direcao = Direcao.DIREITA;
            this.personagem.spriteAtual = this.personagem.spriteRight;
            this.offsetx -= this.velocidade;
            this.personagem.moveDireita(this.velocidade);
//            this.apertouDireita = true;
//            this.apertouEsquerda = false;
//            this.apertouCima = false;
//            this.apertouBaixo = false;
//            this.apertouDireitaCima = false;
//            this.apertouDireitaBaixo = false;
//            this.apertouEsquerdaCima = false;
//            this.apertouEsquerdaBaixo = false;
            //this.personagem.moveDireita(5);

        } else if (teclado.keyDown(Keys.A)) {
            this.personagem.direcao = Direcao.ESQUERDA;
            this.personagem.spriteAtual = this.personagem.spriteLeft;
            this.offsetx += this.velocidade;
            this.personagem.moveEsquerda(this.velocidade);
//            this.apertouEsquerda = true;
//            this.apertouDireita = false;
//            this.apertouCima = false;
//            this.apertouBaixo = false;
//            this.apertouDireitaCima = false;
//            this.apertouDireitaBaixo = false;
//            this.apertouEsquerdaCima = false;
//            this.apertouEsquerdaBaixo = false;
            //this.personagem.moveEsquerda(5);

        } else if (teclado.keyDown(Keys.W)) {
            this.personagem.direcao = Direcao.CIMA;
            this.personagem.spriteAtual = this.personagem.spriteUp;
            this.offsety += this.velocidade;
//            this.apertouCima = true;
//            this.apertouDireita = false;
//            this.apertouEsquerda = false;
//            this.apertouBaixo = false;
//            this.apertouDireitaCima = false;
//            this.apertouDireitaBaixo = false;
//            this.apertouEsquerdaCima = false;
//            this.apertouEsquerdaBaixo = false;
            this.personagem.moveCima(this.velocidade);
            //this.personagem.moveCima(5);

        } else if (teclado.keyDown(Keys.S)) {
            this.personagem.direcao = Direcao.BAIXO;
            this.personagem.spriteAtual = this.personagem.spriteDown;
            this.offsety -= this.velocidade;
//            this.apertouBaixo = true;
//            this.apertouDireita = false;
//            this.apertouEsquerda = false;
//            this.apertouCima = false;
//            this.apertouDireitaCima = false;
//            this.apertouDireitaBaixo = false;
//            this.apertouEsquerdaCima = false;
//            this.apertouEsquerdaBaixo = false;
            this.personagem.moveBaixo(this.velocidade);
            //this.personagem.moveBaixo(5);

        } else {
            this.apertouDireita = false;
            this.apertouEsquerda = false;
            this.apertouCima = false;
            this.apertouBaixo = false;
            this.apertouDireitaCima = false;
            this.apertouDireitaBaixo = false;
            this.apertouEsquerdaCima = false;
            this.apertouEsquerdaBaixo = false;
        }




        Mouse mouse = GameEngine.getInstance().getMouse();
        Point ponto = mouse.getMousePos();
        this.xMouse = ponto.x;
        this.yMouse = ponto.y;


        if (mouse.isLeftButtonPressed()) {
            ponto = new Point(mouse.getMousePos());
            this.destX = ponto.x;
            this.destY = ponto.y;
          //  this.destX = this.xMouse+this.offsetx;
          //  this.destY = this.yMouse+this.offsety;
           // this.angulo = util.Util.calculaAngulo(destX, this.personagem.getX(), destY, this.personagem.getY());
            this.angulo = util.Util.calculaAngulo(destX, GameEngine.getInstance().getGameCanvas().getWidth()/2, destY, GameEngine.getInstance().getGameCanvas().getHeight()/2);
            this.atacou = true;
        }
    }

    public void draw(Graphics g) {
        personagem.draw(g);
    }

    public Direcao getDirecao() {
        return this.personagem.getDirecao();
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

    public double getAngulo() {
        return angulo;
    }

    public int getDestX() {
        return destX;
    }

    public int getDestY() {
        return destY;
    }

    @Override
    public int getX() {
        return this.personagem.getX();
    }

    @Override
    public int getY() {
        return this.personagem.getY();
    }

    public int getXMouse() {
        return xMouse;
    }

    public int getYMouse() {
        return yMouse;
    }

    public void setCampoDeVisao() {
        this.campoDeVisao = new Rectangle(this.personagem.getX(), this.personagem.getY(), this.personagem.spriteAtual.pegaLargura(), 800);
        switch (this.personagem.direcao) {
            case CIMA:
                this.campoDeVisao = new Rectangle(this.personagem.getX(), this.personagem.getY() - 800, this.personagem.spriteAtual.pegaLargura(), 800);
                break;
            case BAIXO:
                this.campoDeVisao = new Rectangle(this.personagem.getX(), this.personagem.getY(), this.personagem.spriteAtual.pegaLargura(), 800);
                break;
            case ESQUERDA:
                this.campoDeVisao = new Rectangle(this.personagem.getX() - 800, this.personagem.getY(), 800, this.personagem.spriteAtual.pegaAltura());
                break;
            case DIREITA:
                this.campoDeVisao = new Rectangle(this.personagem.getX(), this.personagem.getY(), 800, this.personagem.spriteAtual.pegaAltura());
                break;
            case DIREITA_BAIXO:
            case DIREITA_CIMA:
            case ESQUERDA_BAIXO:
            case ESQUERDA_CIMA:
            //nao faz nada;


        }
    }

    public Rectangle getCampoDeVisao() {
        return this.campoDeVisao;
    }
}