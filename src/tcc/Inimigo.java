package tcc;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import util.Util;

public class Inimigo extends GameObject {

    public Rectangle campoDeVisao;
    public Personagem personagem;
    public Player player;
    public int vida;
    protected int velocidade = 3;
    protected Image spriteAtual;
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
    float anguloAtePlayer;
    int alcancePerseguição = 300;
    int destMovimentoX;
    int destMovimentoY;
    //diminuir minDistancia para 50, e so chamar o metodo aproxima se o ataque sorteado tiver uma distancia curta
    //so entao aproxima
    int distanciaParaAndar;
    int anguloDestino;
    int xInicial;
    int yInicial;
    int distanciaMaxAteOSpawn = 100;
    public String tipo;

    //o player estiver na linha de visao do inimigo e se estiver suficientemente perto, o inimigo atira.
    public Inimigo(Personagem personagem, Player player, int x, int y) {
        this.sorteiaDestino();
        this.atacou = false;

        this.personagem = personagem;
        this.player = player;

        this.personagem.setDirecao(Direcao.ESQUERDA);

        this.xInicial = x;
        this.yInicial = y;

        this.setX(x);
        this.setY(y);

        this.x = this.getX();
        this.y = this.getY();

        this.xPlayer = player.personagem.x;
        this.yPlayer = player.personagem.y;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        this.personagem.update(gc, game, delta);

        this.personagem.x = this.x;
        this.personagem.y = this.y;

        this.direcao = this.personagem.getDirecao();

        switch (this.direcao) {
            case BAIXO:
                this.personagem.animacaoAtual = this.personagem.animacaoDown;
                break;
            case CIMA:
                this.personagem.animacaoAtual = this.personagem.animacaoUp;
                break;
            case DIREITA:
                this.personagem.animacaoAtual = this.personagem.animacaoRight;
                break;
            case ESQUERDA:
                this.personagem.animacaoAtual = this.personagem.animacaoLeft;
                break;
            case DIREITA_BAIXO:
                this.personagem.animacaoAtual = this.personagem.animacaoRight;
            case DIREITA_CIMA:
            case ESQUERDA_BAIXO:
            case ESQUERDA_CIMA:

        }

//////        if (this.personagem.getHp() <= this.personagem.getHpInicial() * 30 / 100) {
//////            this.afasta();
//////        }
        this.player.sendoPerseguido = false;

        if (this.podePerseguir()) {
            this.player.sendoPerseguido = true;
            this.player.contRegen = 0;
            this.aproxima();
            this.ataca();
//            this.sorteiaDestino();
        } else if (this.distanciaParaAndar > 0 || this.calculaDistanciaAtePonto(this.xInicial, this.yInicial) <= this.distanciaMaxAteOSpawn) {
            this.anda();
        } else {
            this.sorteiaDestino();
        }



        this.anguloAtePlayer = (float) util.Util.calculaAngulo(destX, this.getX(), destY, this.getY());


        if (this.distanciaX < 0) {
            this.distanciaX *= (-1);
        }
        if (this.distanciaY < 0) {
            this.distanciaY *= (-1);
        }

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if (this.tipo.equals("Minion")) {
            this.personagem.render(gc, game, g);
        } else {
            this.personagem.renderZoomed(gc, game, g);
        }
    }

