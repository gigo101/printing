/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ppp
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

public class globalmethods {
    
Connection conn=null;
CallableStatement cStmt=null;
boolean hadresult;
ResultSet rs=null;
String sql=null;
PreparedStatement pst=null;

    public globalmethods() {
        conn=javaconnect.ConnecrDb();
    }
    
            
    public void filltable(JTable a,String name){
        try {
            //sql="select * from student";
            //pst=conn.prepareStatement(sql);
            //rs=pst.executeQuery();
            cStmt = conn.prepareCall("{call proc_querystuds(?)}");
            cStmt.setString(1, name);
            hadresult=cStmt.execute();
            rs = cStmt.getResultSet();
            a.setModel(DbUtils.resultSetToTableModel(rs));     
          
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }  
    }
    
    public void fillcombo(JComboBox combo){
        try{
            cStmt = conn.prepareCall("{call proc_querysemesyear()}");
            hadresult=cStmt.execute();
            rs = cStmt.getResultSet();
            while(rs.next()){
                String semester= rs.getString("semester");
                combo.addItem(semester);
            }          
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);                
        } finally{
            try {
                rs.close();
                pst.close();
                
            } catch (Exception e) {
            }
        }

    }
   
}
