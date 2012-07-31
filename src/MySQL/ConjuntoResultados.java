package MySQL;

import java.sql.*; 

public class ConjuntoResultados
    {
        private ResultSet rs;
        
        public ConjuntoResultados(ResultSet rs){
            this.rs = rs;
        }
        
        public boolean next(){
            try{
                return rs.next();
            }catch(SQLException sqlError){
                System.out.println("Erro no SQL: "+sqlError);
                return false;
            }
        }
        
        public String getString(String t){
            try{
                return rs.getString(t);
            }catch(SQLException sqlError){
                System.out.println("Erro no SQL: "+sqlError);
                return "";
            }
        }
        
        public int getInt(String t){
            try{
                return rs.getInt(t);
            }catch(SQLException sqlError){
                System.out.println("Erro no SQL: "+sqlError);
                return 0;
            }
        }
        
        public double getDouble(String t){
            try{
                return rs.getDouble(t);
            }catch(SQLException sqlError){
                System.out.println("Erro no SQL: "+sqlError);
                return 0;
            }
        }
    }