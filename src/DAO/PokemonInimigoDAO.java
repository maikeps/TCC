/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MySQL.ConjuntoResultados;
import MySQL.MySQL;
import java.util.ArrayList;
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
    
    
    public static ArrayList<PokemonInimigo> getListaPokemonInimigo(){
        
        ArrayList<PokemonInimigo> lista = new ArrayList<PokemonInimigo>();
        
        MySQL banco = new MySQL();
        String sql = "select * from pokemonInimigo";
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        
        
        while(linhas.next()){
            PokemonInimigo p = new PokemonInimigo();
            
            
            p.setIdPokemon(linhas.getInt("idPokemon"));
            p.setTipo(linhas.getString("tipo"));
            p.setAtk(linhas.getInt("atk"));
            p.setDef(linhas.getInt("def"));
            p.setSpd(linhas.getInt("spd"));
            p.setHp(linhas.getInt("hp"));
            p.setLvl(linhas.getInt("lvl"));
            
            lista.add(p);
            
        }
        
        return lista;      
    }
    
    public static PokemonInimigo getPokemonInimigoPeloNome(String nome){
        
        MySQL banco = new MySQL();
        String sql = "select * from pokemonInimigo "
                + "inner join pokemon on pokemon.id = pokemonInimigo.idPokemon "
                + "where nome = \""+nome+"\"";
        ConjuntoResultados linhas = banco.executaSelect(sql);
        
        PokemonInimigo p = new PokemonInimigo();
        if(linhas.next()){
            
            p.setIdPokemon(linhas.getInt("pokemonInimigo.idPokemon"));
            p.setTipo(linhas.getString("pokemonInimigo.tipo"));
            p.setAtk(linhas.getInt("pokemonInimigo.atk"));
            p.setDef(linhas.getInt("pokemonInimigo.def"));
            p.setSpd(linhas.getInt("pokemonInimigo.spd"));
            p.setHp(linhas.getInt("pokemonInimigo.hp"));
            p.setLvl(linhas.getInt("pokemonInimigo.lvl"));
            p.setNome(linhas.getString("pokemon.nome"));
            
        }
        
        return p;    
    }
}
