package HallBooking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Chiranth
 */
public class HALLS {
    
    My_connection my_connection =new My_connection();
    
    //Function to display halls
    public void fillHallsJTable(JTable table)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT * FROM `halls`";
        
        try {
            
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                
                tableModel.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Function to display halls
    public void fillHall_TypeJTable(JTable table)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT * FROM `type`";
        
        try {
            
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[3];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                
                tableModel.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //Function to fill a combobox with the hall_type id
    public void fillHall_TypeJCombobox(JComboBox combobox)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT * FROM `type`";
        
        try {
            
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            while(rs.next())
            {
               combobox.addItem(rs.getInt(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Function to add a new hall
    public boolean addHall(int number, int type, String phone)
    {
        PreparedStatement st;
        ResultSet rs;
        String addQuery = "INSERT INTO `halls`(`hall_no`, `type`, `phone`, `booking`) VALUES (?,?,?,?)";
        
        try {
            st = my_connection.createConnection().prepareStatement(addQuery);
            
            st.setInt(1, number);
            st.setInt(2, type);
            st.setString(3, phone);
            //when we add a new hall
            //the booked column will be set to no
            //the booked column mean i this hall is free or not
            st.setString(4, "NO");
            
            return (st.executeUpdate() > 0);
            
            } 
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
           
    }

    //Function to edit Room
    public boolean editHall(int number,int type, String phone, String isBooked)
    {
        PreparedStatement st;
        ResultSet rs;
        String editQuery = "UPDATE `halls` SET `type`=?,`phone`=?,`booking`=? WHERE `hall_no`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(editQuery);
            
            st.setInt(1, type);
            st.setString(2, phone);
            st.setString(3, isBooked);
            st.setInt(4, number);
            
            return (st.executeUpdate() > 0);
            
            }
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //function to remove selected clint Hall
    public boolean removeHall(int roomNumber)
    {
        PreparedStatement st;
        ResultSet rs;
        String deleteQuery = "DELETE FROM `halls` WHERE `hall_no`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(deleteQuery);
            
            st.setInt(1, roomNumber);
            
            return (st.executeUpdate() > 0);
            
            }
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //function to set halls to reserved or not
    //Function to edit Room
    public boolean setHallToBooked(int number, String isBooked)
    {
        PreparedStatement st;
        String editQuery = "UPDATE `halls` SET `booking`=? WHERE `hall_no`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(editQuery);
            
            st.setString(1, isBooked);
            st.setInt(2, number);
            
            return (st.executeUpdate() > 0);
            
            }
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //Function to check if room is already reserved
    public String isHallToBooked(int number)
    {
        PreparedStatement st;
        ResultSet rs;
        String editQuery = "SELECT `booking` FROM `halls` WHERE `hall_no`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(editQuery);
            
            st.setInt(1, number);
            
            rs = st.executeQuery();
            
            if(rs.next())
            {
                return rs.getString(1);
            }
            else{
                return "";
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    
    
}
