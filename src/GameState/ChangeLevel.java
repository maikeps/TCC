/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import tcc.Biomas;
import tcc.ValueNoise2D;

/**
 *
 * @author Maike
 */
public class ChangeLevel extends BasicGameState {

    public static final int ID = 14;
    StateBasedGame game;
    GameContainer gc;
    Sound somSelect;
    Sound somMove;
    public static boolean carregouMapa = false;
    public static int x = 0;
    public static int y = 0;
    public static int mapSize = 128;
    public static Biomas bioma;
    public static boolean inicializou = false;
    ValueNoise2D pn2d;
    float[][] vals;
    BufferedImage img;
    public static String[] sArray;
    String hexStr;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        this.game = game;
        this.gc = gc;
        this.somSelect = new Sound("resources/sounds/misc/select.wav");
        this.somMove = new Sound("resources/sounds/misc/move.wav");



        sArray = new String[mapSize + 1];
        sArray[0] = "";

        System.out.println("ChangeLevel loaded.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int i) throws SlickException {
        if (this.carregouMapa) {
            Fase1.podeComecar = false;
            Fase1.portalSurgiu = false;
            Fase1.bioma = this.bioma;
            Fase1.primeiraVezQueCriaPlayer = true;
            game.enterState(Fase1.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        //sorteia bioma
        
        if (inicializou == false) {
            pn2d = new ValueNoise2D(mapSize, 0.2f, 5, 20000f, new Random());
            vals = pn2d.get();//retorna os valores do noise
            img = new BufferedImage(mapSize + 1, mapSize + 1, BufferedImage.TYPE_INT_ARGB);
            this.inicializou = true;
        }

        if (bioma == null) {
            Biomas[] listaBiomas = Biomas.values();
            bioma = listaBiomas[(new Random()).nextInt(listaBiomas.length)];
        }
        if (carregouMapa == false) {
            this.carregaMapa(bioma, g);
            System.out.println(bioma);
        }
        g.drawString("Carregando mapa, por favor aguarde.", gc.getWidth() / 2 - g.getFont().getWidth("Carregando mapa, por favor aguarde.") / 2, gc.getHeight() / 2 + 50);

    }

    public void keyPressed(int key, char c) {
    }

    public void carregaMapa(Biomas bioma, Graphics g) {
        System.out.println("x: " + x);
        sArray[x] = "";
        for (int y = 0; y < vals[x].length; y++) {
            img.setRGB(x, y, ((int) vals[x][y]) | 0xFF721138);//comeca a desenhar a img

            if (img.getRGB(x, y) > 0xFF725f53) {
                sArray[x] += "2,";
            } else if (img.getRGB(x, y) < 0xFF725f3d) {
                sArray[x] += "3,";
            } else {
                sArray[x] += "1,";
            }
        }
        x++;
        float parcela = 300f / mapSize;
        g.drawRect(gc.getWidth() / 2 - 150, gc.getHeight() / 2 - 10, 300, 20);
        g.fillRect(gc.getWidth() / 2 - 150, gc.getHeight() / 2 - 10, parcela * x, 20);
 

        if (this.x == this.vals.length) {
            this.carregouMapa = true;
            this.salvaArquivo(bioma);
        }
    }

    public void salvaArquivo(Biomas bioma) {
        try {
            ValueNoise2D.limpaTxt(new File("resources/texto.txt"));
        } catch (IOException ex) {
            Logger.getLogger(ValueNoise2D.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] a = new String[4];
        String chao1 = "";
        String chao2 = "";
        String agua = "";
        String obstaculo = "";
        if (bioma == Biomas.GRASS) {
            //grass, normal, flying
            chao1 = "grass";
            chao2 = "grass2";
            agua = "water";
            obstaculo = "bush";
        } else if (bioma == Biomas.DESERT) {
            //fire, dragon, flying
            chao1 = "sand";
            chao2 = "sand_stones";
            agua = "lava";
            obstaculo = "cactus";
        } else if (bioma == Biomas.BEACH) {
            //water, flying, electric
            //porcao de agua, areia ao redor e grama longe da agua.
            chao1 = "beach_sand";
            chao2 = "grass";
            agua = "water";
            obstaculo = "umbrella";
        } else if (bioma == Biomas.SWAMPLAND) {
            //poison, dark, shadow
            //agua de pantano, grama de pantano(mais escura)
            chao1 = "swamp_grass";
            chao2 = "swamp_grass";
            agua = "swamp_water";
            obstaculo = "swamp_plant";
        } else if (bioma == Biomas.ICELAND) {
            //ice e water
            chao1 = "snow";
            chao2 = "snow";
            agua = "ice";
            obstaculo = "ice_stone";
        } else if (bioma == Biomas.FOREST) {
            //grass, bug, flying
            chao1 = "jungle_grass";
            chao2 = "flowers";
            agua = "tree";
            obstaculo = "tree";
        } else if (bioma == Biomas.MOUNTAIN) {
            //dragon, rock, ground, steel
            chao1 = "mountain";
            chao2 = "mountain";
            agua = "mountain_obstacle";//aglomerado de pedras, como se fosse uma elevacao
            obstaculo = "mountain_stone";//pedras menores
        } else if (bioma == Biomas.ABANDONED_CASTLE) {
            //fighting, ghost, psychic, dark
            chao1 = "castle_floor";
            chao2 = "castle_floor";
            agua = "abyss";
            obstaculo = "torch";
        }
        a[0] = "3";
        a[1] = "resources/tiles/tiles avulsos/" + chao1 + ".png";
        a[2] = "resources/tiles/tiles avulsos/" + agua + ".png";
        a[3] = "resources/tiles/tiles avulsos/" + chao2 + ".png";
        ValueNoise2D.saveTxtTeste(new File("resources/texto.txt"), a, true);

        ValueNoise2D.saveTxtTeste(new File("resources/texto.txt"), sArray, true);

        String[] ultimaLinha = {"%"};
        ValueNoise2D.saveTxtTeste(new File("resources/texto.txt"), ultimaLinha, true);
    }

    static void reset(Biomas bioma) {
        ChangeLevel.sArray = new String[ChangeLevel.mapSize + 1];
        ChangeLevel.sArray[0] = "";
        ChangeLevel.x = 0;
        ChangeLevel.y = 0;
        ChangeLevel.carregouMapa = false;
        ChangeLevel.bioma = bioma;
        ChangeLevel.inicializou = false;
    }
}
