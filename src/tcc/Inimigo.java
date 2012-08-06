package tcc;

import Personagens.PersonagemTeste;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.Imagem;
import java.awt.Point;
import util.Util;

public class Inimigo extends ObjetoComMovimento {

    public Rectangle campoDeVisao;
    public PersonagemTeste personagem;
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
    int alcancePerseguição = 300;
    int destMovimentoX;
    int destMovimentoY;
    //diminuir minDistancia para 50, e so chamar o metodo aproxima se o ataque sorteado tiver uma distancia curta
    //so entao aproxima

    //o player estiver na linha de visao do inimigo e se estiver suficientemente perto, o inimigo atira.
    public Inimigo(PersonagemTeste personagem, Player player) {
        this.sorteiaDestino();
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

        this.x = this.personagem.getX();
        this.y = this.personagem.getY();

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
                this.personagem.spriteAtual = this.personagem.spriteRight;
            case DIREITA_CIMA:
            case ESQUERDA_BAIXO:
            case ESQUERDA_CIMA:
            //
        }

        //    this.setCampoDeVisao();

        personagem.step(timeElapsed);



        if (this.personagem.getHp() <= 50) {
            this.afasta();
            //this.estado = EstadoInimigo.FUGINDO;
        } else {
            this.estado = EstadoInimigo.PERSEGUINDO;
        }

        if (this.podePerseguir()) {
            this.aproxima();
            this.ataca();
            this.sorteiaDestino();
        } else {
            if (this.x != this.destMovimentoX && this.y != this.destMovimentoY) {
                this.anda();
            } else {
                this.sorteiaDestino();
            }
        }

        if (this.estado == EstadoInimigo.PERSEGUINDO) {
            // this.aproxima();
//            this.ataca();
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
        this.personagem.draw(g);
    }

    public Direcao getDirecao() {
        return this.personagem.getDirecao();
    }

    public PersonagemTeste getPersonagem() {
        return this.personagem;
    }

