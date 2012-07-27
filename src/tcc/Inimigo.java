package tcc;

import Personagens.Personagem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.GameEngine;
import javaPlay2.Keyboard;
import javaPlay2.Imagem;

public class Inimigo extends ObjetoComMovimento {

    public Rectangle campoDeVisao;
    public Personagem personagem;
    public Player player;
    public int vida;
    protected int velocidade = 2;
    protected Imagem spriteAtual;
    protected int cooldownAtaque;
    public boolean atacou;
    int xPlayer;
    int yPlayer;
    int qualAtaque;
    int distanciaX;
    int distanciaY;
    int minDistanciaX = 50;
    int minDistanciaY = 50;
    EstadoInimigo estado;
    
    int destX, destY;
    double angulo;
    //diminuir minDistancia para 50, e so chamar o metodo aproxima se o ataque sorteado tiver uma distancia curta
    //so entao aproxima

    //o player estiver na linha de visao do inimigo e se estiver suficientemente perto, o inimigo atira.
    public Inimigo(Personagem personagem, Player player) {

        this.atacou = false;

        this.personagem = personagem;
        this.player = player;

        this.personagem.setDirecao(Direcao.ESQUERDA);

        this.personagem.setX(400);
        this.personagem.setY(500);

        this.x = this.personagem.getX();
        this.y = this.personagem.getY();

        this.xPlayer = player.getPersonagem().getX();
        this.yPlayer = player.getPersonagem().getY();


    }

