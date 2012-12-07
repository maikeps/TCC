/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import DAO.PokemonDAO;
import DAO.PokemonDerrotadoDAO;
import DAO.PokemonLiberadoDAO;
import tcc.Player;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Pokemon;
import model.PokemonDerrotado;
import model.PokemonLiberado;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import tcc.Biomas;
import util.Util;

/**
 *
 * @author maike_p_santos
 */
public class CharacterSelect extends BasicGameState {

    public static final int ID = 5;
    StateBasedGame game;
    private Image cenario; //imagem de fundo
    static String player1; //qual personagem o player escolheu
    private String inimigo; //qual personagem o inimigo escolheu
    private int xSelecionado = 1; //qual pokemon esta selecionado na horizontal(de 1 ate 9)
    private int ySelecionado = 1; //qual pokemon esta selecionado na vertical(de 1 ate 3)
    private int xDraw; //qual o x do quadrado de selecao
    private int yDraw; //qual o y do quadrado de selecao
    public ArrayList<PokemonLiberado> listaDePokemonLiberado; //lista de pokemons liberados
    public ArrayList<Pokemon> listaDePokemon; //lista de pokemon(todos)
    public ArrayList<String> nomes; //lista de nomes dos pokemons
    public ArrayList<String> nomesLiberados; //lista de nomes dos pokemons liberados
    public Image pokemonImage; //imagem do pokemon a desenhar
    public int pokemonSelecionado = 0; //qual pokemon esta selecionado(na lista o item zero é o primeiro pokemon)
    public Image imgGrande; //imagem redimensionada do pokemon
    public int linha = 1; //em qual linha o quadrado de selecao esta atualmente
    public int numLinhas; //numero de linhas de pokemon
    Player player;
    Sound somSelect;
    Sound somMove;
    ArrayList<Pokemon> pokemonsPrimeiraLinha;
    ArrayList<Pokemon> pokemonsSegundaLinha;
    ArrayList<Pokemon> pokemonsTerceiraLinha;
    int totalLinhas = 5;

