/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MySQL.ConjuntoResultados;
import MySQL.MySQL;
import model.PokemonInimigo;

/**
 *
 * @author maike_p_santos
 */
public class PokemonInimigoDAO {
    
    public static PokemonInimigo getPokemonInimigo(int id){
        
        MySQL banco = new MySQL();
        String sql = "select * from pokemonInimigo where id = "+id;
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        
        PokemonInimigo p = new PokemonInimigo();
        if(linhas.next()){
            
            p.setIdPokemon(linhas.getInt("idPokemon"));
            p.setTipo(linhas.getString("tipo"));
            p.setAtk(linhas.getInt("atk"));
            p.setDef(linhas.getInt("def"));
            p.setSpd(linhas.getInt("spd"));
            p.setHp(linhas.getInt("hp"));
            p.setLvl(linhas.getInt("lvl"));
            
        }
        
        return p;      
    }
    
}
