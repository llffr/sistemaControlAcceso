/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Modelo.PersonaMod;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.frmPersona;

public class clsPersonaDao {

	clsConexion cn = new clsConexion();
	Connection con = cn.ConectarDB();
	ResultSet rs;

	//login
	public boolean AccesoSistema(PersonaMod usu) {
		PreparedStatement ps = null;
		//DNI y clave se especifican por el ususario
		String sql = "select DNI,clave from PERSONA where DNI=? and clave=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, usu.getDNI());
			ps.setString(2, usu.getCLAVE());
			rs = ps.executeQuery();

			if (rs.next()) {
				if (usu.getDNI().equals(rs.getString(1)) && usu.getCLAVE().equals(rs.getString(2))) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}

	//"internal jFrame Persona" muestra todos los cargos en comboBox
	public void llenarCargo() {
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery("select * from cargo");
			frmPersona.cmbCargo.addItem("Seleccione cargo");
			while (rs.next()) {
				frmPersona.cmbCargo.addItem(rs.getString("NOMCARGO"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	//"internal jFrame Persona". busqueda por apellido 
	public void BuscarPersonalDao(PersonaMod p) {
		try {
			String[] titulo = {"id", "dni", "nombre", "apellidos",
				"telefono", "cargo"};
			CallableStatement cst = con.prepareCall("{call pa_filtrarDatosPersonal(?)}");
			DefaultTableModel model = new DefaultTableModel(null, titulo);
			frmPersona.tbPersonal.setModel(model);

			cst.setString(1, p.getDNI());
			rs = cst.executeQuery();
			String[] fila = new String[6];

			while (rs.next()) {
				fila[0] = rs.getString(1);
				fila[1] = rs.getString(2);
				fila[2] = rs.getString(3);
				fila[3] = rs.getString(4);
				fila[4] = rs.getString(5);
				fila[5] = rs.getString(6);
				model.addRow(fila);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void registrarPersonal(PersonaMod p) {
		try {
			CallableStatement cst = con.prepareCall("{call sp_registrarPersonal(?, ?, ?, ?, ?, ?)}");
			cst.setString(1, p.getDNI());
			cst.setString(2, p.getNOMBRE());
			cst.setString(3, p.getAPELLIDOS());
			cst.setString(4, p.getTELF());
			cst.setInt(5, p.getIDCARGO());
			cst.setString(6, p.getCLAVE());

			int rpt = cst.executeUpdate();

			//evalua si se ingreso datos
			if (rpt == 1) {
				JOptionPane.showMessageDialog(null, "Registrado Correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e);
		}
	}

	//investigar keypress event
	public void marcarAsistencia(PersonaMod p) {
		try {
			CallableStatement cst = con.prepareCall("{call sp_registroAsistenciaPersonal(?)}");
			cst.setString(1, p.getDNI());

			int rpt = cst.executeUpdate();

			//evalua si se ingreso datos
			if (rpt == 1) {
				JOptionPane.showMessageDialog(null, "Registrado Correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e);
		}
	}

	public void actualizarPersonal(PersonaMod p) {
		try {
			CallableStatement cst = con.prepareCall("{call sp_actualizarPersonal(?, ?, ?, ?, ?, ?)}");
			cst.setString(1, p.getDNI());
			cst.setString(2, p.getNOMBRE());
			cst.setString(3, p.getAPELLIDOS());
			cst.setString(4, p.getTELF());
			cst.setInt(5, p.getIDCARGO());
			cst.setString(6, p.getCLAVE());

			int rpt = cst.executeUpdate();

			//evalua si se ingreso datos
			if (rpt == 1) {
				JOptionPane.showMessageDialog(null, "Registrado Correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e);
		}
	}

	public void eliminarPersonal(PersonaMod p) {
		try {
			CallableStatement cst = con.prepareCall("{call pa_eliminarPersona(?)}");
			cst.setString(1, p.getDNI());

			int rpt = cst.executeUpdate();

			//evalua si se ingreso datos
			if (rpt == 1) {
				JOptionPane.showMessageDialog(null, "Eliminado Correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e);
		}
	}

}
