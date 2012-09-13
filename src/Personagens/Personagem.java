package Personagens;

import Ataques.Ataque;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import pixelPerfect.GameObjectImagePixelPerfect;
import tcc.Ataques;
import tcc.Direcao;
import tcc.ObjetoComMovimento;


//Tem que verificar o temColisao()

public class Personagem extends ObjetoComMovimento {
    
        int velocidade = 1; // ??
    int velocidadeInicial = 1; // ??
    
    /* Colisão simple
    public Imagem spriteRight; 
    public Imagem spriteLeft;
    public Imagem spriteUp;
    public Imagem spriteDown;
    public Imagem spriteAtual;*/
    public Direcao direcao;

    //Com colisão pixel perfct 
   public Imagem  spriteRight; 
   public Imagem  spriteLeft;
   public Imagem  spriteUp;
   public Imagem  spriteDown;
   public Imagem  spriteAtual;
    
    public int cooldownAtual; //como esta o cooldown atualmente(diminui em um por step)
    public int cooldown; //cooldown que tem que esperar ate atacar de novo
    //se o cooldownAtual for menor que zero, o jogador pode atacar
    Ataques ataque;
    
    
    
    
    protected int id;
    protected String nome;
    protected int atk;
    protected int def;
    protected int spd;
    protected int hp;
    protected int hpInicial;
    protected int lvl;

    public Personagem(){
        
    }

    public Personagem(int id, String nome, int atk, int def, int spd, int hp, int lvl) {

        this.id = id;
        this.nome = nome;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        this.hp = hp;
        this.hpInicial = hp;
        this.lvl = lvl;
        
        double n = (30/(double)this.spd)*100;
        this.cooldown = (int)n;
        
       /*  Colisão simples
        try {
            this.spriteRight = new Imagem("resources/personagens/"+this.id+" - "+this.nome+"/"+this.nome+"_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/"+this.id+" - "+this.nome+"/"+this.nome+"_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/"+this.id+" - "+this.nome+"/"+this.nome+"_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/"+this.id+" - "+this.nome+"/"+this.nome+"_Up.gif");
            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }
        */

         try {
            this.spriteRight = new Imagem("resources/personagens/"+this.id+" - "+this.nome+"/"+this.nome+"_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/"+this.id+" - "+this.nome+"/"+this.nome+"_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/"+this.id+" - "+this.nome+"/"+this.nome+"_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/"+this.id+" - "+this.nome+"/"+this.nome+"_Up.gif");
            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }
        
    }

    public void step(long timeElapsed) {
        
          this.cooldownAtual --;

        if (this.tocaParedeEsquerda()) {
            this.x = 5;
            this.velocidade = this.velocidadeInicial;
        }
        if (this.tocaParedeDireita()) {
            this.x = 795 - this.spriteAtual.pegaLargura();
            this.velocidade = this.velocidadeInicial;
        }
        if (this.tocaParedeCima()) {
            this.y = 5;
            this.velocidade = this.velocidadeInicial;
        }
        if (this.tocaParedeBaixo()) {
            this.y = 694 - this.spriteAtual.pegaAltura();
            this.velocidade = this.velocidadeInicial;
        }

    }

//    public void draw(Graphics g) {
//        this.spriteAtual.draw(g, this.x, this.y);
//    }

      public void draw(Graphics g) {
        this.spriteAtual.draw(g, this.x, this.y);
    }

    public boolean tocaParedeEsquerda() {
        return (this.x < 0);
    }

    public boolean tocaParedeDireita() {
        return (this.x >= 796 - this.spriteAtual.pegaLargura());
    }

    public boolean tocaParedeBaixo() {
        return (this.y > 695 - this.spriteAtual.pegaAltura());
    }

    public boolean tocaParedeCima() {
        return (this.y < 0);
    }

    //retorna o retangulo invisivel ocupado pelo personagem
  //  public Rectangle getRetangulo() {
    //    return new Rectangle(this.x, this.y, this.spriteAtual.pegaLargura(), this.spriteAtual.pegaAltura());
   // }

    //verifica se o retangulo do personagem tem colisao com o do inimigo
////////    public boolean temColisao(Personagem player) {
////////        return this.getRetangulo().intersects(player.getRetangulo());
////////    }
    
//    public boolean temColisao(Ataque ataque){
//        Point colisao = this.spriteAtual.temColisao(ataque.imagem);
//        if(colisao != null){
//            return true;
//        }
//    }

    //direcao que o personagem esta virado
    public Direcao getDirecao() {
        return this.direcao;
    }

    public void setDirecao(Direcao direcao) {
        this.direcao = direcao;
    }

   // public void setSpriteAtual(Imagem sprite) {
     //   this.spriteAtual = sprite;

    //}

    public boolean estaMorto() {
        return (this.hp <= 0);
    }

    public void perdeHp(int hp) {
        this.hp -= hp;
    }
    
    public void ganhaHp(int hp) {
        this.hp += hp;
    }

    public void resetPosicao(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public boolean podeAtirar() {
        return (this.cooldownAtual <= 0);
    }

    //retorna o cooldownAtual(quanto falta para poder atacar novamente)
    public int getCooldownAtual() {
        return this.cooldownAtual;
    }
    
    public void setCooldownAtual(){
        this.cooldownAtual = this.cooldown;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void setCooldown() {
        this.cooldown = 30/this.spd;
    }
    
//    public void setCooldown(int cooldown) {
//        this.cooldown = cooldown;
//    }

    // seta o ataque que sera usado pelo pokemon
    public void setAtaque(Ataques ataque){
        this.ataque = ataque;
    }

    // retorna qual ataque sera usado pelo pokemon
    public Ataques getAtaque() {
        return ataque;
    }
    
    //para saber se jogador tocou uma das bordas do cenario
    public boolean tocaParede(){
        return(this.tocaParedeBaixo() || this.tocaParedeCima() || this.tocaParedeDireita() || this.tocaParedeEsquerda());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    

    //---- STATS ----\\
    
    public int getHp() {
        return this.hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getHpInicial() {
        return hpInicial;
    }
    public void setHpInicial(int hpInicial) {
        this.hpInicial = hpInicial;
    }
    public int getAtk(){
        return this.atk;
    }
    public void setAtk(int atk) {
        this.atk = atk;
    }
    public int getDef() {
        return def;
    }
    public void setDef(int def) {
        this.def = def;
    }
    public int getSpd() {
        return spd;
    }
    public void setSpd(int spd) {
        this.spd = spd;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getLvl() {
        return lvl;
    }
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    
    
    
}
