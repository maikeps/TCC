/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MySQL.ConjuntoResultados;
import MySQL.MySQL;
import java.util.ArrayList;
import model.PokemonDerrotado;

/**
 *
 * @author maike_p_santos
 */
public class PokemonDerrotadoDAO {
    
    public static PokemonDerrotado getPokemon(int id){
        
        MySQL banco = new MySQL();
        String sql = "select * from pokemonDerrotado where idPokemon = " + id;

        ConjuntoResultados linhas = banco.executaSelect(sql);

        PokemonDerrotado pd = new PokemonDerrotado();

        if (linhas.next()) {

            pd.setId(linhas.getInt("id"));
            pd.setIdPokemon(linhas.getInt("idPokemon"));
            pd.setVezesDerrotado(linhas.getInt("vezesDerrotado"));
        }

        return pd;
    }
    
    public static ArrayList<PokemonDerrotado> getLista(){
        ArrayList<PokemonDerrotado> lista = new ArrayList<PokemonDerrotado>();

        MySQL banco = new MySQL();
        String sql = "select * from pokemonDerrotado";

        ConjuntoResultados linhas = banco.executaSelect(sql);

        while (linhas.next()) {
            PokemonDerrotado p = new PokemonDerrotado();

            p.setId(linhas.getInt("id"));
            p.setIdPokemon(linhas.getInt("idPokemon"));
            p.setVezesDerrotado(linhas.getInt("vezesDerrotado"));

            lista.add(p);
        }

        return lista;
    }
    
}
