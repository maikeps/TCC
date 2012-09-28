package MySQL;

import java.sql.*; 

public class MySQL
{
    Statement statement;
    String user = "root";
    String pass = "7huy7119";
    String database = "tcc";
    String host = "localhost";
   
    public MySQL()
    {
       String url = "jdbc:mysql://"+host+":3306/"+database;             

       try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,pass);
            statement = conn.createStatement();
        } catch (ClassNotFoundException e){
            System.out.println("Driver MySQL não encontrado.");
            System.exit(0);
        } catch (SQLException e){
            System.out.println("Erro na conexão com a base de dados: "+e);
        } 
       
       this.executaUpdate("set global max_connections = 1000;");
    }

    public boolean executaInsert(String insert)
    {
        try {
            statement.execute(insert);
            return true;
        } catch (SQLException e){
            System.out.println("Erro na Inclusão "+e);
            System.out.println("O comando sql executado foi: "+insert);
            return false;
        }
    }
    
    public boolean executaUpdate(String insert)
    {
        try {
            statement.execute(insert);
            return true;
        } catch (SQLException e){
            System.out.println("Erro na Inclusão "+e);
            return false;
        }
    }
    
    public boolean executaDelete(String delete)
    {
        try {
            statement.execute(delete);
            return true;
        } catch (SQLException e){
            System.out.println("Erro na exclusão "+e);
            return false;
        }
    }
    
    public ConjuntoResultados executaSelect(String select)
    {
        try {
            ResultSet rs = statement.executeQuery(select);
            ConjuntoResultados cr = new ConjuntoResultados(rs);
            return cr;
        } catch (SQLException e){
            System.out.println("Erro no SELECT "+e);
            return null;
        }
    }       
    
}