    public void step(long timeElapsed) {

        this.direcao = this.personagem.getDirecao();

        switch (this.direcao) {
            case BAIXO:
                this.personagem.spriteAtual = this.personagem.spriteDown;
                break;
            case CIMA:
                this.personagem.spriteAtual = this.personagem.spriteUp;
                break;
            case DIREITA:
                this.personagem.spriteAtual = this.personagem.spriteRight;
                break;
            case ESQUERDA:
                this.personagem.spriteAtual = this.personagem.spriteLeft;
                break;
            case DIREITA_BAIXO:
            case DIREITA_CIMA:
            case ESQUERDA_BAIXO:
            case ESQUERDA_CIMA:
            //
        }

        this.setCampoDeVisao();

        personagem.step(timeElapsed);



        if (this.personagem.getHp() <= 50) {
            this.afasta();
            //this.estado = EstadoInimigo.FUGINDO;
        } else {
            this.estado = EstadoInimigo.PERSEGUINDO;
        }

        if (this.estado == EstadoInimigo.PERSEGUINDO) {
            this.aproxima();
            this.ataca();
            this.velocidade = 2;
        }
        if (this.estado == EstadoInimigo.FUGINDO) {
            //this.afasta();
            this.velocidade = 4;
        }


        if (this.distanciaX < 0) {
            this.distanciaX *= (-1);
        }
        if (this.distanciaY < 0) {
            this.distanciaY *= (-1);
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

    public void setXPlayer(int n) {
        this.xPlayer = n;
    }

    public void setYPlayer(int n) {
        this.yPlayer = n;
    }

    public void setDistanciaX(int distanciaX) {
        this.distanciaX = distanciaX;
    }

    public void setDistanciaY(int distanciaY) {
        this.distanciaY = distanciaY;
    }

    public int getDistanciaX() {
        return distanciaX;
    }

    public int getDistanciaY() {
        return distanciaY;
    }

    public void aproxima() {


        if (this.yPlayer >= this.personagem.getY() && this.calculaDistanciaAtePlayer(xPlayer, yPlayer) >= 80) {
            this.personagem.direcao = Direcao.BAIXO;
            this.personagem.spriteAtual = this.personagem.spriteDown;
            this.personagem.moveBaixo(this.velocidade);
        } else if (this.yPlayer >= this.personagem.getY()) {
            this.personagem.direcao = Direcao.BAIXO;
            this.personagem.spriteAtual = this.personagem.spriteDown;
        }

        if (this.yPlayer < this.personagem.getY() && this.calculaDistanciaAtePlayer(xPlayer, yPlayer) >= 80) {
            this.personagem.direcao = Direcao.CIMA;
            this.personagem.spriteAtual = this.personagem.spriteUp;
            this.personagem.moveCima(this.velocidade);
        } else if (this.yPlayer < this.personagem.getY()) {
            this.personagem.direcao = Direcao.CIMA;
            this.personagem.spriteAtual = this.personagem.spriteUp;
        }
        if (this.xPlayer < this.personagem.getX() && this.calculaDistanciaAtePlayer(xPlayer, yPlayer) >= 80) {
            this.personagem.direcao = Direcao.ESQUERDA;
            this.personagem.spriteAtual = this.personagem.spriteLeft;
            this.personagem.moveEsquerda(this.velocidade);
        } else if (this.xPlayer < this.personagem.getX()) {
            this.personagem.direcao = Direcao.ESQUERDA;
            this.personagem.spriteAtual = this.personagem.spriteLeft;
        }

        if (this.xPlayer >= this.personagem.getX() && this.calculaDistanciaAtePlayer(xPlayer, yPlayer) >= 80) {
            this.personagem.direcao = Direcao.DIREITA;
            this.personagem.spriteAtual = this.personagem.spriteRight;
            this.personagem.moveDireita(this.velocidade);
        } else if (this.xPlayer >= this.personagem.getX()) {
            this.personagem.direcao = Direcao.DIREITA;
            this.personagem.spriteAtual = this.personagem.spriteRight;
        }

        //        
//        if (this.xPlayer >= this.personagem.getX() && this.yPlayer >= this.personagem.getY()) {
//            this.personagem.direcao = Direcao.DIREITA_BAIXO;
//            this.personagem.spriteAtual = this.personagem.spriteDown;
//        }
//        if (this.xPlayer >= this.personagem.getX() && this.yPlayer < this.personagem.getY()) {
//            this.personagem.direcao = Direcao.DIREITA_CIMA;
//            this.personagem.spriteAtual = this.personagem.spriteUp;
//        }
        //if (this.xPlayer < this.personagem.getX() && this.yPlayer >= this.personagem.getY()) {
//        if (this.distanciaX >= this.distanciaY && this.yPlayer < this.personagem.getY()) {
//            //this.personagem.direcao = Direcao.ESQUERDA_BAIXO;
//            this.personagem.spriteAtual = this.personagem.spriteUp;
//        } 
//        if (this.xPlayer < this.personagem.getX() && this.yPlayer < this.personagem.getY()) {
//           // this.personagem.direcao = Direcao.ESQUERDA_CIMA;
//            this.personagem.spriteAtual = this.personagem.spriteLeft;
//        }
    }

    public void afasta() {
        //se o ataque sorteado for de longa distancia, ou se estiver com pouca vida, tenta se afastar
        //o inimigo vai tentar sair da linha de visao do player se estiver morrendo


        this.estado = EstadoInimigo.FUGINDO;


        if (this.yPlayer >= this.personagem.getY() && this.distanciaY >= this.minDistanciaY) {
            if (this.playerEstaVendo()) {
                this.personagem.direcao = Direcao.CIMA;
                //this.personagem.spriteAtual = this.personagem.spriteUp;
                this.personagem.moveCima(this.velocidade);
            } else {
                this.estado = EstadoInimigo.PERSEGUINDO;
            }

        }

        if (this.yPlayer < this.personagem.getY() && this.distanciaY < (this.minDistanciaY)) {
            if (this.playerEstaVendo()) {
                this.personagem.direcao = Direcao.BAIXO;
                //this.personagem.spriteAtual = this.personagem.spriteDown;
                this.personagem.moveBaixo(this.velocidade);
            } else {
                this.estado = EstadoInimigo.PERSEGUINDO;
            }
        }
        if (this.xPlayer < this.personagem.getX() && this.distanciaX < (this.minDistanciaY)) {
            if (this.playerEstaVendo()) {
                this.personagem.direcao = Direcao.DIREITA;
                //this.personagem.spriteAtual = this.personagem.spriteRight;
                this.personagem.moveDireita(this.velocidade);
            } else {
                this.estado = EstadoInimigo.PERSEGUINDO;
            }
        } else if (this.xPlayer >= this.personagem.getX() && this.distanciaX >= this.minDistanciaY) {
            if (this.playerEstaVendo()) {
                this.personagem.direcao = Direcao.ESQUERDA;
                //this.personagem.spriteAtual = this.personagem.spriteLeft;
                this.personagem.moveEsquerda(this.velocidade);
            } else {
                this.estado = EstadoInimigo.PERSEGUINDO;
            }
        }
    }

    public void ataca() {
        this.destX = this.player.getX();
        this.destY = this.player.getY();
        this.angulo = util.Util.calculaAngulo(destX, this.personagem.getX(), destY, this.personagem.getY());
        this.atacou = true;
    }

    public double calculaDistanciaAtePlayer(int xPlayer, int yPlayer) {
        int x1 = this.personagem.getX();
        int y1 = this.personagem.getY();
        int x2 = xPlayer;
        int y2 = yPlayer;

        int x = x2 - x1;
        int y = y2 - y1;

        //Pow é a função para elevar um número a uma potencia.
        double distanciaAoQuadrado = Math.pow(x, 2) + Math.pow(y, 2);
        //Agora, faz a raiz da distância ao quadrado para ter a distância.
        //Math.sqrt é a fórmula que faz a raiz de um número
        double distancia = Math.sqrt(distanciaAoQuadrado);

        return distancia;
    }

    public boolean playerEstaVendo() {
//        return this.player.getCampoDeVisao().intersects(this.personagem.getRetangulo());
        return false;
    }

    public boolean estaVendoPlayer() {
        return this.getCampoDeVisao().intersects(this.player.personagem.getRetangulo());
    }
//
//    public boolean estaPertoDoPlayer(){
//        
//        return()
//    }

    public void setCampoDeVisao() {
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


        }
    }

    public Rectangle getCampoDeVisao() {
        return this.campoDeVisao;
    }

    public void getEstaProximoDoPlayer() {
    }
    
    
    public int getX(){
        return this.personagem.getX();
    }
    public int getY(){
        return this.personagem.getY();
    }

    public double getAngulo() {
        return angulo;
    }
}
