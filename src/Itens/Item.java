/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Itens;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import tcc.GameObject;

/**
 *
 * @author lucas_macedo
 */
public abstract class Item extends GameObject{
    String nome;
    int raridade;
    Efeito efeito;
    Image image;
    int forca;
    int contador;
    
    public boolean pegou = false;

    public Efeito getEfeito() {
        return efeito;
    }

    public void setEfeito(Efeito efeito) {
        this.efeito = efeito;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getRaridade() {
        return raridade;
    }

    public void setRaridade(int raridade) {
        this.raridade = raridade;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
          
    
    
    
    @Override
    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }
    
}
