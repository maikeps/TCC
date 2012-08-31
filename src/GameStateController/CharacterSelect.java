/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import DAO.PokemonDAO;
import DAO.PokemonDerrotadoDAO;
import DAO.PokemonLiberadoDAO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.util.ArrayList;
import javaPlay2.GameEngine;
import javaPlay2.GameStateController;
import javaPlay2.Imagem;
import javaPlay2.Keyboard;
import javaPlay2.Keys;
import javax.swing.JOptionPane;
import model.Pokemon;
import model.PokemonDerrotado;
import model.PokemonLiberado;
import util.Util;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.FileInputStream;

/**
 *
 * @author maike_p_santos
 */
public class CharacterSelect implements GameStateController {

    private Imagem Cenario; //imagem de fundo
    private String player1; //qual personagem o player escolheu
    private String inimigo; //qual personagem o inimigo escolheu
    private int xSelecionado; //qual pokemon esta selecionado na horizontal(de 1 ate 9)
    private int ySelecionado; //qual pokemon esta selecionado na vertical(de 1 ate 3)
    private int xDraw; //qual o x do quadrado de selecao
    private int yDraw; //qual o y do quadrado de selecao
    ArrayList<PokemonLiberado> listaDePokemonLiberado; //lista de pokemons liberados
    ArrayList<Pokemon> listaDePokemon; //lista de pokemon(todos)
    ArrayList<String> nomes; //lista de nomes dos pokemons
    Imagem pokemonImage; //imagem do pokemon a desenhar
    int pokemonSelecionado = 0; //qual pokemon esta selecionado(na lista o item zero é o primeiro pokemon)
    private Imagem imgGrande; //imagem redimensionada do pokemon
    int linha = 1; //em qual linha o quadrado de selecao esta atualmente
    int numLinhas; //numero de linhas de pokemon

    public CharacterSelect(String p1) {
        this.ySelecionado = 1;
        this.player1 = p1;
        this.xDraw = 70;
        this.yDraw = 70 + 350;
    }

    @Override
    public void load() {

        try {
            this.Cenario = new Imagem("resources/Cenario/493pokemons2 preto.png");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }

    }

    @Override
    public void step(long timeElapsed) {
        this.teclas();
    }

