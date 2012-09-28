package tcc;

import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.Imagem;
import java.awt.Point;
import util.Util;

public class Inimigo extends ObjetoComMovimento {

    public Rectangle campoDeVisao;
    public Personagem personagem;
    public Player player;
    public int vida;
    protected int velocidade = 4;
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
    int destino;
    int anguloDestino;
    int xInicial;
    int yInicial;
    int distanciaMaxAteOSpawn = 25;

    //o player estiver na linha de visao do inimigo e se estiver suficientemente perto, o inimigo atira.
    public Inimigo(Personagem personagem, Player player, int x, int y) {
        this.sorteiaDestino();
        this.atacou = false;

        this.personagem = personagem;
        this.player = player;

        this.personagem.setDirecao(Direcao.ESQUERDA);

        this.xInicial = x;
        this.yInicial = y;

        this.personagem.setX(x);
        this.personagem.setY(y);

        this.x = this.personagem.getX();
        this.y = this.personagem.getY();

        this.xPlayer = player.getPersonagem().getX();
        this.yPlayer = player.getPersonagem().getY();

        this.sorteiaDestino();


    }

    public void step(long timeElapsed) {

//        this.x = this.personagem.getX() - this.player.offsetx;
//        this.y = this.personagem.getY() - this.player.offsety;

////////        // Cima e Baixo
////////        if (player.apertouCima) {
////////            this.personagem.moveBaixo(10);
////////        }
////////        if (player.apertouBaixo) {
////////            this.personagem.moveCima(10);
////////        }
////////
////////
////////        // Direção para a direita
////////        if (player.apertouDireita) {
////////            this.personagem.moveEsquerda(10);
////////        }
////////        if (player.apertouDireitaBaixo) {
////////            this.personagem.moveEsquerdaCima(10);
////////        }
////////        if (player.apertouDireitaCima) {
////////            this.personagem.moveEsquerdaBaixo(10);
////////        }
////////
////////        // Direção para a esquerda
////////        if (player.apertouEsquerda) {
////////            this.personagem.moveDireita(10);
////////        }
////////        if (player.apertouEsquerdaBaixo) {
////////            this.personagem.moveDireitaCima(10);
////////        }
////////        if (player.apertouEsquerdaCima) {
////////            this.personagem.moveDireitaBaixo(10);
////////        }



        //this.personagem.setY(this.personagem.getY() + this.player.offsety);

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

            this.ataca();//metodo antigo, provavelmente nao sera mais usado
            this.sorteiaDestino();//nao sei por que tem isso aqui '-'
        } else // if (this.x != this.destMovimentoX && this.y != this.destMovimentoY) {
        //if (!(this.destMovimentoX == 0 && this.destMovimentoY == 0)) {
        if (this.destino != 0 || this.calculaDistanciaAtePonto(this.xInicial, this.yInicial) <= this.distanciaMaxAteOSpawn) {
            this.anda();
        } else {
            this.sorteiaDestino();
        }
        


