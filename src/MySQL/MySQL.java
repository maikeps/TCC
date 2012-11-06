package MySQL;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class MySQL
{
    Statement statement;    
    String user = "Admin";
    String pass = "pokeproject";
    String host = "data/BancoDados.";	
   
    public MySQL()
    {	   
       String url = "jdbc:hsqldb:file:"+this.host;
       try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            Connection conn = DriverManager.getConnection(url,user,pass);
            statement = conn.createStatement();
        } catch (ClassNotFoundException e){            
			JOptionPane.showMessageDialog(null, "Driver HSQL nao encontrado");
        } catch (SQLException e){            
			JOptionPane.showMessageDialog(null, "Erro na conexao com a base de dados:\n\n"+e);
        } 
       
    }

    public boolean executaInsert(String insert)
    {
        try {
            insert = insert.replace("\"", "'");            
            statement.execute(insert);
            return true;
        } catch (SQLException e){
            System.out.println("Erro na InclusÃ¯Â¿Â½o "+e);
            return false;
        }
    }
    
    public boolean executaUpdate(String update)
    {
        try {
            update = update.replace("\"", "'");  
            statement.execute(update);
            return true;
        } catch (SQLException e){
            System.out.println("Erro na InclusÃ¯Â¿Â½o "+e);
            return false;
        }
    }
    
    public boolean executaDelete(String delete)
    {
        try {
            delete = delete.replace("\"", "'");  
            statement.execute(delete);
            return true;
        } catch (SQLException e){
            System.out.println("Erro na exclusÃ¯Â¿Â½o "+e);
            return false;
        }
    }
    
    public ConjuntoResultados executaSelect(String select){
        try {
            select = select.replace("\"", "'");  
            ResultSet rs = statement.executeQuery(select);
            ConjuntoResultados cr = new ConjuntoResultados(rs);
            return cr;
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, ""+e, "Erro no SELECT", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }


	/**
    * Retorna o caminho onde a aplicaÃ§Ã£o estÃ¡ sendo executada
    * @return caminho da aplicaÃ§Ã£o
    */
   private String getApplicationPath() {
      String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath();
        File dir = new File(url).getParentFile();
        String path = null;

        if (dir.getPath().contains(".jar"))
            path = findJarParentPath(dir);
        else
            path = dir.getPath();

        try {
            return URLDecoder.decode(path, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return path.replace("%20", " ");
        }
   }

   
   private String findJarParentPath(File jarFile) {
        while (jarFile.getPath().contains(".jar"))
           jarFile = jarFile.getParentFile();
            
        return jarFile.getPath().substring(6);
    }
    
}
