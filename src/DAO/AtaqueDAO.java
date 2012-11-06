/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import model.Ataque;
import MySQL.ConjuntoResultados;
import MySQL.MySQL;
import java.util.ArrayList;

/**
 *
 * @author maike_p_santos
 */
public class AtaqueDAO {

    public static Ataque getAtaquePeloId(int id) {

        MySQL banco = new MySQL();
        String sql = "select * from ataque where id = " + id;

        ConjuntoResultados linhas = banco.executaSelect(sql);

        Ataque a = new Ataque();
        while (linhas.next()) {

            a.setId(linhas.getInt("id"));
            a.setNome(linhas.getString("nome"));
            a.setAtk(linhas.getInt("atk"));
            a.setElemento(linhas.getInt("elemento"));


        }

        return a;

    }

    public static Ataque getAtaque(String nome) {

        MySQL banco = new MySQL();
        String sql = "select * from ataque where nome = \"" + nome + "\"";

        ConjuntoResultados linhas = banco.executaSelect(sql);

        Ataque a = new Ataque();

        if (linhas.next()) {
            a.setId(linhas.getInt("id"));
            a.setNome(linhas.getString("nome"));
            a.setAtk(linhas.getInt("atk"));
            a.setElemento(linhas.getInt("elemento"));

        }

        return a;

    }

    public static Ataque getPoder(String nome) {
        MySQL banco = new MySQL();
        String sql = "select * from pokemon ";
        sql += "inner join ataque on pokemon.idAtaque = ataque.id ";
        sql += "where pokemon.nome = \"" + nome + "\"";


        ConjuntoResultados linhas = banco.executaSelect(sql);

        Ataque a = new Ataque();

        if (linhas.next()) {
            a.setId(linhas.getInt("id"));
            a.setNome(linhas.getString("ataque.nome"));
            a.setAtk(linhas.getInt("atk"));
            a.setElemento(linhas.getInt("elemento"));

        }

        return a;
    }
    
    public static ArrayList<Ataque> getListaAtaque(){
        
        ArrayList<Ataque> lista = new ArrayList<Ataque>();
        
        MySQL banco = new MySQL();
        String sql = "select * from ataque";
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        
        while(linhas.next()){
            Ataque a = new Ataque();
            
            a.setId(linhas.getInt("id"));
            a.setNome(linhas.getString("nome"));
            a.setAtk(linhas.getInt("atk"));
            a.setElemento(linhas.getInt("elemento"));
            
            lista.add(a);
        }
        return lista;
        
    }
}
