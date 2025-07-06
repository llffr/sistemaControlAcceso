/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Modelo.Espacio_Estacionamiento;
import Modelo.PersonaMod;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.frmConsultaMarcacion;
import vista.frmPersona;

public class clsPersonaDao {

	clsConexion cn = new clsConexion();
	Connection con = cn.mysqlConnection();
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
			rs = st.executeQuery("select * from CARGO");
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
			//establece columnas de la tabla
			String[] titulo = {"dni", "nombre", "apellidos",
				"telefono", "cargo"};
			CallableStatement cst = con.prepareCall("{call pa_buscarPersonalApellido(?)}");
			DefaultTableModel model = new DefaultTableModel(null, titulo);
			frmPersona.tbPersonal.setModel(model);

			cst.setString(1, p.getDNI());
			rs = cst.executeQuery();
			String[] fila = new String[5];

			//añade nombre de columnas a la tabla
			while (rs.next()) {
				fila[0] = rs.getString(1);
				fila[1] = rs.getString(2);
				fila[2] = rs.getString(3);
				fila[3] = rs.getString(4);
				fila[4] = rs.getString(5);
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

	// estacionamienoto: lugar y dni
	public void chooseParking(PersonaMod p, Espacio_Estacionamiento parking) {
		try {
			//tb persona, espacio_estacionamiento
			CallableStatement cst = con.prepareCall("{call sp_ingresarEstacionamiento(?, ?)}");
			cst.setString(1, p.getDNI());
			cst.setInt(2, parking.getIDESPACIO());

			int rpt = cst.executeUpdate();

			//evalua si se ingreso datos
			if (rpt == 1) {
				JOptionPane.showMessageDialog(null, "Registrado Correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: " + e);
		}
	}

	public void exitParking(PersonaMod p) {
		try {
			//tb persona, espacio_estacionamiento
			CallableStatement cst = con.prepareCall("{call sp_salirEstacionamiento(?)}");
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

	public boolean hasActiveEntry(String dni) {
		boolean result = false;
		try {
			PreparedStatement ps = con.prepareStatement(
					"SELECT COUNT(*) FROM CONTROLACCESO WHERE DNI = ? AND H_SALIDA IS NULL");
			ps.setString(1, dni);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al verificar ingreso activo: " + e.getMessage());
		}
		return result;
	}
	// end parking

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

	public void consultaxFecha(PersonaMod usu) {
		try {
			String[] titulo = {"DNI", "APELLIDOS y NOMBRES", "FECHA", "HG1", "HS1", "HI2", "HS2", "HORAS TOTALES"};
			CallableStatement cst = con.prepareCall("{call pa_consultaAsistenciaxFecha(?,?,?)}");
			DefaultTableModel modelo = new DefaultTableModel(null, titulo);
			frmConsultaMarcacion.jTable1.setModel(modelo);
			cst.setString(1, usu.getF1());
			cst.setString(2, usu.getF2());
			cst.setString(3, usu.getAPELLIDOS());

			rs = cst.executeQuery();
			String[] fila = new String[8];
			while (rs.next()) {
				fila[0] = rs.getString(1);
				fila[1] = rs.getString(2);
				fila[2] = rs.getString(3);
				fila[3] = rs.getString(4);
				fila[4] = rs.getString(5);
				fila[5] = rs.getString(6);
				fila[6] = rs.getString(7);
				fila[7] = rs.getString(8);
				//ingresa los nombres de las columnas 
				modelo.addRow(fila);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}

}
