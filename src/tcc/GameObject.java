/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc;

import java.awt.Point;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author maike_p_santos
 */
public abstract class GameObject {

    protected Direcao direcao = Direcao.CIMA;
    
    protected int x;
    protected int y;
    
    protected int altura;
    protected int largura;

    public abstract void update(GameContainer gc, StateBasedGame game, int delta);

    public abstract void render(GameContainer gc, StateBasedGame game, Graphics g);

    public void moveDireita(int valor) {
        direcao = Direcao.DIREITA;
        this.x += valor;
    }   

    public void moveEsquerda(int valor) {
        direcao = Direcao.ESQUERDA;
        this.x -= valor;
    }

    public void moveCima(int valor) {
        direcao = Direcao.CIMA;
        this.y -= valor;
    }

    public void moveBaixo(int valor) {
        direcao = Direcao.BAIXO;
        this.y += valor;
    }

    private int calculaDistanciaDiagonal(int valor){
        return (int)Math.floor( Math.sqrt( Math.pow(valor, 2) / 2));
    }
    public void moveDireitaCima(int valor) {
        this.direcao = Direcao.DIREITA_CIMA;
        int distancia = this.calculaDistanciaDiagonal(valor);
        this.x += distancia;
        this.y -= distancia;
    }

    public void moveDireitaBaixo(int valor) {
        this.direcao = Direcao.DIREITA_BAIXO;
        int distancia = this.calculaDistanciaDiagonal(valor);
        this.x += distancia;
        this.y += distancia;
    }

    public void moveEsquerdaCima(int valor) {
        this.direcao = Direcao.ESQUERDA_CIMA;
        int distancia = this.calculaDistanciaDiagonal(valor);
        this.x -= distancia;
        this.y -= distancia;
    }

    public void moveEsquerdaBaixo(int valor) {
        this.direcao = Direcao.ESQUERDA_BAIXO;
        int distancia = this.calculaDistanciaDiagonal(valor);
        this.x -= distancia;
        this.y += distancia;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }
    
    public Point getPontoCentral(){
        Point ponto = new Point();
        ponto.x = ( this.getX()/2 + this.getMaxX()/2 );
        ponto.y = ( this.getY()/2 + this.getMaxY()/2 );
        return ponto;
    }

    public Point getPontoMin(){
        return new Point(this.x, this.y);
    }

    public Point getPontoMax(){
        //O uso do -1 é para melhorar o cálculo da colisão
        return new Point(this.getMaxX() - 1, this.getMaxY() - 1);
    }
    
    public int getX() {
        return x;
    }

    public int getMaxX(){
        return (this.x + this.largura -1 );
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getMaxY(){
        return (this.y+this.altura -1);
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, this.largura, this.altura);
    }

    public boolean temColisao(Rectangle retangulo) {
        return this.getRetangulo().intersects(retangulo);
    }

    public boolean temColisao(GameObject obj) {
        return this.getRetangulo().intersects(obj.getRetangulo());
    }
}
