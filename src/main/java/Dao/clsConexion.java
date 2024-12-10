/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import vista.frmLogin;

public class clsConexion {
    Connection conn = null;

    public Connection ConectarDB() {
        String user = "sa";
        String passwd = "root";
        try {
            String cadena = "jdbc:sqlserver://localhost:1433;databaseName=SISTEMACONTROLACCESO;"
                + "trustServerCertificate=true";
            conn = DriverManager.getConnection(cadena, user, passwd);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No connection");
        }
        return conn;
    }

    public Connection mysqlConnection() {
        String user = "";
        String passwd = "";

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/SISTEMACONTROLACCESO?user=" + user + "&password=" + passwd);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No connection");
            e.printStackTrace();
            System.exit(0);
        }
        return conn;
    }

    public static void main(String[] args) {
        clsConexion tt = new clsConexion();
        tt.mysqlConnection();
    }
}