        if (this.estado == EstadoInimigo.PERSEGUINDO) {
            // this.aproxima();
//            this.ataca();
            this.velocidade = 4;
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
        g.drawString("" + this.player.offsetx, 500, 500);
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
        //    this.angulo = util.Util.calculaAngulo(destX, this.personagem.getX(), destY, this.personagem.getY());
        this.angulo = util.Util.calculaAngulo(this.player.personagem.getX(), this.personagem.getX(), this.player.personagem.getY(), this.personagem.getY());

        if (this.xPlayer > this.personagem.getX() && this.yPlayer < this.personagem.getY() && this.angulo <= 45.0) {
            quadranteDoPlayer = 1;
        }
        if (this.xPlayer > this.personagem.getX() && this.yPlayer < this.personagem.getY() && this.angulo <= 90.0 && this.angulo > 4.05) {
            quadranteDoPlayer = 2;
        }
        if (this.xPlayer < this.personagem.getX() && this.yPlayer < this.personagem.getY() && this.angulo <= 135.0 && this.angulo > 90.0) {
            quadranteDoPlayer = 3;
        }
        if (this.xPlayer < this.personagem.getX() && this.yPlayer < this.personagem.getY() && this.angulo <= 180.0 && this.angulo > 135.0) {
            quadranteDoPlayer = 4;
        }
        if (this.xPlayer < this.personagem.getX() && this.yPlayer > this.personagem.getY() && this.angulo <= 225.0 && this.angulo > 180.0) {
            quadranteDoPlayer = 5;
        }
        if (this.xPlayer < this.personagem.getX() && this.yPlayer > this.personagem.getY() && this.angulo <= 270.0 && this.angulo > 225.0) {
            quadranteDoPlayer = 6;
        }
        if (this.xPlayer > this.personagem.getX() && this.yPlayer > this.personagem.getY() && this.angulo <= 315.0 && this.angulo > 270.0) {
            quadranteDoPlayer = 7;
        }
        if (this.xPlayer > this.personagem.getX() && this.yPlayer > this.personagem.getY() && this.angulo <= 360.0 && this.angulo > 315.0) {
            quadranteDoPlayer = 8;
        }




        switch (quadranteDoPlayer) {
            case 1:
                this.personagem.direcao = Direcao.DIREITA;
                this.personagem.moveDireitaCima(this.velocidade);
                break;
            case 2:
                this.personagem.direcao = Direcao.CIMA;
                this.personagem.moveDireitaCima(this.velocidade);
                break;
            case 3:
                this.personagem.direcao = Direcao.CIMA;
                this.personagem.moveEsquerdaCima(this.velocidade);
                break;
            case 4:
                this.personagem.direcao = Direcao.ESQUERDA;
                this.personagem.moveEsquerdaCima(this.velocidade);
                break;
            case 5:
                this.personagem.direcao = Direcao.ESQUERDA;
                this.personagem.moveEsquerdaBaixo(this.velocidade);
                break;
            case 6:
                this.personagem.direcao = Direcao.BAIXO;
                this.personagem.moveEsquerdaBaixo(this.velocidade);
                break;
            case 7:
                this.personagem.direcao = Direcao.BAIXO;
                this.personagem.moveDireitaBaixo(this.velocidade);
                break;
            case 8:
                this.personagem.direcao = Direcao.DIREITA;
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

//////        if (this.destMovimentoX > 0 && this.destMovimentoY <= 0) {
//////            quadrante = 1;
//////        }
//////        if (this.destMovimentoX <= 0 && this.destMovimentoY <= 0) {
//////            quadrante = 2;
//////        }
//////        if (this.destMovimentoX <= 0 && this.destMovimentoY > 0) {
//////            quadrante = 3;
//////        }
//////        if (this.destMovimentoX > 0 && this.destMovimentoY > 0) {
//////            quadrante = 4;
//////        }

        if (this.anguloDestino < 90) {
            quadrante = 1;
        }
        if (this.anguloDestino < 180 && this.anguloDestino >= 90) {
            quadrante = 2;
        }
        if (this.anguloDestino < 270 && this.anguloDestino >= 180) {
            quadrante = 3;
        }
        if (this.anguloDestino < 360 && this.anguloDestino >= 270) {
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

        this.destino--;

//////        if (this.destMovimentoX < 0) {
//////            this.destMovimentoX++;
//////        } else if (this.destMovimentoX > 0) {
//////            this.destMovimentoX--;
//////        }
//////        if (this.destMovimentoY < 0) {
//////            this.destMovimentoY++;
//////        } else if (this.destMovimentoY > 0) {
//////            this.destMovimentoY--;
//////        }

    }

    public void sorteiaDestino() {
//        this.destMovimentoX = Util.random(this.x + this.alcancePerseguição);
//        this.destMovimentoX += this.alcancePerseguição;
//        this.destMovimentoY = Util.random(this.y + this.alcancePerseguição);
//        this.destMovimentoY += this.alcancePerseguição;


        //this.destMovimentoX = Util.random(200) - 100;
        //this.destMovimentoY = Util.random(200) - 100;
        this.destino = Util.random(50);
        this.anguloDestino = Util.random(360);
    }

    public double calculaDistanciaAtePonto(int xPonto, int yPonto) {
        int x1 = this.personagem.getX();
        int y1 = this.personagem.getY();
        int x2 = xPonto;
        int y2 = yPonto;

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
        return (this.calculaDistanciaAtePonto(this.player.getX(), this.player.getY()) <= this.alcancePerseguição);
    }
}
