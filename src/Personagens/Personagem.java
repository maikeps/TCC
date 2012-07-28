package Personagens;

import java.awt.Graphics;
import java.awt.Rectangle;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import tcc.Ataques;
import tcc.Direcao;
import tcc.ObjetoComMovimento;


//Tem que verificar o temColisao()

public abstract class Personagem extends ObjetoComMovimento {
    
    String nome;
    int hpAtual;
    int velocidade = 1; // ??
    int velocidadeInicial = 1; // ??
    public Imagem spriteRight; 
    public Imagem spriteLeft;
    public Imagem spriteUp;
    public Imagem spriteDown;
    public Imagem spriteAtual;
    public Direcao direcao;
        
    // stats
    int atk;
    int def;
    int spd;
    int hp;
    
    
    int cooldownAtual; //como esta o cooldown atualmente(diminui em um por step)
    int cooldown = 50; //cooldown que tem que esperar ate atacar de novo
    //se o cooldownAtual for menor que zero, o jogador pode atacar
    Ataques ataque;


    public Personagem() {

        //ver se pode tirar isso
//        this.hp = 200;
  //      this.hpAtual = 200;
        try {
            this.spriteRight = new Imagem("resources/personagens/Blastoise/Blastoise_Right.gif");
            this.spriteLeft = new Imagem("resources/personagens/Blastoise/Blastoise_Left.gif");
            this.spriteDown = new Imagem("resources/personagens/Blastoise/Blastoise_Down.gif");
            this.spriteUp = new Imagem("resources/personagens/Blastoise/Blastoise_Up.gif");
            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não ecnontrado: " + ex.getMessage());
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
            this.y = 194;
            this.velocidade = this.velocidadeInicial;
        }
        if (this.tocaParedeBaixo()) {
            this.y = 694 - this.spriteAtual.pegaAltura();
            this.velocidade = this.velocidadeInicial;
        }


    }

    public void draw(Graphics g) {
        this.spriteAtual.draw(g, this.x, this.y);
    }

    public boolean tocaParedeEsquerda() {
        return (this.x <= 4);
    }

    public boolean tocaParedeDireita() {
        return (this.x >= 796 - this.spriteAtual.pegaLargura());
    }

    public boolean tocaParedeBaixo() {
        return (this.y > 695 - this.spriteAtual.pegaAltura());
    }

    public boolean tocaParedeCima() {
        return (this.y < 190);
    }

    //retorna o retangulo invisivel ocupado pelo personagem
    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, this.spriteAtual.pegaLargura(), this.spriteAtual.pegaAltura());
    }

    //verifica se o retangulo do personagem tem colisao com o do inimigo
    public boolean temColisao(Personagem player) {
        return this.getRetangulo().intersects(player.getRetangulo());
    }

    //direcao que o personagem esta virado
    public Direcao getDirecao() {
        return this.direcao;
    }

    public void setDirecao(Direcao direcao) {
        this.direcao = direcao;
    }

    public void setSpriteAtual(Imagem sprite) {
        this.spriteAtual = sprite;

    }

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
    public int getcooldownAtual() {
        return this.cooldownAtual;
    }
    
    public void setCooldownAtual(){
        this.cooldownAtual = this.cooldown;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

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
    
}
