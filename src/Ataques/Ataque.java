/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import tcc.GameObject;
import tcc.Personagem;



/**
 *
 * @author Maike
 */
public abstract class Ataque extends GameObject {

    int velocidade = 15; //Determina a velocidade em que o personagem anda
    public boolean desativado;
    public boolean acertou = false;
    int dano;// dano final
    int danoBruto;// dano inicial 
    int xInicial; // Possição inicial para 
    int yInicial;
    // Imagem imagem;
    public Image imagem;
    public SpriteSheet sprite;
    Personagem personagem; // personagem que ataca
    float angulo;
    int destX;
    int destY;
    double deltaX, deltaY, dx, dy;
    int contadorDano; //contador de tempo para o dano que aparece na tela
    
    public void setDano(int n) {
        this.dano = n;
    }

    public void setDanoBruto(int n) {
        this.danoBruto = n;
    }

    public int getDano() {
        return this.dano;
    }

    public int getDanoBruto() {
        return this.danoBruto;
    }

    public Shape getShape() {
        if (this.imagem != null) {
            double degree = this.angulo; //desired degree
            Shape rect = new Rectangle((int)this.x, (int)this.y, this.imagem.getWidth(), this.imagem.getHeight()); //creating the rectangle you want to rotate
            AffineTransform transform = new AffineTransform();
            //rotate or do other things with the rectangle (shear, translate, scale and so on)
            //transform.rotate(Math.toRadians(-degree), this.x + this.imagem.pegaLargura() /2, this.y + this.imagem.pegaAltura() /2); //rotating in central axis
            transform.rotate(Math.toRadians(-degree), this.x, this.y); //rotating in central axis
            //rect receiving the rectangle after rotate
            rect = transform.createTransformedShape(rect);

            return rect;

        } else {
            double degree = this.angulo; //desired degree
            Shape rect = new Rectangle((int)this.x, (int)this.y, this.sprite.getWidth(), this.sprite.getHeight()); //creating the rectangle you want to rotate
            AffineTransform transform = new AffineTransform();
            //rotate or do other things with the rectangle (shear, translate, scale and so on)
            //transform.rotate(Math.toRadians(-degree), this.x + this.imagem.pegaLargura() /2, this.y + this.imagem.pegaAltura() /2); //rotating in central axis
            transform.rotate(Math.toRadians(-degree), this.x, this.y); //rotating in central axis
            //rect receiving the rectangle after rotate
            rect = transform.createTransformedShape(rect);

            return rect;

        }
    }
    
    public double getAngulo() {
        return angulo;
    }

    public void setAngulo(float angulo) {
        this.angulo = angulo;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public float getDestX() {
        return destX;
    }

    public void setDestX(int destX) {
        this.destX = destX;
    }

    public float getDestY() {
        return destY;
    }

    public void setDestY(int destY) {
        this.destY = destY;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public float getxInicial() {
        return xInicial;
    }

    public void setxInicial(int xInicial) {
        this.xInicial = xInicial;
    }

    public float getyInicial() {
        return yInicial;
    }

    public void setyInicial(int yInicial) {
        this.yInicial = yInicial;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }

    public void setContador(int num){
        this.contadorDano = num;
    }
    
    public int getContador() {
        return contadorDano;
    }
    

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }

    public boolean getAcertou() {
        return this.acertou;
    }

    public void desativado() {
        this.desativado = true;
    }
}