    @Override
    public void draw(Graphics g) {
        //cria a fonte
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("resources/fontes/PressStart2P.ttf"));
            f = f.deriveFont(16f);
            g.setFont(f);
        } catch (FontFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            System.exit(1);
        }

        //pinta um retangulo preto
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 700);
        //desenha a imagem de fundo
        this.Cenario.draw(g, 0, 0);

        g.setColor(Color.white);
        g.drawString(" P1, escolha o personagem", 325, 420);

        //desenha os quadrados por baixo de cada pokemon
        this.desenhaFundo(g);
        g.drawRect(this.xDraw, this.yDraw, 80, 80);
        g.drawRect(this.xDraw + 1, this.yDraw + 1, 79, 79);
        g.drawRect(this.xDraw + 2, this.yDraw + 2, 78, 78);
        g.drawRect(this.xDraw + 3, this.yDraw + 3, 77, 77);
        g.drawRect(this.xDraw + 4, this.yDraw + 4, 76, 76);

        //desenha imagens dos pokemons
        this.desenhaImagens(g);
        //desenha stats dos pokemon
        this.desenhaStats(g);
    }

    @Override
    public void unload() {
    }

    @Override
    public void start() {
        this.sorteiaInimigo();

        this.listaDePokemon = PokemonDAO.getLista();
        this.listaDePokemonLiberado = PokemonLiberadoDAO.getListaPokemon(1);

        this.nomes = new ArrayList<String>();

        for (Pokemon p : this.listaDePokemon) {
            this.nomes.add(p.getNome());
        }

        this.numLinhas = ((this.listaDePokemon.size() + 1) / 9) + 1;
        System.out.println(numLinhas);

    }

    @Override
    public void stop() {
    }

    public String getPlayer1() {
        return this.player1;
    }
    
    public void setPlayer1(String player1){
        this.player1 = player1;
    }

    public String getInimigo() {
        return this.inimigo;
    }
    
    public void setInimigo(String inimigo){
        this.inimigo = inimigo;
    }

    public int getYSelecionado() {
        return this.ySelecionado;
    }

    public int getXDraw() {
        return this.xDraw;
    }

    public void sorteiaInimigo() {
        this.nomes = new ArrayList<String>();
        this.listaDePokemon = PokemonDAO.getLista();
        this.listaDePokemonLiberado = PokemonLiberadoDAO.getListaPokemon(1);
        for (Pokemon p : this.listaDePokemon) {
            this.nomes.add(p.getNome());
        }
        int n = Util.random(this.nomes.size() + 1);
        while (n >= this.nomes.size()) {
            n = Util.random(this.nomes.size() + 1);
        }
        //System.out.println("inimigo no charSelect: "+this.nomes.get(n));
        this.inimigo = this.nomes.get(n);



    }

    public void iniciaJogo() {
        GameEngine.getInstance().setNextGameStateController(3);
    }

    public void desenhaStats(Graphics g) {

        int i = this.pokemonSelecionado;

        //nome do pokemon
        g.drawString(i + 1 + " - " + this.nomes.get(i) + "", 350, 350);
        Pokemon p = PokemonDAO.getPokemonPeloNome(this.nomes.get(i));
        g.setColor(Color.white);
        //desenha barras de stats - HP, ATK, DEF, SPD
        g.fillRect(100, 160, p.getHpBase(), 20);
        g.fillRect(100, 185, p.getAtkBase(), 20);
        g.fillRect(100, 210, p.getDefBase(), 20);
        g.fillRect(100, 235, p.getSpdBase(), 20);
        g.setColor(Color.black);
        //desenha os numeros dos stats
        g.drawString("HP: " + p.getHpBase(), 100, 175);
        g.drawString("ATK: " + p.getAtkBase(), 100, 200);
        g.drawString("DEF: " + p.getDefBase(), 100, 225);
        g.drawString("SPD: " + p.getSpdBase(), 100, 250);

        //informacoes sobre o pokemon
        g.setColor(Color.white);
        PokemonLiberado pl = PokemonLiberadoDAO.getPokemon(this.pokemonSelecionado + 1);
        g.drawString("Kills: " + pl.getInimigosDerrotados(), 500, 175);
        g.drawString("Deaths: " + pl.getVezesDerrotasParaNPC(), 500, 200);
        g.drawString("Dano Total: " + pl.getTotalDanoCausado(), 500, 225);
        g.drawString("Medals: " + pl.getVezesQueZerouOJogo(), 500, 250);



        //desenha a progress bar
        //nao desenha na primeira linha por que a raridade dos 9 primeiros pokemons é zero
        //isso quer dizer que nao tem como eles aparecerem como inimigo.
        
        
        Pokemon poke = PokemonDAO.getPokemon(this.pokemonSelecionado + 1); //pega o pokemon que esta selecionado
        PokemonLiberado pokeliberado = PokemonLiberadoDAO.getPokemon(this.pokemonSelecionado + 1);
        
     if (pokeliberado.getNome() == null){ 
        g.setColor(Color.green);
        g.fillRect(400, 300, poke.getRaridade(), 29); //desenha a barra de baixo, quando essa encher, o pokemon é liberado
        PokemonDerrotado pokeDerrotado = PokemonDerrotadoDAO.getPokemon(this.pokemonSelecionado + 1); //ve quantas vezes o pokemon foi derrotado
        g.setColor(Color.white);
        g.fillRect(402, 302, pokeDerrotado.getVezesDerrotado(), 25); //desenha a barra de cima que mostra quantas vezes o pokemon foi derrotado
        g.setColor(Color.black);
        g.drawString(pokeDerrotado.getVezesDerrotado() + "/" + poke.getRaridade(), 410, 320); //escreve os numeros
        g.setColor(Color.white);

      }
    }

    public void desenhaImagens(Graphics g) {

        int x1 = 0; //x da imagem que sera desenhada(desenha todos os pokemons como nao-liberados)
        int y1 = 350; //y da imagem que sera desenhada(desenha todos os pokemons como nao-liberados)
        int cont1 = 0; //contador que verifica se já foram desenhados 9 pokemons na linha(nao-liberados)
        int cont2 = 1; //contador que verifica s já foram desenhados 9 pokemons na linha(liberados)
        int x2 = 0; //x da imagem que sera desenhada(desenha todos os pokemons liberados)
        int y2 = 350; //y da imagem que sera desenhada(desenha todos os pokemons liberados)


        //se a linha selecionada pelo retangulo de selecao for maior que 3(abaixo da terceira)
        //modifica o y para fazer com que as imagens vão para cima
        if (this.linha > 3) {
            y1 -= 75 * (this.linha - 3);
            y2 -= 75 * (this.linha - 3);
        }

        //desenha todos os pokemons
        for (Pokemon p : this.listaDePokemon) {
            cont1++; //aumenta o cont
            if (cont1 > 9) {
                //se o cont for maior que 9, quer dizer que ja desenhou 9 pokemons nessa linha
                //entao passa para a proxima
                cont1 = 1;
                x1 = 0;
                y1 += 75;
            }

            String nome = p.getNome(); //pega o nome do pokemon a desenhar

            //cria a imagem do pokemon
            try {
                this.pokemonImage = new Imagem("resources/personagens/" + p.getId() + " - " + nome + "/" + nome + "_Locked.gif");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
                System.exit(1);
            }

            //se o y do pokemon estiver dentro do especificado
            //quer dizer que ele esta em uma das tres linhas
            //entao desenha
            if (!(75 + y1 <= 350 || 75 + y1 > 575)) {
                this.pokemonImage.draw(g, 75 + x1, 75 + y1);
            }

            x1 += 75; //aumenta o x para desenhar o proximo pokemon 75px a direita


        }

        //desenha os pokemons liberados
        for (PokemonLiberado pl : this.listaDePokemonLiberado) {

            //se o numero do pokemon for maior que 9*cont2
            //por exemplo: se o numero for 15
            //15 é maior que 9*cont2(inicialmente = 1)
            //ou seja, 15 é maior que 9
            //entao aumenta em um o cont2, zera o x2 e aumenta em 75 o y2
            //isso quer dizer que nao tem mais pokemon liberado para desenhar nessa linha
            if (pl.getIdPokemon() > 9 * cont2) {
                cont2++;
                x2 = 0;
                y2 += 75;
            }

            //pega o nome do pokemon
            String nome = pl.getNome();

            try { //cria a imagem
                this.pokemonImage = new Imagem("resources/personagens/" + pl.getIdPokemon() + " - " + nome + "/" + nome + "_Down.gif");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
                System.exit(1);
            }

            //se o y do pokemon estiver dentro do especificado
            //quer dizer que ele esta em uma das tres linhas
            //entao desenha
            if (!(75 + y2 <= 350 || 75 + y2 > 575)) {
                this.pokemonImage.draw(g, 75 + x2 * (pl.getIdPokemon() - 1), 75 + y2);
            }
            x2 = 75; //aumenta o x para desenhar o proximo pokemon 75px a direita
        }


        //desenha o pokemon com zoom para aparecer na parte superior da tela
        PokemonLiberado pl = PokemonLiberadoDAO.getPokemon(this.pokemonSelecionado + 1); //pega o pokemon que esta selecionado(apenas os liberados)
        Pokemon poke = PokemonDAO.getPokemon(this.pokemonSelecionado + 1); //pega o pokemon que esta selecionado

        try {
            if (pl.getIdPokemon() != 0) { //se a busca do dao retornar resultado, desenha a imagem colorida
                this.imgGrande = new Imagem("resources/personagens/" + pl.getIdPokemon() + " - " + pl.getNome() + "/" + pl.getNome() + "_Down.gif");
            } else { //senão, desenha preto.
                this.imgGrande = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Locked.gif");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Recurso não encontrado: " + ex.getMessage());
            System.exit(1);
        }

        this.imgGrande.drawZoomed(g, 250, 50, 5);


    }

    public void teclas() {

        Keyboard teclado = GameEngine.getInstance().getKeyboard();

        if (teclado.keyDown(Keys.ESQUERDA)) {
            //se o quadrado de seleção nao estiver na primeira coluna
            //o quadrado entao simplesmenta anda uma casa para a esquerda
            if (this.xSelecionado > 0) {
                this.xSelecionado -= 1;
                this.pokemonSelecionado -= 1;
            } else //se o quadrado de seleção esiver na primeira coluna
            //o quadrado entao vai para a ultima coluna(direita)
            if (this.xSelecionado <= 0) {
                this.xSelecionado = 8;
                this.pokemonSelecionado += 8;
            }

            //posicao do quadrado de seleção de personagem
            this.xDraw = (this.xSelecionado + 1) * 75 - 5;

            Util.sleep(150);
        }
        if (teclado.keyDown(Keys.DIREITA)) {
            //se o quadrado de seleção nao estiver na ultima coluna
            //o quadrado entao simplesmenta anda uma casa para a direita
            if (this.xSelecionado < 8) {
                this.xSelecionado += 1;
                this.pokemonSelecionado += 1;
            } else //se o quadrado de seleção esiver na ultima coluna
            //o quadrado entao vai para a primeira coluna(esquerda)
            if (this.xSelecionado >= 0) {
                this.xSelecionado = 0;
                this.pokemonSelecionado -= 8;
            }

            this.xDraw = (this.xSelecionado + 1) * 75 - 5;

            Util.sleep(150);
        }

        if (teclado.keyDown(Keys.CIMA)) {
            //se o quadrado de seleção nao estiver na primeira linha
            //o quadrado entao simplesmenta anda uma casa para cima
            if (this.ySelecionado > 1) {
                //se a linha for uma das tres primeiras, move o quadrado
                //senao, simplesmente deixa ele parado na ultima
                if (this.linha <= 3) {
                    this.ySelecionado -= 1;
                }
                this.pokemonSelecionado -= 9;
                this.linha--; //linha onde esta o quadrado
            } else //se o quadrado de seleção estiver na primeira linha
            //o quadrado entao vai para a linha de bem de baixo(terceira)
            if (this.ySelecionado <= 1) {
                this.ySelecionado = 3;
                this.pokemonSelecionado += 9 * (this.numLinhas-1); //pokemon selecionado é o da ultima linha
                this.linha = this.numLinhas;
            }

            this.yDraw = (this.ySelecionado * 75 - 5) + 350;

            Util.sleep(150);
        }
        if (teclado.keyDown(Keys.BAIXO)) {
            //se o quadrado de seleção nao estiver na ultima linha
            //o quadrado entao simplesmenta anda uma casa para baixo
            this.linha++; //linha onde esta o quadrado
            if (this.ySelecionado < 3) {
                this.ySelecionado += 1;
                this.pokemonSelecionado += 9;
            } else //se o quadrado de seleção esiver na ultima linha
            //o quadrado anda uma linha para baixo, mostrando os pokemons da proxima linha
            //e fazendo desaparecer os da linha de cima
            {
                if (this.linha == this.numLinhas && this.linha > 3) {
                    this.linha = 1;
                    this.ySelecionado = 1;
                    this.pokemonSelecionado = this.xSelecionado;
                } else {
                    this.pokemonSelecionado += 9;
                }
            }
        }
        this.yDraw = (this.ySelecionado * 75 - 5) + 350;
        Util.sleep(150);


        
        //tecla de espaço
        
        Pokemon p = this.listaDePokemon.get(this.pokemonSelecionado);
        String nome = p.getNome();
        PokemonLiberado pl = PokemonLiberadoDAO.getPokemonPeloNome(nome);
        //se o jogador apertou espaço e o pokemon escolhido ja foi liberado, comeca o jogo
        if (teclado.keyDown(Keys.ENTER) && pl.getNome() != null) {
            this.player1 = this.nomes.get(this.pokemonSelecionado);

            Util.sleep(500);
            this.sorteiaInimigo();
            //enquanto o inimigo for igual ao jogador, sorteia de novo.
            //isso nao sera mais usado quando for implantado o sistema de tiles
            //porque pode sim existir um pokemon igual ao selecionado pelo jogador
            while (this.inimigo.equals(this.getPlayer1())) {
                this.sorteiaInimigo();
            }
            this.iniciaJogo();
        }
    }

    public void desenhaFundo(Graphics g) {

        //arrumar
        //desenha fundo de cima
        g.setColor(Color.lightGray);
        g.fillRect(75, 40, 675, 350);
        g.setColor(Color.decode("1996553984"));
        g.drawRect(75, 40, 675, 350);


        int linhas = 3;
        int colunas = 9;

        //desenha os quadrados que ficam por baixo dos pokemons
        for (int i = 1; i <= colunas; i++) {
            for (int i2 = 1; i2 <= linhas; i2++) {
                g.setColor(Color.lightGray);
                g.fillRect(75 * i, 350 + 75 * i2, 70, 70);
                //g.setColor(Color.red); 
                g.setColor(Color.decode("1996553984"));
                g.drawRect(75 * i, 350 + 75 * i2, 70, 70);
            }
        }
        g.setColor(Color.white);
    }
}
