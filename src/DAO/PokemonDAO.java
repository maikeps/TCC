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

    public static Pokemon getPokemon(int id) {

        MySQL banco = new MySQL();
        String sql = "select * from pokemon "
                + "inner join elemento on pokemon.elementoPrimario = elemento.id "
                + "where pokemon.id = " + id;

        ConjuntoResultados linhas = banco.executaSelect(sql);

        Pokemon p = new Pokemon();

        if (linhas.next()) {
            p.setId(linhas.getInt("pokemon.id"));
            p.setNome(linhas.getString("pokemon.nome"));
            p.setRaridade(linhas.getInt("pokemon.raridade"));
            p.setLevelQueEvolui(linhas.getInt("pokemon.lvlQueEvolui"));
            p.setAtkBase(linhas.getInt("pokemon.atkBase"));
            p.setDefBase(linhas.getInt("pokemon.defBase"));
            p.setSpdBase(linhas.getInt("pokemon.spdBase"));
            p.setHpBase(linhas.getInt("pokemon.hpBase"));
            p.setElementoPrimario(linhas.getInt("pokemon.elementoPrimario"));
            p.setElementoSecundario(linhas.getInt("pokemon.elementoSecundario"));
            p.setElementoPrimarioString(linhas.getString("elemento.elemento"));
            p.setBaseExp(linhas.getInt("pokemon.baseExp"));
        }

        sql = "select * from pokemon "
                + "inner join elemento on pokemon.elementoSecundario = elemento.id "
                + "where pokemon.id = " + id;

        linhas = banco.executaSelect(sql);

        if (linhas.next()) {
            p.setElementoSecundarioString(linhas.getString("elemento.elemento"));
        }

        sql = "select * from pokemon "
                + "inner join ataque on pokemon.idAtaque = ataque.id "
                + "inner join elemento on ataque.elemento = elemento.id "
                + "where pokemon.id = " + id;

        linhas = banco.executaSelect(sql);

        if (linhas.next()) {
            p.setIdAtaque(linhas.getInt("ataque.id"));
            p.setNomeAtaque(linhas.getString("ataque.nome"));
            p.setForcaAtaque(linhas.getInt("ataque.atk"));
            p.setElementoAtaque(linhas.getString("elemento.elemento"));
            p.setElementoAtaqueId(linhas.getInt("elemento.id"));
        }

        return p;
    }

    public static Pokemon getPokemonVelho(int id) {

        MySQL banco = new MySQL();
        String sql = "select * from pokemon "
                + "inner join elemento e1 on pokemon.elementoPrimario = e1.id "
                + "inner join elemento e2 on pokemon.elementoSecundario = e2.id "
                + "inner join ataque on pokemon.idAtaque = ataque.id "
                + "inner join elemento eAtk on ataque.elemento = eAtk.id "
                + "where pokemon.id = " + id;

        ConjuntoResultados linhas = banco.executaSelect(sql);

        Pokemon p = new Pokemon();

        if (linhas.next()) {

            p.setId(linhas.getInt("pokemon.id"));
            p.setNome(linhas.getString("pokemon.nome"));
            p.setRaridade(linhas.getInt("pokemon.raridade"));
            p.setLevelQueEvolui(linhas.getInt("pokemon.lvlQueEvolui"));
            p.setAtkBase(linhas.getInt("pokemon.atkBase"));
            p.setDefBase(linhas.getInt("pokemon.defBase"));
            p.setSpdBase(linhas.getInt("pokemon.spdBase"));
            p.setHpBase(linhas.getInt("pokemon.hpBase"));
            p.setElementoPrimario(linhas.getInt("pokemon.elementoPrimario"));
            p.setElementoSecundario(linhas.getInt("pokemon.elementoSecundario"));
            p.setElementoPrimarioString(linhas.getString("e1.elemento"));
            p.setElementoSecundarioString(linhas.getString("e2.elemento"));
            p.setBaseExp(linhas.getInt("pokemon.baseExp"));
            p.setIdAtaque(linhas.getInt("ataque.id"));
            p.setNomeAtaque(linhas.getString("ataque.nome"));
            p.setForcaAtaque(linhas.getInt("ataque.atk"));
            p.setElementoAtaque(linhas.getString("eAtk.elemento"));
            p.setElementoAtaqueId(linhas.getInt("eAtk.id"));

        }

        return p;

    }

    public static ArrayList<Pokemon> getLista() {
        ArrayList<Pokemon> lista = new ArrayList<Pokemon>();

        MySQL banco = new MySQL();
        String sql = "select * from pokemon "
                + "inner join elemento on pokemon.elementoPrimario = elemento.id";
                
        ConjuntoResultados linhas = banco.executaSelect(sql);

        while (linhas.next()) {
            Pokemon p = new Pokemon();

            p.setId(linhas.getInt("id"));
            p.setNome(linhas.getString("nome"));
            p.setRaridade(linhas.getInt("raridade"));
            p.setLevelQueEvolui(linhas.getInt("lvlQueEvolui"));
            p.setAtkBase(linhas.getInt("atkBase"));
            p.setDefBase(linhas.getInt("defBase"));
            p.setSpdBase(linhas.getInt("spdBase"));
            p.setHpBase(linhas.getInt("hpBase"));
            p.setElementoPrimario(linhas.getInt("elementoPrimario"));
            p.setElementoSecundario(linhas.getInt("elementoSecundario"));
            p.setElementoPrimarioString(linhas.getString("elemento.elemento"));

            lista.add(p);
        }

        return lista;
    }

    public static Pokemon getPokemonPeloNome(String nome) {

        MySQL banco = new MySQL();
        String sql = "select * from pokemon where nome = \"" + nome + "\"";

        ConjuntoResultados linhas = banco.executaSelect(sql);

        Pokemon p = new Pokemon();
        if (linhas.next()) {


            p.setId(linhas.getInt("id"));
            p.setNome(linhas.getString("nome"));
            p.setRaridade(linhas.getInt("raridade"));
            p.setLevelQueEvolui(linhas.getInt("lvlQueEvolui"));
            p.setAtkBase(linhas.getInt("atkBase"));
            p.setDefBase(linhas.getInt("defBase"));
            p.setSpdBase(linhas.getInt("spdBase"));
            p.setHpBase(linhas.getInt("hpBase"));
            p.setElementoPrimario(linhas.getInt("elementoPrimario"));
            p.setElementoSecundario(linhas.getInt("elementoSecundario"));

        }

        return p;

    }
}
