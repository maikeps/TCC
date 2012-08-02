/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MySQL.ConjuntoResultados;
import MySQL.MySQL;
import java.util.ArrayList;
import model.Pokemon;

/**
 *
 * @author maike_p_santos
 */
public class PokemonDAO {

    public static ArrayList<Pokemon> getPokemon(int id) {
        
        ArrayList<Pokemon> lista = new ArrayList<Pokemon>();
        
        MySQL banco = new MySQL();
        String sql = "select * from pokemon where id = "+id;
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        
        while(linhas.next()){
            Pokemon p = new Pokemon();
            
            p.setId(linhas.getInt("id"));
            p.setNome(linhas.getString("nome"));
            p.setRaridade(linhas.getInt("raridade"));
            p.setLevelQueEvolui(linhas.getInt("lvlQueEvolui"));
            p.setAtkbase(linhas.getInt("atkBase"));
            p.setDefBase(linhas.getInt("defBase"));
            p.setSpdbase(linhas.getInt("spdBase"));
            p.setHpBase(linhas.getInt("hpBase"));
            p.setElementoPrimario(linhas.getInt("elementoPrimario"));
            p.setElementoSecundario(linhas.getInt("elementoSecundario"));
            
            lista.add(p);
        }
        
        return lista;
        
    }
    
    public static ArrayList<Pokemon> getLista(){
        ArrayList<Pokemon> lista = new ArrayList<Pokemon>();
        
        MySQL banco = new MySQL();
        String sql = "select * from pokemon";
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        
        while(linhas.next()){
            Pokemon p = new Pokemon();
            
            p.setId(linhas.getInt("id"));
            p.setNome(linhas.getString("nome"));
            p.setRaridade(linhas.getInt("raridade"));
            p.setLevelQueEvolui(linhas.getInt("lvlQueEvolui"));
            p.setAtkbase(linhas.getInt("atkBase"));
            p.setDefBase(linhas.getInt("defBase"));
            p.setSpdbase(linhas.getInt("spdBase"));
            p.setHpBase(linhas.getInt("hpBase"));
            p.setElementoPrimario(linhas.getInt("elementoPrimario"));
            p.setElementoSecundario(linhas.getInt("elementoSecundario"));
            
            lista.add(p);
        }
        
        return lista;
    }
}
