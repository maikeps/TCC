/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MySQL.ConjuntoResultados;
import MySQL.MySQL;
import java.util.ArrayList;
import model.PokemonLiberado;

/**
 *
 * @author maike_p_santos
 */
public class PokemonLiberadoDAO {

    public static ArrayList<PokemonLiberado> getListaPokemon(int idJogador) {
        
        ArrayList<PokemonLiberado> lista = new ArrayList<PokemonLiberado>();
        
        MySQL banco = new MySQL();
//        String sql = "select * from pokemonLiberado where idJogador = "+idJogador;
        String sql = "select * from pokemonLiberado "
                + "inner join Pokemon on Pokemon.id = PokemonLiberado.idPokemon "
                + "where idJogador = "+idJogador;
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        
        while(linhas.next()){
            PokemonLiberado p = new PokemonLiberado();
            
            p.setIdJogador(linhas.getInt("idJogador"));
            p.setIdPokemon(linhas.getInt("idPokemon"));
            p.setAtk(linhas.getInt("atk"));
            p.setDef(linhas.getInt("def"));
            p.setExp(linhas.getInt("exp"));
            p.setFaseQueChegou(linhas.getInt("faseQueChegou"));
            p.setHp(linhas.getInt("hp"));
            p.setInimigosDerrotados(linhas.getInt("inimigosDerrotados"));
            p.setLvl(linhas.getInt("lvl"));
            p.setLvlQueChegou(linhas.getInt("lvlQueChegou"));
            p.setSpd(linhas.getInt("spd"));
            p.setTotalDanoCausado(linhas.getInt("totalDanoCausado"));
            p.setVezesDerrotasParaNPC(linhas.getInt("vezesDerrotasParaNPC"));
            p.setVezesQueZerouOJogo(linhas.getInt("vezesQueZerouOJogo"));
            p.setNome(linhas.getString("nome"));
            
            lista.add(p);
        }
        
        return lista;
        
    }
    
    public static PokemonLiberado getPokemon(int id){
        MySQL banco = new MySQL();
        String sql = "select * from pokemonLiberado "
                + "inner join Pokemon on Pokemon.id = PokemonLiberado.idPokemon "
                + "where PokemonLiberado.idPokemon = "+id;
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        PokemonLiberado p = new PokemonLiberado();
        
        if(linhas.next()){
            p.setIdJogador(linhas.getInt("idJogador"));
            p.setIdPokemon(linhas.getInt("idPokemon"));
            p.setAtk(linhas.getInt("atk"));
            p.setDef(linhas.getInt("def"));
            p.setExp(linhas.getInt("exp"));
            p.setFaseQueChegou(linhas.getInt("faseQueChegou"));
            p.setHp(linhas.getInt("hp"));
            p.setInimigosDerrotados(linhas.getInt("inimigosDerrotados"));
            p.setLvl(linhas.getInt("lvl"));
            p.setLvlQueChegou(linhas.getInt("lvlQueChegou"));
            p.setSpd(linhas.getInt("spd"));
            p.setTotalDanoCausado(linhas.getInt("totalDanoCausado"));
            p.setVezesDerrotasParaNPC(linhas.getInt("vezesDerrotasParaNPC"));
            p.setVezesQueZerouOJogo(linhas.getInt("vezesQueZerouOJogo"));
            p.setNome(linhas.getString("nome"));
        }
        
        return p;
    }
    
    public static PokemonLiberado getPokemonPeloNome(String nome){
        MySQL banco = new MySQL();
        String sql = "select * from pokemonLiberado "
                + "inner join Pokemon on Pokemon.id = PokemonLiberado.idPokemon "
                + "where Pokemon.nome = \""+nome+"\"";
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        PokemonLiberado p = new PokemonLiberado();
        
        if(linhas.next()){
            p.setIdJogador(linhas.getInt("idJogador"));
            p.setIdPokemon(linhas.getInt("idPokemon"));
            p.setAtk(linhas.getInt("atk"));
            p.setDef(linhas.getInt("def"));
            p.setExp(linhas.getInt("exp"));
            p.setFaseQueChegou(linhas.getInt("faseQueChegou"));
            p.setHp(linhas.getInt("hp"));
            p.setInimigosDerrotados(linhas.getInt("inimigosDerrotados"));
            p.setLvl(linhas.getInt("lvl"));
            p.setLvlQueChegou(linhas.getInt("lvlQueChegou"));
            p.setSpd(linhas.getInt("spd"));
            p.setTotalDanoCausado(linhas.getInt("totalDanoCausado"));
            p.setVezesDerrotasParaNPC(linhas.getInt("vezesDerrotasParaNPC"));
            p.setVezesQueZerouOJogo(linhas.getInt("vezesQueZerouOJogo"));
            p.setNome(linhas.getString("Pokemon.nome"));
        }
        
        return p;
    }
    
     public static int getExperiencia(int lvl){
        MySQL banco = new MySQL();
        String sql = "select * from experiencia where lvl = "+lvl;
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        PokemonLiberado p = new PokemonLiberado();
        
        int exp = 0;
        if(linhas.next()){
            exp = linhas.getInt("exp");                    
        }
        
        return exp;
     }
    
    
}