    public void setPersonagem(PersonagemTeste p) {
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

//        int quadranteDoPlayer = 1;
//
//        if (this.xPlayer > this.x && this.yPlayer < this.y) {
//            quadranteDoPlayer = 1;
//        }
//        if (this.xPlayer < this.x && this.yPlayer < this.y) {
//            quadranteDoPlayer = 2;
//        }
//        if (this.xPlayer < this.x && this.yPlayer > this.y) {
//            quadranteDoPlayer = 3;
//        }
//        if (this.xPlayer > this.x && this.yPlayer > this.y) {
//            quadranteDoPlayer = 4;
//        }


        int quadranteDoPlayer = 1;
        this.angulo = util.Util.calculaAngulo(destX, this.personagem.getX(), destY, this.personagem.getY());

        if (this.xPlayer > this.x && this.yPlayer < this.y && this.angulo <= 45.0) {
            quadranteDoPlayer = 1;
        }
        if (this.xPlayer > this.x && this.yPlayer < this.y && this.angulo <= 90.0 && this.angulo > 4.05) {
            quadranteDoPlayer = 2;
        }
        if (this.xPlayer < this.x && this.yPlayer < this.y && this.angulo <= 135.0 && this.angulo > 90.0) {
            quadranteDoPlayer = 3;
        }
        if (this.xPlayer < this.x && this.yPlayer < this.y && this.angulo <= 180.0 && this.angulo > 135.0) {
            quadranteDoPlayer = 4;
        }
        if (this.xPlayer < this.x && this.yPlayer > this.y && this.angulo <= 225.0 && this.angulo > 180.0) {
            quadranteDoPlayer = 5;
        }
        if (this.xPlayer < this.x && this.yPlayer > this.y && this.angulo <= 270.0 && this.angulo > 225.0) {
            quadranteDoPlayer = 6;
        }
        if (this.xPlayer > this.x && this.yPlayer > this.y && this.angulo <= 315.0 && this.angulo > 270.0) {
            quadranteDoPlayer = 7;
        }
        if (this.xPlayer > this.x && this.yPlayer > this.y && this.angulo <= 360.0 && this.angulo > 315.0) {
            quadranteDoPlayer = 8;
        }
        
        System.out.println(quadranteDoPlayer + " angulo:" + this.angulo);



        switch (quadranteDoPlayer) {
            case 1:
                this.personagem.direcao = Direcao.DIREITA;
              //  this.personagem.spriteAtual = this.personagem.spriteRight;
                this.personagem.moveDireitaCima(this.velocidade);
                break;
            case 2:
                this.personagem.direcao = Direcao.CIMA;
             //   this.personagem.spriteAtual = this.personagem.spriteUp;
                this.personagem.moveDireitaCima(this.velocidade);
                break;
            case 3:
                this.personagem.direcao = Direcao.CIMA;
              //  this.personagem.spriteAtual = this.personagem.spriteUp;
                this.personagem.moveEsquerdaCima(this.velocidade);
                break;
            case 4:
                this.personagem.direcao = Direcao.ESQUERDA;
              //  this.personagem.spriteAtual = this.personagem.spriteLeft;
                this.personagem.moveEsquerdaCima(this.velocidade);
                break;
            case 5:
                this.personagem.direcao = Direcao.ESQUERDA;
               // this.personagem.spriteAtual = this.personagem.spriteLeft;
                this.personagem.moveEsquerdaBaixo(this.velocidade);
                break;
            case 6:
                this.personagem.direcao = Direcao.BAIXO;
             //   this.personagem.spriteAtual = this.personagem.spriteDown;
                this.personagem.moveEsquerdaBaixo(this.velocidade);
                break;
            case 7:
                this.personagem.direcao = Direcao.BAIXO;
              //  this.personagem.spriteAtual = this.personagem.spriteDown;
                this.personagem.moveDireitaBaixo(this.velocidade);
                break;
            case 8:
                this.personagem.direcao = Direcao.DIREITA;
             //   this.personagem.spriteAtual = this.personagem.spriteRight;
                this.personagem.moveDireitaBaixo(this.velocidade);
                break;
        }



//
//        switch (quadranteDoPlayer) {
//            case 1:
//                this.personagem.direcao = Direcao.CIMA;
//                this.personagem.spriteAtual = this.personagem.spriteUp;
//                this.personagem.moveDireitaCima(this.velocidade);
//                break;
//            case 2:
//                this.personagem.direcao = Direcao.ESQUERDA;
//                this.personagem.spriteAtual = this.personagem.spriteLeft;
//                this.personagem.moveEsquerdaCima(this.velocidade);
//                break;
//            case 3:
//                this.personagem.direcao = Direcao.BAIXO;
//                this.personagem.spriteAtual = this.personagem.spriteDown;
//                this.personagem.moveEsquerdaBaixo(this.velocidade);
//                break;
//            case 4:
//                this.personagem.direcao = Direcao.DIREITA;
//                this.personagem.spriteAtual = this.personagem.spriteRight;
//                this.personagem.moveDireitaBaixo(this.velocidade);
//                break;
//        }

//
//                if (this.yPlayer >= this.personagem.getY() && this.calculaDistanciaAtePlayer(xPlayer, yPlayer) >= 80) {
//                    this.personagem.direcao = Direcao.BAIXO;
//                    this.personagem.spriteAtual = this.personagem.spriteDown;
//                    this.personagem.moveBaixo(this.velocidade);
//                } else if (this.yPlayer >= this.personagem.getY()) {
//                    this.personagem.direcao = Direcao.BAIXO;
//                    this.personagem.spriteAtual = this.personagem.spriteDown;
//                }
//        
//                if (this.yPlayer < this.personagem.getY() && this.calculaDistanciaAtePlayer(xPlayer, yPlayer) >= 80) {
//                    this.personagem.direcao = Direcao.CIMA;
//                    this.personagem.spriteAtual = this.personagem.spriteUp;
//                    this.personagem.moveCima(this.velocidade);
//                } else if (this.yPlayer < this.personagem.getY()) {
//                    this.personagem.direcao = Direcao.CIMA;
//                    this.personagem.spriteAtual = this.personagem.spriteUp;
//                }
//                if (this.xPlayer < this.personagem.getX() && this.calculaDistanciaAtePlayer(xPlayer, yPlayer) >= 80) {
//                    this.personagem.direcao = Direcao.ESQUERDA;
//                    this.personagem.spriteAtual = this.personagem.spriteLeft;
//                    this.personagem.moveEsquerda(this.velocidade);
//                } else if (this.xPlayer < this.personagem.getX()) {
//                    this.personagem.direcao = Direcao.ESQUERDA;
//                    this.personagem.spriteAtual = this.personagem.spriteLeft;
//                }
//        
//                if (this.xPlayer >= this.personagem.getX() && this.calculaDistanciaAtePlayer(xPlayer, yPlayer) >= 80) {
//                    this.personagem.direcao = Direcao.DIREITA;
//                    this.personagem.spriteAtual = this.personagem.spriteRight;
//                    this.personagem.moveDireita(this.velocidade);
//                } else if (this.xPlayer >= this.personagem.getX()) {
//                    this.personagem.direcao = Direcao.DIREITA;
//                    this.personagem.spriteAtual = this.personagem.spriteRight;
//                }
//        
//        
//                if (this.xPlayer >= this.personagem.getX() && this.yPlayer >= this.personagem.getY()) {
//                    this.personagem.direcao = Direcao.DIREITA_BAIXO;
//                    this.personagem.spriteAtual = this.personagem.spriteDown;
//                }
//                if (this.xPlayer >= this.personagem.getX() && this.yPlayer < this.personagem.getY()) {
//                    this.personagem.direcao = Direcao.DIREITA_CIMA;
//                    this.personagem.spriteAtual = this.personagem.spriteUp;
//                }
//                if (this.xPlayer < this.personagem.getX() && this.yPlayer >= this.personagem.getY()) {
//                    if (this.distanciaX >= this.distanciaY && this.yPlayer < this.personagem.getY()) {
//                        //this.personagem.direcao = Direcao.ESQUERDA_BAIXO;
//                        this.personagem.spriteAtual = this.personagem.spriteUp;
//                    }
//                    if (this.xPlayer < this.personagem.getX() && this.yPlayer < this.personagem.getY()) {
//                        // this.personagem.direcao = Direcao.ESQUERDA_CIMA;
//                        this.personagem.spriteAtual = this.personagem.spriteLeft;
//                    }
//                }

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

    public void anda() {

        int quadrante = 1;

        if (this.destMovimentoX > this.x && this.destMovimentoY < this.y) {
            quadrante = 1;
        }
        if (this.destMovimentoX < this.x && this.destMovimentoY < this.y) {
            quadrante = 2;
        }
        if (this.destMovimentoX < this.x && this.destMovimentoY > this.y) {
            quadrante = 3;
        }
        if (this.destMovimentoX > this.x && this.destMovimentoY > this.y) {
            quadrante = 4;
        }

        switch (quadrante) {
            case 1:
                this.personagem.direcao = Direcao.CIMA;
                this.personagem.spriteAtual = this.personagem.spriteUp;
                this.personagem.moveDireitaCima(this.velocidade);
                break;
            case 2:
                this.personagem.direcao = Direcao.ESQUERDA;
                this.personagem.spriteAtual = this.personagem.spriteLeft;
                this.personagem.moveEsquerdaCima(this.velocidade);
                break;
            case 3:
                this.personagem.direcao = Direcao.BAIXO;
                this.personagem.spriteAtual = this.personagem.spriteDown;
                this.personagem.moveEsquerdaBaixo(this.velocidade);
                break;
            case 4:
                this.personagem.direcao = Direcao.DIREITA;
                this.personagem.spriteAtual = this.personagem.spriteRight;
                this.personagem.moveDireitaBaixo(this.velocidade);
                break;
        }


    }

    public void sorteiaDestino() {
//        this.destMovimentoX = Util.random(this.x + this.alcancePerseguição);
//        this.destMovimentoX += this.alcancePerseguição;
//        this.destMovimentoY = Util.random(this.y + this.alcancePerseguição);
//        this.destMovimentoY += this.alcancePerseguição;
        this.destMovimentoX = Util.random(600);
        this.destMovimentoY = Util.random(400) + 250;
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
        //return this.player.getCampoDeVisao().intersects(this.personagem.getRetangulo());
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

//    public void setCampoDeVisao() {
//        switch (this.personagem.direcao) {
//            case CIMA:
//                this.campoDeVisao = new Rectangle(this.personagem.getX(), this.personagem.getY() - 800, this.personagem.spriteAtual.pegaLargura(), 800);
//                break;
//            case BAIXO:
//                this.campoDeVisao = new Rectangle(this.personagem.getX(), this.personagem.getY(), this.personagem.spriteAtual.pegaLargura(), 800);
//                break;
//            case ESQUERDA:
//                this.campoDeVisao = new Rectangle(this.personagem.getX() - 800, this.personagem.getY(), 800, this.personagem.spriteAtual.pegaAltura());
//                break;
//            case DIREITA:
//                this.campoDeVisao = new Rectangle(this.personagem.getX(), this.personagem.getY(), 800, this.personagem.spriteAtual.pegaAltura());
//                break;
//
//
//        }
//    }
    public Rectangle getCampoDeVisao() {
        return this.campoDeVisao;
    }

    public void getEstaProximoDoPlayer() {
    }

    public int getX() {
        return this.personagem.getX();
    }

    public int getY() {
        return this.personagem.getY();
    }

    public double getAngulo() {
        return angulo;
    }

    public boolean podePerseguir() {
        return (this.calculaDistanciaAtePlayer(this.player.getX(), this.player.getY()) <= this.alcancePerseguição);
    }
}
