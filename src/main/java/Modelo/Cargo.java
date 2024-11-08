/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

//datos de tabla CARGO 
public class Cargo {
	private int IDCARGO;
	private String NOMCARGO;

	public Cargo() {
	}

	public Cargo(int idcargo, String nomCargo) {
		this.IDCARGO = idcargo;
		this.NOMCARGO = nomCargo;
	}

	public int getIDCARGO() {
		return IDCARGO;
	}

	public void setIDCARGO(int IDCARGO) {
		this.IDCARGO = IDCARGO;
	}

	public String getNOMCARGO() {
		return NOMCARGO;
	}

	public void setNOMCARGO(String NOMCARGO) {
		this.NOMCARGO = NOMCARGO;
	}
	
}
