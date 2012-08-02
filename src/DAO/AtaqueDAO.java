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
    
    public static ArrayList<Ataque> getAtaque(int id) {
        
        ArrayList<Ataque> lista = new ArrayList<Ataque>();
        
        MySQL banco = new MySQL();
        String sql = "select * from ataque where id = "+id;
        
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
    
    
    public static Ataque getAtaque(String nome) {
        
        MySQL banco = new MySQL();
        String sql = "select * from ataque where nome = \""+nome+"\"";
        
        System.out.println(sql);
        
        ConjuntoResultados linhas = banco.executaSelect(sql);
        
        Ataque a = new Ataque();
        
        if(linhas.next()){
            a.setId(linhas.getInt("id"));
            a.setNome(linhas.getString("nome"));
            a.setAtk(linhas.getInt("atk"));
            a.setElemento(linhas.getInt("elemento"));
  
        }
        
        return a;
        
    }
    
    
}
