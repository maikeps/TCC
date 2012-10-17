package tcc;

import javax.swing.JOptionPane;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

//Tem que verificar o temColisao()
public class Personagem extends GameObject {

    int velocidade = 1; // ??
    int velocidadeInicial = 1; // ??
    public Image spriteRight;
    public Image spriteLeft;
    public Image spriteUp;
    public Image spriteDown;
    public Image spriteAtual;
    public int cooldownAtual; //como esta o cooldown atualmente(diminui em um por step)
    public int cooldown; //cooldown que tem que esperar ate atacar de novo
    //se o cooldownAtual for menor que zero, o jogador pode atacar
    protected int id;
    protected String nome;
    protected int atk;
    protected int def;
    protected int spd;
    protected int hp;
    protected int hpInicial;
    protected int lvl;

    public Personagem() {
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

        double n = (30 / (double) this.spd) * 100;
        this.cooldown = (int) n;

        try {
            this.spriteRight = new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Right.gif");
            this.spriteLeft = new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Left.gif");
            this.spriteDown = new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Down.gif");
            this.spriteUp = new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Up.gif");
            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        this.cooldownAtual--;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        this.spriteAtual.draw(this.x, this.y);
    }

    public Direcao getDirecao() {
        return this.direcao;
    }

    public void setDirecao(Direcao direcao) {
        this.direcao = direcao;
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

    public void resetPosicao(int x, int y) {
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

    public void setCooldownAtual() {
        this.cooldownAtual = this.cooldown;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void setCooldown() {
        this.cooldown = 30 / this.spd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public Rectangle getRetangulo() {
        return new Rectangle(this.x, this.y, this.spriteAtual.getWidth(), this.spriteAtual.getHeight());
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

    public int getAtk() {
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
