package HallBooking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chiranth
 */
public class BOOKING {
    
    //--> alter TABLE bookings ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id) on DELETE CASCADE;
    //--> alter TABLE bookings ADD CONSTRAINT fk_hall_number FOREIGN KEY (hall_number) REFERENCES halls(hall_no) on DELETE CASCADE;
    //--> alter TABLE halls ADD CONSTRAINT fk_type_id FOREIGN KEY (type) REFERENCES type(id) on DELETE CASCADE;
    
    
    
    My_connection my_connection = new My_connection();
    HALLS hall = new HALLS();
    

    //Function to add a new booking
    public boolean addBooking(int customer_id, int hall_number, String dateIn, String dateOut)
    {
        PreparedStatement st;
        ResultSet rs;
        String addQuery = "INSERT INTO `bookings`(`customer_id`, `hall_number`, `date_in`, `date_out`) VALUES (?,?,?,?)";
        
        try {
            st = my_connection.createConnection().prepareStatement(addQuery);
            
            st.setInt(1, customer_id);
            st.setInt(2, hall_number);
            st.setString(3, dateIn);
            st.setString(4, dateOut);
            
            if(hall.isHallToBooked(hall_number).equals("NO")){
                if (st.executeUpdate() > 0)
                {
                    hall.setHallToBooked(hall_number, "YES");
                    return true;
                
                }else{
                    return false;
                }
            }else{
                
                JOptionPane.showMessageDialog(null, "This Hall is Alredy Booked","Hall Booked", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            }
            catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
    
    //Function to edit booking
    public boolean editBooking(int booking_id, int customer_id, int hall_number, String dateIn, String dateOut)
    {
        PreparedStatement st;
        ResultSet rs;
        String editQuery = "UPDATE `bookings` SET `customer_id`=?,`hall_number`=?,`date_in`=?,`date_out`=? WHERE `id`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(editQuery);
            
            st.setInt(1, customer_id);
            st.setInt(2, hall_number);
            st.setString(3, dateIn);
            st.setString(4, dateOut);
            st.setInt(5, booking_id);
            
            
            return (st.executeUpdate() > 0);
            
            }
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //function to remove selected booking
    public boolean removeBooking(int booking_id)
    {
        PreparedStatement st;
        ResultSet rs;
        String deleteQuery = "DELETE FROM `bookings` WHERE `id`=?";
        
        try {
            st = my_connection.createConnection().prepareStatement(deleteQuery);
            
            st.setInt(1, booking_id);
            
            //hall number before deleting the booking
            int hall_number = getHallNumberFromBooking(booking_id);
            
            if (st.executeUpdate() > 0)
            {
                hall.setHallToBooked(hall_number, "NO");
                return true;
                
            }else{
                return false;
            }
            
            }
        catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    //Function to display halls
    public void fillBookingJTable(JTable table)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT * FROM `bookings`";
        
        try {
            
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            
            rs = ps.executeQuery();
            
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                
                tableModel.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //Function to get the hall number from a booking
    public int getHallNumberFromBooking(int bookingID)
    {
        PreparedStatement ps;
        ResultSet rs;
        String selectQuery = "SELECT `hall_number` FROM `bookings` WHERE `id`=?";
        
        try {
            
            ps = my_connection.createConnection().prepareStatement(selectQuery);
            
            ps.setInt(1, bookingID);
            
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                return rs.getInt(1);
            }else{
                return 0;
            }
          
        }catch (SQLException ex) {
            Logger.getLogger(CUSTOMER.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    } 
        
}
