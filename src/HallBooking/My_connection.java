package HallBooking;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chiranth
 */
public class My_connection {
    
    public Connection createConnection()
    {
        Connection connection = null;
        
        MysqlDataSource mds= new MysqlDataSource();
        
        mds.setServerName("localhost");
        mds.setPortNumber(3306);
        mds.setUser("root");
        mds.setPassword("");
        mds.setDatabaseName("hall_booking_db");
        
        try {
            connection = mds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(My_connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
        
       
    }
}
