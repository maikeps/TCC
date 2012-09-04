package util;

import model.Ataque;
import DAO.AtaqueDAO;
import DAO.PokemonDAO;
import DAO.PokemonLiberadoDAO;
import GameStateController.CharacterSelect;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay2.Imagem;
import javax.swing.JOptionPane;
import model.Pokemon;
import model.PokemonLiberado;
import javaPlayExtras.Splash;

public class Util {

    static public void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    static public int random(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    static public double calculaAngulo(int xAlvo, int xPersonagem, int yAlvo, int yPersonagem) {

        /*
         * xAlvo e yAlvo é onde o player clica com o mouse xPersonagem e
         * yPersonagem é onde o player esta atualmente
         */

        //se calculo estiver errado trocar x2 e y2 por xPonto e yPonto
        int x2 = xAlvo;
        int y2 = yAlvo;

        int xPonto = xPersonagem;
        int yPonto = yPersonagem;
        double quadrante = 1;
        double anguloTotal = 0;


        double b = Math.abs(yPonto - y2);
        double c = Math.abs(xPonto - x2);
        //double b = 50;
        //double c = 26;
        double a = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2));

        double sen = b / a;
        double cos = c / a;

        double tg = sen / cos;


        double angulo = Math.toDegrees(Math.atan(tg));

        //verificacao do quadrante
        if (x2 > xPonto && y2 < yPonto) {
            quadrante = 1.0;
            anguloTotal = angulo;
        }
        if (x2 < xPonto && y2 < yPonto) {
            quadrante = 2.0;
            anguloTotal = 180 - angulo;
        }
        if (x2 < xPonto && y2 > yPonto) {
            quadrante = 3.0;
            anguloTotal = 180 + angulo;
        }
        if (x2 >= xPonto && y2 >= yPonto) {
            quadrante = 4.0;
            anguloTotal = 360 - angulo;
        }


        int startX = 100;
        int startY = 500;
        double endX = startX + 50 * Math.cos(Math.toRadians(anguloTotal * (-1)));
        double endY = startY + 50 * Math.sin(Math.toRadians(anguloTotal * (-1)));

        return anguloTotal;

    }

    public static void carregaImagens() {
        
        

        ArrayList<Ataque> listaDeAtaques = AtaqueDAO.getListaAtaque();
        ArrayList<Pokemon> listaDePokemon = PokemonDAO.getLista();

        Imagem imgPokemon;
        Imagem imgAtaque;
        Imagem title;
        Imagem fundoCharSelect;

        //carrega as imagens "Locked" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Locked.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
        }

        //carrega as imagens "Down" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Down.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
        }

        //carrega as imagens "Up" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Up.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
        }

        //carrega as imagens "Left" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Left.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
        }

        //carrega as imagens "Right" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Right.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
        }

        //carrega os ataques
        for (Ataque a : listaDeAtaques) {
            try {
                imgAtaque = new Imagem("resources/ataques/" + a.getNome() + "/" + a.getNome() + ".gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
        }

        //carrega a imagem do mainMenu
        try {
            imgPokemon = new Imagem("resources/Title.png");
        } catch (Exception ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //carrega a imagem de fundo do CharSelect
        try {
            imgPokemon = new Imagem("resources/Cenario/493pokemons2 preto.png");
        } catch (Exception ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
