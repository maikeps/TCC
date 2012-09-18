/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStateController;

import DAO.AtaqueDAO;
import DAO.PokemonDAO;
import com.mysql.jdbc.Util;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay2.GameEngine;
import javaPlay2.GameStateController;
import javaPlay2.Imagem;
import model.Ataque;
import model.Pokemon;

/**
 *
 * @author maike_p_santos
 */
public class LoadingScreen implements GameStateController {

    int progresso;
    boolean carregouTudo = false;

    //  Splash s = new Splash();
    // s.setVisible(false);
    public void load() {
    }

    public void step(long timeElapsed) {
        if (this.carregouTudo) {
            GameEngine.getInstance().setNextGameStateController(2);
        }
        
        this.carregaImagens();
        this.carregouTudo = true;
    }

    public void draw(Graphics g) {
        g.fillRect(0, 0, GameEngine.getInstance().getGameCanvas().getWidth(), GameEngine.getInstance().getGameCanvas().getHeight());
    }

    public void unload() {
    }

    public void start() {
    }

    public void stop() {
    }

    public void carregaImagens() {
        ArrayList<Ataque> listaDeAtaques = AtaqueDAO.getListaAtaque();
        ArrayList<Pokemon> listaDePokemon = PokemonDAO.getLista();

        Imagem imgPokemon;
        Imagem imgAtaque;
        Imagem title;
        Imagem fundoCharSelect;

        //carrega as imagens "Locked" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            //tenta carregar a img em gif
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Locked.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            //tenta carregar a img em png
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Locked.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
            this.progresso++;
        }

        //carrega as imagens "Down" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Down.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
            this.progresso++;
        }

        //carrega as imagens "Up" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Up.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
            this.progresso++;
        }

        //carrega as imagens "Left" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Left.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
            this.progresso++;
        }

        //carrega as imagens "Right" dos pokemon
        for (Pokemon poke : listaDePokemon) {
            try {
                imgPokemon = new Imagem("resources/personagens/" + poke.getId() + " - " + poke.getNome() + "/" + poke.getNome() + "_Right.gif");
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
            this.progresso++;
        }

        /*/carrega os ataques
            
        for (Ataque a : listaDeAtaques) {
            System.out.println("ataque: "+a.getNome());
            try {
                try{
                  imgAtaque = new Imagem("resources/ataques/" + a.getNome() + "/" + a.getNome() + ".gif");
                }finally{
                  imgAtaque = new Imagem("resources/ataques/" + a.getNome() + "/" + a.getNome() + ".png");
                }        
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            // CharacterSelect.pokemonImage.draw(g, 0, 0);
            this.progresso++;
        }
*/
        //carrega a imagem do mainMenu
        try {
            imgPokemon = new Imagem("resources/Title.png");
        } catch (Exception ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        //carrega a imagem de fundo do CharSelect
        this.progresso++;
        try {
            imgPokemon = new Imagem("resources/Cenario/fundo CharSelect.png");
        } catch (Exception ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.progresso++;

    }
}
