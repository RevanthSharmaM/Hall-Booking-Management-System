package HallBooking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chiranth
 */
public class CUSTOMER {
    
    My_connection my_connection = new My_connection();
    
    //function to add customer
    public boolean addCustomer(String fname, String lname, String phone, String email)
    {
        PreparedStatement st;
        ResultSet rs;
        String addQuery = "INSERT INTO `customer`(`First_name`, `Last_name`, `Phone`, `Email`) VALUES (?,?,?,?)";
        
        try {
            st = my_connection.createConnection().prepareStatement(addQuery);
            
            st.setString(1, fname);
            st.setString(2, lname);
            st.setString(3, phone);
            st.setString(4, email);
            
            return (st.executeUpdate() > 0);
            
            } 
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
           
    }
    
    //function to edit the select clints
    public boolean editCustomer(int id, String fname, String lname, String phone, String email)
    {
        PreparedStatement st;
        ResultSet rs;
        String editQuery = "UPDATE `customer` SET `First_name`=?,`Last_name`=?,`Phone`=?,`Email`=? WHERE `id`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(editQuery);
            
            st.setString(1, fname);
            st.setString(2, lname);
            st.setString(3, phone);
            st.setString(4, email);
            st.setInt(5, id);
            
            return (st.executeUpdate() > 0);
            
            }
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //function to remove selected clint
    public boolean removeCustomer(int id)
    {
        PreparedStatement st;
        ResultSet rs;
        String deleteQuery = "DELETE FROM `customer` WHERE `id`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(deleteQuery);
            
            st.setInt(1, id);
            
            return (st.executeUpdate() > 0);
            
            }
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    //populate jtable with all the clints is the database
    public void fillcustomerJTable(JTable table)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT * FROM `customer`";
        
        try {
            
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                
                tableModel.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
