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
	String usuario = "sa";
	String passwd = "root";

	//conexion sql server
	public Connection ConectarDB() {
		try {
			String cadena = "jdbc:sqlserver://localhost:1433;databaseName=SISTEMACONTROLACCESO;"
				+ "trustServerCertificate=true";
			conn = DriverManager.getConnection(cadena, usuario, passwd);
			//JOptionPane.showMessageDialog(null, "conexion exitosa");
			System.out.println("conexion exitosa");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "no conexion exitosa" + e.toString());
		}
		return conn;
	}

	public static void main(String[] args) {
		clsConexion tt = new clsConexion();
		tt.ConectarDB();
	}
}
