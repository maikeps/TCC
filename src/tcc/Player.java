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
    
    
    public Player(Personagem personagem) {

        
        this.destX = 0;
        this.destY = 0;
        this.atacou = false;

        this.personagem = personagem;

        this.personagem.setDirecao(Direcao.DIREITA);

        this.personagem.setX(GameEngine.getInstance().getGameCanvas().getWidth()/2 - personagem.spriteAtual.pegaLargura()/2);
        this.personagem.setY(GameEngine.getInstance().getGameCanvas().getHeight()/2 - personagem.spriteAtual.pegaAltura()/2);

        
        

    }

    public void step(long timeElapsed) {
        personagem.step(timeElapsed);

        Keyboard teclado = GameEngine.getInstance().getKeyboard();

        if (teclado.keyDown(Keys.A) && teclado.keyDown(Keys.W)) {
            this.personagem.direcao = Direcao.ESQUERDA_CIMA;
            this.personagem.spriteAtual = this.personagem.spriteLeft;
            //this.personagem.moveEsquerdaCima(7);

        } else if (teclado.keyDown(Keys.A) && teclado.keyDown(Keys.S)) {
            this.personagem.direcao = Direcao.ESQUERDA_BAIXO;
            this.personagem.spriteAtual = this.personagem.spriteLeft;
            //this.personagem.moveEsquerdaBaixo(7);

        } else if (teclado.keyDown(Keys.D) && teclado.keyDown(Keys.W)) {
            this.personagem.direcao = Direcao.DIREITA_CIMA;
            this.personagem.spriteAtual = this.personagem.spriteRight;
            //this.personagem.moveDireitaCima(7);

        } else if (teclado.keyDown(Keys.D) && teclado.keyDown(Keys.S)) {
            this.personagem.direcao = Direcao.DIREITA_BAIXO;
            this.personagem.spriteAtual = this.personagem.spriteRight;
            //this.personagem.moveDireitaBaixo(7);

        } else if (teclado.keyDown(Keys.D)) {
            this.personagem.direcao = Direcao.DIREITA;
            this.personagem.spriteAtual = this.personagem.spriteRight;
            this.offsetx -= 5; 
            //this.personagem.moveDireita(5);

        } else if (teclado.keyDown(Keys.A)) {
            this.personagem.direcao = Direcao.ESQUERDA;
            this.personagem.spriteAtual = this.personagem.spriteLeft;
            this.offsetx += 5;
            //this.personagem.moveEsquerda(5);

        } else if (teclado.keyDown(Keys.W)) {
            this.personagem.direcao = Direcao.CIMA;
            this.personagem.spriteAtual = this.personagem.spriteUp;
            this.offsety += 5;
            //this.personagem.moveCima(5);

        } else if (teclado.keyDown(Keys.S)) {
            this.personagem.direcao = Direcao.BAIXO;
            this.personagem.spriteAtual = this.personagem.spriteDown;
            this.offsety -= 5;
            //this.personagem.moveBaixo(5);

        }


        Mouse mouse = GameEngine.getInstance().getMouse();
        Point ponto = mouse.getMousePos();
        this.xMouse = ponto.x;
        this.yMouse = ponto.y;


        if (mouse.isLeftButtonPressed()) {
            ponto = new Point(mouse.getMousePos());
////            this.destX = ponto.x;
////            this.destY = ponto.y;
            this.destX = this.xMouse;
            this.destY = this.yMouse;
            this.angulo = util.Util.calculaAngulo(destX, this.personagem.getX(), destY, this.personagem.getY());
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
    public int getX(){
        return this.personagem.getX();
    }
    
    @Override
    public int getY(){
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