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
        String sql = "select * from pokemon p "
                + "inner join elemento e1 on p.elementoPrimario = e1.id "
                + "inner join elemento e2 on p.elementoSecundario = e2.id "
                + "inner join ataque a on p.idAtaque = a.id "
                + "inner join elemento eAtk on a.elemento = eAtk.id "
                + "where p.id = " + id;

        ConjuntoResultados linhas = banco.executaSelect(sql);

        Pokemon p = new Pokemon();

        if (linhas.next()) {

            p.setId(linhas.getInt("p.id"));
            p.setNome(linhas.getString("p.nome"));
            p.setRaridade(linhas.getInt("p.raridade"));
            p.setLevelQueEvolui(linhas.getInt("p.lvlQueEvolui"));
            p.setAtkBase(linhas.getInt("p.atkBase"));
            p.setDefBase(linhas.getInt("p.defBase"));
            p.setSpdBase(linhas.getInt("p.spdBase"));
            p.setHpBase(linhas.getInt("p.hpBase"));
            p.setElementoPrimario(linhas.getInt("p.elementoPrimario"));
            p.setElementoSecundario(linhas.getInt("p.elementoSecundario"));
            p.setElementoPrimarioString(linhas.getString("e1.elemento"));
            p.setElementoSecundarioString(linhas.getString("e2.elemento"));
            p.setBaseExp(linhas.getInt("p.baseExp"));
            p.setIdAtaque(linhas.getInt("a.id"));
            p.setNomeAtaque(linhas.getString("a.nome"));
            p.setForcaAtaque(linhas.getInt("a.atk"));
            p.setElementoAtaque(linhas.getString("eAtk.elemento"));
            p.setElementoAtaqueId(linhas.getInt("eAtk.id"));

        }

        return p;

    }

    public static ArrayList<Pokemon> getLista() {
        ArrayList<Pokemon> lista = new ArrayList<Pokemon>();

        MySQL banco = new MySQL();
        String sql = "select * from pokemon";

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
