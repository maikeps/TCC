/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Insert;

import MySQL.MySQL;


/**
 *
 * @author maike_p_santos
 */
public class PokemonInimigoInsert {
    
    public PokemonInimigoInsert(int idPokemon, String tipo, int atk, int def, int spd, int hp, int lvl) {
        
        String sql = "insert into pokemonInimigo "
                + "(idPokemon, tipo, atk, def, spd, hp, lvl) values"
                + "(\""+idPokemon+"\", \""+tipo+"\", \""+atk+"\", "
                + "\""+def+"\", \""+spd+"\", \""+hp+"\", \""+lvl+"\")";
        
        MySQL bd = new MySQL();
        boolean bool = bd.executaInsert(sql);
        
        System.out.println(sql);
        
    }
    
}
