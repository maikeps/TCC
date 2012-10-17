/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Itens;

import org.newdawn.slick.Image;
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

    public Efeito getEfeito() {
        return efeito;
    }

    public void setEfeito(Efeito efeito) {
        this.efeito = efeito;
    }

    public Image getImage() {
        return image;
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
          
    
}
