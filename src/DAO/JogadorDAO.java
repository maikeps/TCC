/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import MySQL.ConjuntoResultados;
import MySQL.MySQL;
import java.util.ArrayList;
import model.Jogador;

/**
 *
 * @author maike_p_santos
 */
public class JogadorDAO {
    
    public static ArrayList<Jogador> getJogador(int id){
        
        ArrayList<Jogador> lista = new ArrayList<Jogador>();
        
        MySQL banco = new MySQL();
        String sql = "select * from pokemon where id = "+id;
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        
        while(linhas.next()){
            Jogador j = new Jogador();
            
            j.setId(linhas.getInt("id"));
            j.setNome(linhas.getString("nome"));
            
            lista.add(j);
        }
        
        return lista;
    }
    
}