    public void aproxima() {
        int quadranteDoPlayer = 1;

        if (this.xPlayer > this.getX() && this.yPlayer < this.getY() && this.anguloAtePlayer <= 45.0) {
            quadranteDoPlayer = 1;
        } else if (this.xPlayer > this.getX() && this.yPlayer < this.getY() && this.anguloAtePlayer < 90.0 && this.anguloAtePlayer > 4.05) {
            quadranteDoPlayer = 2;
        } else if (this.xPlayer < this.getX() && this.yPlayer < this.getY() && this.anguloAtePlayer <= 135.0 && this.anguloAtePlayer > 90.0) {
            quadranteDoPlayer = 3;
        } else if (this.xPlayer < this.getX() && this.yPlayer < this.getY() && this.anguloAtePlayer < 180.0 && this.anguloAtePlayer > 135.0) {
            quadranteDoPlayer = 4;
        } else if (this.xPlayer < this.getX() && this.yPlayer > this.getY() && this.anguloAtePlayer <= 225.0 && this.anguloAtePlayer > 180.0) {
            quadranteDoPlayer = 5;
        } else if (this.xPlayer < this.getX() && this.yPlayer > this.getY() && this.anguloAtePlayer < 270.0 && this.anguloAtePlayer > 225.0) {
            quadranteDoPlayer = 6;
        } else if (this.xPlayer > this.getX() && this.yPlayer > this.getY() && this.anguloAtePlayer <= 315.0 && this.anguloAtePlayer > 270.0) {
            quadranteDoPlayer = 7;
        } else if (this.xPlayer > this.getX() && this.yPlayer > this.getY() && this.anguloAtePlayer < 360.0 && this.anguloAtePlayer > 315.0) {
            quadranteDoPlayer = 8;
        }

        double dx = Math.cos(Math.toRadians(this.anguloAtePlayer)) * velocidade;
        double dy = -Math.sin(Math.toRadians(this.anguloAtePlayer)) * velocidade;

        this.x += dx;
        this.y += dy;

        switch (quadranteDoPlayer) {
            case 1:
                this.personagem.direcao = Direcao.DIREITA;
                break;
            case 2:
                this.personagem.direcao = Direcao.CIMA;
                break;
            case 3:
                this.personagem.direcao = Direcao.CIMA;
                break;
            case 4:
                this.personagem.direcao = Direcao.ESQUERDA;
                break;
            case 5:
                this.personagem.direcao = Direcao.ESQUERDA;
                break;
            case 6:
                this.personagem.direcao = Direcao.BAIXO;
                break;
            case 7:
                this.personagem.direcao = Direcao.BAIXO;
                break;
            case 8:
                this.personagem.direcao = Direcao.DIREITA;
                break;
        }
    }

    public void ataca() {
        this.destX = this.player.getX();
        this.destY = this.player.getY();
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
                this.personagem.animacaoAtual = this.personagem.animacaoUp;
                this.moveDireitaCima(this.velocidade);
                break;
            case 2:
                this.personagem.direcao = Direcao.ESQUERDA;
                this.personagem.animacaoAtual = this.personagem.animacaoLeft;
                this.moveEsquerdaCima(this.velocidade);
                break;
            case 3:
                this.personagem.direcao = Direcao.BAIXO;
                this.personagem.animacaoAtual = this.personagem.animacaoDown;
                this.moveEsquerdaBaixo(this.velocidade);
                break;
            case 4:
                this.personagem.direcao = Direcao.DIREITA;
                this.personagem.animacaoAtual = this.personagem.animacaoRight;
                this.moveDireitaBaixo(this.velocidade);
                break;
        }

        this.distanciaParaAndar -= this.velocidade;

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
        this.distanciaParaAndar = Util.random(distanciaMaxAteOSpawn);
        this.anguloDestino = Util.random(360);
    }

    public double calculaDistanciaAtePonto(int xPonto, int yPonto) {
        int x1 = this.getX();
        int y1 = this.getY();
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

    public Rectangle getCampoDeVisao() {
        return this.campoDeVisao;
    }

    public void getEstaProximoDoPlayer() {
    }

    public int getX() {
        return this.personagem.x;
    }

    public int getY() {
        return this.personagem.y;
    }

    public void setX(int x) {
        this.personagem.x = x;
    }

    public void setY(int y) {
        this.personagem.y = y;
    }

    public float getAngulo() {
        return anguloAtePlayer;
    }

    public int getDestX() {
        return this.destX;
    }

    public int getDestY() {
        return this.destY;
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

    public float getHp() {
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

    public boolean podePerseguir() {
        return (this.calculaDistanciaAtePonto(this.player.getX(), this.player.getY()) <= this.alcancePerseguição);
    }
}
