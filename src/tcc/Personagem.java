package tcc;

import DAO.PokemonLiberadoDAO;
import javax.swing.JOptionPane;
import model.PokemonLiberado;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

//Tem que verificar o temColisao()
public class Personagem extends GameObject {

    int velocidade = 1; // ??
    int velocidadeInicial = 1; // ??
    //public Image spriteRight;
    //public Image spriteLeft;
    //public Image spriteUp;
    //public Image spriteDown;
    //public Image spriteAtual;
    public int cooldownAtual; //como esta o cooldown atualmente(diminui em um por step)
    public int cooldown; //cooldown que tem que esperar ate atacar de novo
    //se o cooldownAtual for menor que zero, o jogador pode atacar
    protected int id;
    protected String nome;
    protected int atk;
    protected int def;
    protected int spd;
    protected float hp;
    protected int hpInicial;
    protected int lvl ;
    protected int exp;
    protected int hpBase;
    protected int atkBase;
    protected int defBase;
    protected int spdBase;
    protected int hpLvlAnterior;
    protected int atkLvlAnterior;
    protected int defLvlAnterior;
    protected int spdLvlAnterior;
    public int larguraMapa;
    public int alturaMapa;

    
    public Animacao animacaoLeft;
    public Animacao animacaoRight;
    public Animacao animacaoUp;
    public Animacao animacaoDown;
    public Animacao animacaoAtual;
    
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
        PokemonLiberado pokemon = PokemonLiberadoDAO.getPokemon(id);
        this.exp = pokemon.getExp();

        double n = (30 / (double) this.spd) * 100;
        this.cooldown = (int) n;

        try {
            //Animacao exemplo = new Animacao(200);
            //exemplo.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Right.gif"));
            //exemplo.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Right2.gif"));
            this.animacaoLeft = new Animacao(300);
            this.animacaoLeft.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Left.png"));
            this.animacaoLeft.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Left2.png"));
            this.animacaoRight = new Animacao(300);
            this.animacaoRight.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Right.png"));
            this.animacaoRight.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Right2.png"));
            this.animacaoUp = new Animacao(300);
            this.animacaoUp.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Up.png"));
            this.animacaoUp.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Up2.png"));
            this.animacaoDown = new Animacao(300);
            this.animacaoDown.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Down.png"));
            this.animacaoDown.add(new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Down2.png"));
            this.animacaoAtual = this.animacaoDown;
//            this.spriteRight = new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Right.gif");
//            this.spriteLeft = new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Left.gif");
//            this.spriteDown = new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Down.gif");
//            this.spriteUp = new Image("resources/personagens/" + this.id + " - " + this.nome + "/" + this.nome + "_Up.gif");
//            this.spriteAtual = this.spriteDown;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        this.animacaoAtual.update();
        
        this.cooldownAtual--;

        if (this.hp > this.hpInicial) {
            this.hp = this.hpInicial;
        }
        if (this.hp < 0) {
            this.hp = 0;
        }


        if (this.x <= 0) {
            this.x = 1;
        }
        if (this.y <= 0) {
            this.y = 0;
        }
        if (this.x > this.larguraMapa) {
            this.x = this.larguraMapa;
        }
        if (this.y > this.alturaMapa) {
            this.y = this.alturaMapa;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
//        this.setLargura(this.spriteAtual.getWidth());
//        this.setAltura(this.spriteAtual.getHeight());
//        this.spriteAtual.draw(this.x, this.y);
        
        this.setLargura(this.animacaoAtual.getImage().getWidth());
        this.setAltura(this.animacaoAtual.getImage().getHeight());
        this.animacaoAtual.render(this.x, this.y, 1, false);
    }

    public void renderZoomed(GameContainer gc, StateBasedGame game, Graphics g) {
//        Image img = this.spriteAtual.getScaledCopy(2);
//        this.setLargura(img.getWidth());
//        this.setAltura(img.getHeight());
//        img.draw(this.x, this.y);
        
        this.setLargura(this.animacaoAtual.getImage().getWidth()*2);
        this.setAltura(this.animacaoAtual.getImage().getHeight()*2);
        this.animacaoAtual.render(this.x, this.y, 2, false);
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
        return new Rectangle(this.x, this.y, this.largura, this.altura);
    }

    //---- STATS ----\\
    public float getHp() {
        return this.hp;
    }

    public void setHp(float hp) {
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

    public int getHpBase() {
        return hpBase;
    }

    public void setHpBase(int hpBase) {
        this.hpBase = hpBase;
    }

    public int getAtkBase() {
        return atkBase;
    }

    public void setAtkBase(int atkBase) {
        this.atkBase = atkBase;
    }

    public int getDefBase() {
        return defBase;
    }

    public void setDefBase(int defBase) {
        this.defBase = defBase;
    }

    public int getSpdBase() {
        return spdBase;
    }

    public void setSpdBase(int spdBase) {
        this.spdBase = spdBase;
    }

    public int getHpLvlAnterior() {
        return hpLvlAnterior;
    }

    public void setHpLvlAnterior(int hpLvlAnterior) {
        this.hpLvlAnterior = hpLvlAnterior;
    }

    public int getAtkLvlAnterior() {
        return atkLvlAnterior;
    }

    public void setAtkLvlAnterior(int atkLvlAnterior) {
        this.atkLvlAnterior = atkLvlAnterior;
    }

    public int getDefLvlAnterior() {
        return defLvlAnterior;
    }

    public void setDefLvlAnterior(int defLvlAnterior) {
        this.defLvlAnterior = defLvlAnterior;
    }

    public int getSpdLvlAnterior() {
        return spdLvlAnterior;
    }

    public void setSpdLvlAnterior(int spdLvlAnterior) {
        this.spdLvlAnterior = spdLvlAnterior;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
}
