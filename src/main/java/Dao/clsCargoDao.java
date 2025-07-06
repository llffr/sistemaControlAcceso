/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Modelo.Cargo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Source;
import vista.Asistencia;
import vista.frmCargo;
import vista.frmPersona;

public class clsCargoDao {

	clsConexion con = new clsConexion();
	Connection cn = con.mysqlConnection();
	ResultSet rs;

	public void ListarPersonas() {
		DefaultTableModel tabla = new DefaultTableModel();
		//define columnas
		String[] titulo = {"DNI", "Nombre", "Apellidos", "Telefono", "Cargo"};
		tabla = new DefaultTableModel(null, titulo);

		try {
			CallableStatement cst = cn.prepareCall("{call pa_listarPersonal}");
			rs = cst.executeQuery();
			while (rs.next()) {
				//num de columnas
				Object dato[] = new Object[5];
				for (int i = 0; i < 5; i++) {
					dato[i] = rs.getString(i + 1);
				}
				tabla.addRow(dato);
			}
			frmPersona.tbPersonal.setModel(tabla);
		} catch (Exception e) {
			e.toString();
		}
	}

	public void ListarCategoria() {
		DefaultTableModel tabla = new DefaultTableModel();
		//define columnas
		String[] titulo = {"id", "Nombre del Cargo"};
		tabla = new DefaultTableModel(null, titulo);

		try {
			CallableStatement cst = cn.prepareCall("{call pa_listarcargo}");
			rs = cst.executeQuery();
			while (rs.next()) {
				//num de columnas
				Object dato[] = new Object[2];
				for (int i = 0; i < 2; i++) {
					dato[i] = rs.getString(i + 1);
				}
				tabla.addRow(dato);
			}
			frmCargo.jTable1.setModel(tabla);
		} catch (Exception e) {
			e.toString();
		}
	}

	public void InsertarCargo(Cargo c) {
		try {
			CallableStatement cst = cn.prepareCall("{call sp_registrarCargo(?)}");
			cst.setString(1, c.getNOMCARGO());
			int rpt = cst.executeUpdate();
			if (rpt == 1) {
				JOptionPane.showMessageDialog(null, "Registro Correctamente...", "Sistemas", JOptionPane.INFORMATION_MESSAGE);
			}
			ListarCategoria();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void EliminarCargo(Cargo c) {
		try {
			CallableStatement cst = cn.prepareCall("{call eliminarCargo(?)}");
			cst.setInt(1, c.getIDCARGO());
			int rpt = cst.executeUpdate();
			if (rpt == 1) {
				JOptionPane.showMessageDialog(null, "Registro Correctamente...", "Sistemas", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	// estacionamiento
	public void spaceAvailables() {
		DefaultTableModel tabla = new DefaultTableModel();
		String[] titulo = {"CODIGO", "LUGAR"};
		tabla = new DefaultTableModel(null, titulo);

		try {
			String query = "SELECT CODIGO, " +
				"CASE WHEN ESTADO = 1 THEN 'Disponible' ELSE 'Ocupado' END AS LUGAR " +
				"FROM ESPACIO_ESTACIONAMIENTO";

			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Object dato[] = new Object[2];
				dato[0] = rs.getString("CODIGO");
				dato[1] = rs.getString("LUGAR");

				tabla.addRow(dato);
			}

			// add to table
			Asistencia.tableSpaces.setModel(tabla);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
