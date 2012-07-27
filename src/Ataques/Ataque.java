/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import Personagens.Personagem;
import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.Imagem;
import javaPlayExtras.AudioPlayer;
import tcc.ObjetoComMovimento;




/**
 *
 * @author Maike
 */
public abstract class Ataque extends ObjetoComMovimento{
 
    int velocidade;
    boolean desativado;
    int dano;
    int xInicial;
    int yInicial;
    Imagem imagem;
    Imagem Vazio;
    Personagem personagem;
    
    public void step(long timeElapsed){
        
    }
    
    public void draw(Graphics g){
        
    }
    
    public void setDano(int n) {
        this.dano = n;
    }

    public int getDano() {
        return this.dano;
    }
    
     public Rectangle getRetangulo(){
        return new Rectangle(this.x, this.y, this.imagem.pegaLargura(), this.imagem.pegaAltura());
    }
    
    public boolean temColisao(Rectangle retangulo){
        if(this.desativado){
            return false;
        }
        
        if(this.getRetangulo().intersects(retangulo)){
            AudioPlayer.play("resources/sounds/Sound 2.wav");
            this.desativado = true;
            return true;            
        } else {
            return false;
        }
    }
    
}