    public CharacterSelect(String p1) {
        this.ySelecionado = 1;
        this.player1 = p1;
        this.xDraw = 70;
        this.yDraw = 70 + 260;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;

        this.listaDePokemon = PokemonDAO.getLista();
        this.listaDePokemonLiberado = PokemonLiberadoDAO.getListaPokemon(1);

        this.nomes = new ArrayList<String>();
        this.nomesLiberados = new ArrayList<String>();
        for (Pokemon p : this.listaDePokemon) {
            this.nomes.add(p.getNome());
        }
        for (PokemonLiberado pl : this.listaDePokemonLiberado) {
            this.nomesLiberados.add(pl.getNome());
        }

        this.pokemonsPrimeiraLinha = new ArrayList<Pokemon>();
        this.pokemonsSegundaLinha = new ArrayList<Pokemon>();
        this.pokemonsTerceiraLinha = new ArrayList<Pokemon>();
        for (int i = 0; i < 9; i++) {
            this.pokemonsPrimeiraLinha.add(this.listaDePokemon.get(i));
        }
        for (int i = 0; i < 9; i++) {
            this.pokemonsSegundaLinha.add(this.listaDePokemon.get(i + 9));
        }
        for (int i = 0; i < 9; i++) {
            this.pokemonsTerceiraLinha.add(this.listaDePokemon.get(i + 18));
        }

        this.sorteiaInimigo();
//
//        for (Pokemon p : this.listaDePokemon) {
//            this.nomes.add(p.getNome());
//        }

        this.numLinhas = ((this.listaDePokemon.size() + 1) / 9) + 1;

        this.cenario = new Image("resources/Cenario/fundo CharSelect.png");

        this.somSelect = new Sound("resources/sounds/misc/select.wav");
        this.somMove = new Sound("resources/sounds/misc/move.wav");
        System.out.println("CharacterSelect loaded.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        this.cenario.draw(0, 0);

        g.setColor(Color.white);
        //   g.drawString(" Player 1, escolha o personagem !!", 250, 290);
        g.drawString("Pressione o botão 'BACKSPACE' para voltar", gc.getWidth() / 2 - g.getFont().getWidth("Pressione o botão 'BACKSPACE' para voltar") / 2, 565);

        this.desenhaFundo(gc, g);
        this.desenhaImagens(gc, g);
        this.desenhaStats(gc, g);

        g.setColor(Color.white);

        g.drawRect(this.xDraw, this.yDraw, 80, 80);
        g.drawRect(this.xDraw + 1, this.yDraw + 1, 79, 79);
        g.drawRect(this.xDraw + 2, this.yDraw + 2, 78, 78);
        g.drawRect(this.xDraw + 3, this.yDraw + 3, 77, 77);
        g.drawRect(this.xDraw + 4, this.yDraw + 4, 76, 77);
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_UP) {
            if(this.ySelecionado > 1){
            this.somMove.play();
            this.ySelecionado--;
            this.pokemonSelecionado -= 9;
            if (this.linha == 1) {
                if (this.ySelecionado <= 0) {
//                    this.ySelecionado = this.totalLinhas;
//                    this.linha = 3;
//                    this.pokemonSelecionado += 9 * (this.totalLinhas);
//                    System.out.println(this.pokemonSelecionado + "poke selecionado");
                }
                if (this.ySelecionado == this.totalLinhas) {
//                    this.pokemonsPrimeiraLinha.clear();
//                    this.pokemonsSegundaLinha.clear();
//                    this.pokemonsTerceiraLinha.clear();
//                    for (int i = 0; i < 9; i++) {
//                        System.out.println("primeira linha: " + (i + (totalLinhas - 3) * 9));
//                        this.pokemonsPrimeiraLinha.add(this.listaDePokemon.get((i + (totalLinhas - 3) * 9)));
//                    }
//                    for (int i = 0; i < 9; i++) {
//                        System.out.println("segunda linha: " + (i + (totalLinhas - 2) * 9));
//                        this.pokemonsSegundaLinha.add(this.listaDePokemon.get((i + (totalLinhas - 2) * 9)));
//                    }
//                    for (int i = 0; i < 9; i++) {
//                        System.out.println("terceira linha: " + (i + (totalLinhas - 1) * 9));
//                        this.pokemonsTerceiraLinha.add(this.listaDePokemon.get((i + (totalLinhas - 1) * 9)));
//                    }
                } else {
                    this.pokemonsPrimeiraLinha.clear();
                    this.pokemonsSegundaLinha.clear();
                    this.pokemonsTerceiraLinha.clear();
                    for (int i = 0; i < 9; i++) {
                        this.pokemonsPrimeiraLinha.add(this.listaDePokemon.get(((i + ((this.ySelecionado - linha) * 9)))));
                    }
                    for (int i = 0; i < 9; i++) {
                        this.pokemonsSegundaLinha.add(this.listaDePokemon.get(((i + ((this.ySelecionado - linha + 1) * 9)))));
                    }
                    for (int i = 0; i < 9; i++) {
                        this.pokemonsTerceiraLinha.add(this.listaDePokemon.get(((i + ((this.ySelecionado - linha + 2) * 9)))));
                    }
//////                    this.pokemonsTerceiraLinha = this.pokemonsSegundaLinha;
//////                    this.pokemonsSegundaLinha = this.pokemonsPrimeiraLinha;
//////                    this.pokemonsPrimeiraLinha.clear();
//////                    for (int i = 0; i < 9; i++) {
//////                        System.out.println("primeira linha up: " + ((i + ((this.ySelecionado - linha + 1) * 9))));
//////                        this.pokemonsPrimeiraLinha.add(this.listaDePokemon.get(((i + ((this.ySelecionado - linha + 1) * 9)))));
//////                    }
                }
            }
            if (this.linha > 1) {
                this.linha--;
            }
            this.yDraw = (this.linha * 75 - 5) + 260;
            }
        }
        if (key == Input.KEY_DOWN) {
            if (this.ySelecionado != this.totalLinhas) {
                this.somMove.play();

                this.ySelecionado++;
                this.pokemonSelecionado += 9;
                System.out.println(ySelecionado + " - linha: " + this.linha);
                if (this.linha == 3) {
                    if (this.ySelecionado > this.totalLinhas) {
//                    this.ySelecionado = 1;
//                    this.linha = 1;
//                    this.pokemonSelecionado = this.xSelecionado-1;
                    }
                    if (this.ySelecionado == 1) {
//                    this.pokemonsPrimeiraLinha.clear();
//                    this.pokemonsSegundaLinha.clear();
//                    this.pokemonsTerceiraLinha.clear();
//                    for (int i = 0; i < 9; i++) {
//                        this.pokemonsPrimeiraLinha.add(this.listaDePokemon.get(i));
//                    }
//                    for (int i = 0; i < 9; i++) {
//                        this.pokemonsSegundaLinha.add(this.listaDePokemon.get(i + 9));
//                    }
//                    for (int i = 0; i < 9; i++) {
//                        this.pokemonsTerceiraLinha.add(this.listaDePokemon.get(i + 18));
//                    }
                    } else {
                        this.pokemonsPrimeiraLinha.clear();
                        this.pokemonsSegundaLinha.clear();
                        this.pokemonsTerceiraLinha.clear();
                        for (int i = 0; i < 9; i++) {
                            this.pokemonsPrimeiraLinha.add(this.listaDePokemon.get(((i + ((this.ySelecionado - linha) * 9)))));
                        }
                        for (int i = 0; i < 9; i++) {
                            this.pokemonsSegundaLinha.add(this.listaDePokemon.get(((i + ((this.ySelecionado - linha + 1) * 9)))));
                        }
                        for (int i = 0; i < 9; i++) {
                            this.pokemonsTerceiraLinha.add(this.listaDePokemon.get(((i + ((this.ySelecionado - linha + 2) * 9)))));
                        }
                    }
                }
                if (this.linha < 3) {
                    this.linha++;
                }
                System.out.println(this.pokemonSelecionado + "poke selecionado down");
                // this.yDraw = (this.ySelecionado * 75 - 5) + 260;
                this.yDraw = (this.linha * 75 - 5) + 260;

            }
        }
        if (key == Input.KEY_LEFT) {
            this.somMove.play();
            this.xSelecionado--;
            this.pokemonSelecionado--;
            if (this.xSelecionado <= 0) {
                this.xSelecionado = 9;
                this.pokemonSelecionado += 9;
            }
            this.xDraw = this.xSelecionado * 75 - 5;

        }
        if (key == Input.KEY_RIGHT) {
            this.somMove.play();
            this.xSelecionado++;
            this.pokemonSelecionado++;
            if (this.xSelecionado > 9) {
                this.xSelecionado = 1;
                this.pokemonSelecionado -= 9;
            }
            this.xDraw = this.xSelecionado * 75 - 5;
        }

        if (key == Input.KEY_BACK) {
            this.somSelect.play();
            game.enterState(MainMenu.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }

        if (key == Input.KEY_ENTER) {
            Pokemon p = this.listaDePokemon.get(this.pokemonSelecionado);
            String nome = p.getNome();
            PokemonLiberado pl = PokemonLiberadoDAO.getPokemonPeloNome(nome);
            if (pl.getNome() != null) {
                try {
                    Sound som = new Sound("resources/sounds/personagens/" + pl.getNome() + ".wav");
                    som.play();
                } catch (SlickException ex) {
                    Logger.getLogger(CharacterSelect.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.player1 = this.nomes.get(this.pokemonSelecionado);
                this.sorteiaInimigo();
                while (inimigo.equals(this.player1)) {
                    this.sorteiaInimigo();
                }

                Biomas bioma = this.getBiomaNativo(p);
                Fase1.biomaInicial = bioma;
                ChangeLevel.reset(bioma);//botar o elemento do pokemon

                //game.enterState(Fase1.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                game.enterState(ChangeLevel.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        }
    }

    public void desenhaImagens(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(Color.white);

        //desenha todos os pokemons
        for (int i = 0; i < this.pokemonsPrimeiraLinha.size(); i++) {
            String status = "Locked";
            if (this.nomesLiberados.contains(this.pokemonsPrimeiraLinha.get(i).getNome())) {
                status = "Down";
            }
            this.pokemonImage = new Image("resources/personagens/" + this.pokemonsPrimeiraLinha.get(i).getId() + " - " + this.pokemonsPrimeiraLinha.get(i).getNome() + "/" + this.pokemonsPrimeiraLinha.get(i).getNome() + "_" + status + ".png");
            this.pokemonImage.drawCentered(110 + (75 * i), 370);
        }
        for (int i = 0; i < this.pokemonsSegundaLinha.size(); i++) {
            String status = "Locked";
            if (this.nomesLiberados.contains(this.pokemonsSegundaLinha.get(i).getNome())) {
                status = "Down";
            }
            this.pokemonImage = new Image("resources/personagens/" + this.pokemonsSegundaLinha.get(i).getId() + " - " + this.pokemonsSegundaLinha.get(i).getNome() + "/" + this.pokemonsSegundaLinha.get(i).getNome() + "_" + status + ".png");
            this.pokemonImage.drawCentered(110 + (75 * i), 445);
        }
        for (int i = 0; i < this.pokemonsTerceiraLinha.size(); i++) {
            String status = "Locked";
            if (this.nomesLiberados.contains(this.pokemonsTerceiraLinha.get(i).getNome())) {
                status = "Down";
            }
            this.pokemonImage = new Image("resources/personagens/" + this.pokemonsTerceiraLinha.get(i).getId() + " - " + this.pokemonsTerceiraLinha.get(i).getNome() + "/" + this.pokemonsTerceiraLinha.get(i).getNome() + "_" + status + ".png");
            this.pokemonImage.drawCentered(110 + (75 * i), 520);
        }


        Pokemon poke = this.listaDePokemon.get(this.pokemonSelecionado);
        if (this.nomesLiberados.contains(this.nomes.get(this.pokemonSelecionado))) { //se a busca do dao retornar resultado, desenha a imagem colorida
            this.imgGrande = new Image("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Down.png");
        } else {
            this.imgGrande = new Image("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Locked.png");
        }
        Image imgGrandeScaled = imgGrande.getScaledCopy(2);
        imgGrandeScaled.drawCentered(gc.getWidth() / 2, 125);


        g.setColor(Color.lightGray);
        g.fillRect(75, 40, 670, 10);//cima
        g.fillRect(75, 200, 670, 95);//baixo
        g.fillRect(75, 40, 200, 220);//esquerda
        g.fillRect(525, 40, 220, 220);//direita

        g.setColor(Color.decode("1996553984"));
        g.drawRect(gc.getWidth() / 2 - 250 / 2, 50, 250, 150);
        g.setColor(Color.white);
    }

    public void desenhaImagensVelho(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(Color.white);

        int x1 = 0; //x da imagem que sera desenhada(desenha todos os pokemons como nao-liberados)
        int y1 = 260; //y da imagem que sera desenhada(desenha todos os pokemons como nao-liberados)
        int cont1 = 0; //contador que verifica se já foram desenhados 9 pokemons na linha(nao-liberados)
        int cont2 = 1; //contador que verifica s já foram desenhados 9 pokemons na linha(liberados)
        int x2 = 0; //x da imagem que sera desenhada(desenha todos os pokemons liberados)
        int y2 = 260; //y da imagem que sera desenhada(desenha todos os pokemons liberados)


        //se a linha selecionada pelo retangulo de selecao for maior que 3(abaixo da terceira)
        //modifica o y para fazer com que as imagens vão para cima
        if (this.linha > 3) {
            y1 -= 75 * (this.linha - 3);
            y2 -= 75 * (this.linha - 3);
        }

        //desenha todos os pokemons
        for (Pokemon pokemon : this.listaDePokemon) {
            cont1++; //aumenta o cont
            if (cont1 > 9) {
                //se o cont for maior que 9, quer dizer que ja desenhou 9 pokemons nessa linha
                //entao passa para a proxima
                cont1 = 1;
                x1 = 0;
                y1 += 75;
            }

            String nome = pokemon.getNome(); //pega o nome do pokemon a desenhar
            //cria a imagem do pokemon   
            this.pokemonImage = new Image("resources/personagens/" + pokemon.getId() + " - " + nome + "/" + nome + "_Locked.png");
            //se o y do pokemon estiver dentro do especificado
            //quer dizer que ele esta em uma das tres linhas
            //entao desenha
            if (!(75 + y1 <= 300 || 75 + y1 > 500)) {
                this.pokemonImage.drawCentered(110 + x1, 110 + y1);
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

            //cria a imagem
            this.pokemonImage = new Image("resources/personagens/" + pl.getIdPokemon() + " - " + nome + "/" + nome + "_Down.png");


            //se o y do pokemon estiver dentro do especificado
            //quer dizer que ele esta em uma das tres linhas
            //entao desenha
            if (!(75 + y2 <= 300 || 75 + y2 > 500)) {
                this.pokemonImage.drawCentered(110 + x2 * (pl.getIdPokemon() - 1), 110 + y2);
            }
            x2 = 75; //aumenta o x para desenhar o proximo pokemon 75px a direita
        }




        //desenha o pokemon com zoom para aparecer na parte superior da tela
        //    PokemonLiberado pl = this.listaDePokemonLiberado.get(this.pokemonSelecionado);//id, nao index ta lista
        //PokemonLiberado pl = PokemonLiberadoDAO.getPokemon(this.pokemonSelecionado + 1); //pega o pokemon que esta selecionado(apenas os liberados)
        //Pokemon poke = PokemonDAO.getPokemon(this.pokemonSelecionado + 1); //pega o pokemon que esta selecionado
        Pokemon poke = this.listaDePokemon.get(this.pokemonSelecionado);

        //if (pl.getIdPokemon() != 0) { //se a busca do dao retornar resultado, desenha a imagem colorida
        if (this.nomesLiberados.contains(this.nomes.get(this.pokemonSelecionado))) { //se a busca do dao retornar resultado, desenha a imagem colorida
//            this.imgGrande = new Image("resources/personagens/" + pl.getIdPokemon() + " - " + pl.getNome() + "/" + pl.getNome() + "_Down.gif");
            this.imgGrande = new Image("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Down.png");
        } else { //senão, desenha preto.
            this.imgGrande = new Image("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Locked.png");
        }

        /// imgGrande.draw(gc.getWidth() / 2 - imgGrande.getWidth() / 2 - 30, imgGrande.getHeight() / 2 + 36, 2);
        Image imgGrandeScaled = imgGrande.getScaledCopy(2);
        imgGrandeScaled.drawCentered(gc.getWidth() / 2, 125);
        // imgGrande.draw(gc.getWidth() / 2, gc.getHeight() / 2, imgGrande.getWidth()*5, imgGrande.getHeight()*5);





        g.setColor(Color.lightGray);
        g.fillRect(75, 40, 670, 10);//cima
        g.fillRect(75, 200, 670, 95);//baixo
        g.fillRect(75, 40, 200, 220);//esquerda
        g.fillRect(525, 40, 220, 220);//direita


        g.setColor(Color.decode("1996553984"));
        g.drawRect(gc.getWidth() / 2 - 250 / 2, 50, 250, 150);
        g.setColor(Color.white);

    }

    public void desenhaFundo(GameContainer gc, Graphics g) {

        //arrumar
        //desenha fundo de cima
//        g.setColor(Color.red);
//        g.fillRect(75, 40, 675, 250);        
        g.setColor(Color.white);
        g.fillRect((gc.getWidth() / 2) - 250 / 2, 50, 250, 150);
        g.setColor(Color.decode("1996553984"));
        g.drawRect(75, 40, 670, 255);


        int linhas = 3;
        int colunas = 9;

        //desenha os quadrados que ficam por baixo dos pokemons
        for (int i = 1; i <= colunas; i++) {
            for (int i2 = 1; i2 <= linhas; i2++) {
                g.setColor(Color.lightGray);
                g.fillRect(75 * i, 260 + 75 * i2, 70, 70);
                //g.setColor(Color.red); 
                g.setColor(Color.decode("1996553984"));
                g.drawRect(75 * i, 260 + 75 * i2, 70, 70);
            }
        }
        g.setColor(Color.white);
    }

    public void desenhaStats(GameContainer gc, Graphics g) {

        int i = this.pokemonSelecionado;

        //nome do pokemon
        g.setColor(Color.black);
        String nome = i + 1 + " - " + this.nomes.get(i);
        g.drawString(nome, gc.getWidth() / 2 - g.getFont().getWidth(nome) / 2, 210);
        // Pokemon p = PokemonDAO.getPokemonPeloNome(this.nomes.get(i));
        Pokemon p = this.listaDePokemon.get(i);
        g.setColor(Color.white);

        //desenha barras de stats - HP, ATK, DEF, SPD
        util.Util.desenhaBarra(g, 130, 80, p.getHpBase(), p.getHpBase(), p.getHpBase(), false);
        util.Util.desenhaBarra(g, 130, 105, p.getAtkBase(), p.getAtkBase(), p.getAtkBase(), false);
        util.Util.desenhaBarra(g, 130, 130, p.getDefBase(), p.getDefBase(), p.getDefBase(), false);
        util.Util.desenhaBarra(g, 130, 155, p.getSpdBase(), p.getSpdBase(), p.getSpdBase(), false);

        g.setColor(Color.black);
        //desenha os numeros dos stats  
        g.drawString("HP: ", 125 - g.getFont().getWidth("HP: "), 80);
        g.drawString("ATK: ", 125 - g.getFont().getWidth("ATK: "), 105);
        g.drawString("DEF: ", 125 - g.getFont().getWidth("DEF: "), 130);
        g.drawString("SPD: ", 125 - g.getFont().getWidth("SPD: "), 155);


        //informacoes sobre o pokemon
        PokemonLiberado pl = PokemonLiberadoDAO.getPokemon(this.pokemonSelecionado + 1);
        g.drawString("Kills: " + pl.getInimigosDerrotados(), 550, 60);
        g.drawString("Deaths: " + pl.getVezesDerrotasParaNPC(), 550, 85);
        g.drawString("Dano Total: " + pl.getTotalDanoCausado(), 550, 110);
        g.drawString("Medals: " + pl.getVezesQueZerouOJogo(), 550, 135);
        if (pl.getVezesDerrotasParaNPC() == 0) {
            g.drawString("K/D: " + pl.getInimigosDerrotados(), 550, 160);
        } else {
            double killsDeaths = (double) pl.getInimigosDerrotados() / (double) pl.getVezesDerrotasParaNPC();
            g.drawString("K/D: " + (killsDeaths), 550, 160);
        }


        //desenha a progress bar
        //nao desenha na primeira linha por que a raridade dos 9 primeiros pokemons é zero
        //isso quer dizer que nao tem como eles aparecerem como inimigo.


        // Pokemon poke = PokemonDAO.getPokemon(this.pokemonSelecionado + 1); //pega o pokemon que esta selecionado
        Pokemon poke = this.listaDePokemon.get(this.pokemonSelecionado); //pega o pokemon que esta selecionado
        PokemonLiberado pokeliberado = PokemonLiberadoDAO.getPokemon(this.pokemonSelecionado + 1);

        if (pokeliberado.getNome() == null) {
            PokemonDerrotado pokeDerrotado = PokemonDerrotadoDAO.getPokemon(this.pokemonSelecionado + 1); //ve quantas vezes o pokemon foi derrotado

            double total = 100;
            util.Util.desenhaBarra(g, gc.getWidth() / 2, 234, (int) total, pokeDerrotado.getVezesDerrotado(), poke.getRaridade(), true);
            g.setColor(Color.black);
            String s = "" + pokeDerrotado.getVezesDerrotado() + "/" + poke.getRaridade();
            g.drawString(s, gc.getWidth() / 2 - g.getFont().getWidth(s) / 2, 260);
        }
    }

    public void sorteiaInimigo() {

        int n = Util.random(this.nomes.size() + 1);
        while (n >= this.nomes.size()) {
            n = Util.random(this.nomes.size() + 1);
        }
        //System.out.println("inimigo no charSelect: "+this.nomes.get(n));
        this.inimigo = this.nomes.get(n);
    }

    public String getPlayer1() {
        return this.player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getInimigo() {
        return this.inimigo;
    }

    public void setInimigo(String inimigo) {
        this.inimigo = inimigo;
    }

    public int getYSelecionado() {
        return this.ySelecionado;
    }

    public int getXDraw() {
        return this.xDraw;
    }

    private Biomas getBiomaNativo(Pokemon p) {
        String elemento = p.getElementoPrimarioString();
        Biomas bioma = Biomas.GRASS;
        System.out.println(elemento);

        if (elemento.equals("Normal") || elemento.equals("Grass") || elemento.equals("Flying")) {
            bioma = Biomas.GRASS;
        } else if (elemento.equals("Fire") || elemento.equals("Dragon") || elemento.equals("Flying")) {
            bioma = Biomas.DESERT;
        } else if (elemento.equals("Water") || elemento.equals("Electric") || elemento.equals("Flying")) {
            bioma = Biomas.BEACH;
        } else if (elemento.equals("Poison") || elemento.equals("Dark") || elemento.equals("Shadow")) {
            bioma = Biomas.SWAMPLAND;
        } else if (elemento.equals("Ice") || elemento.equals("Water")) {
            bioma = Biomas.ICELAND;
        } else if (elemento.equals("Grass") || elemento.equals("Bug") || elemento.equals("Flying")) {
            bioma = Biomas.FOREST;
        } else if (elemento.equals("Dragon") || elemento.equals("Rock") || elemento.equals("Ground") || elemento.equals("Steel")) {
            bioma = Biomas.MOUNTAIN;
        } else if (elemento.equals("Fighting") || elemento.equals("Ghost") || elemento.equals("Psychic") || elemento.equals("Dark")) {
            bioma = Biomas.ABANDONED_CASTLE;
        }

        return bioma;
    }
}
