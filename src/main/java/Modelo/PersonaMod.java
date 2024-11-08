/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


//datos de tabla Persona
public class PersonaMod {
	private int IDPER;
	private String DNI;
	private String NOMBRE;
	private String APELLIDOS;
	private String TELF;
	private int IDCARGO;
	private int ESTADO;
	private String CLAVE;

	public PersonaMod() {
	}

	public PersonaMod(int IDPER, String DNI, String NOMBRE, String APELLIDOS, String TELF, int IDCARGO, int ESTADO, String CLAVE) {
		this.IDPER = IDPER;
		this.DNI = DNI;
		this.NOMBRE = NOMBRE;
		this.APELLIDOS = APELLIDOS;
		this.TELF = TELF;
		this.IDCARGO = IDCARGO;
		this.ESTADO = ESTADO;
		this.CLAVE = CLAVE;
	}

	public int getIDPER() {
		return IDPER;
	}

	public void setIDPER(int IDPER) {
		this.IDPER = IDPER;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public String getNOMBRE() {
		return NOMBRE;
	}

	public void setNOMBRE(String NOMBRE) {
		this.NOMBRE = NOMBRE;
	}

	public String getAPELLIDOS() {
		return APELLIDOS;
	}

	public void setAPELLIDOS(String APELLIDOS) {
		this.APELLIDOS = APELLIDOS;
	}

	public String getTELF() {
		return TELF;
	}

	public void setTELF(String TELF) {
		this.TELF = TELF;
	}

	public int getIDCARGO() {
		return IDCARGO;
	}

	public void setIDCARGO(int IDCARGO) {
		this.IDCARGO = IDCARGO;
	}

	public int getESTADO() {
		return ESTADO;
	}

	public void setESTADO(int ESTADO) {
		this.ESTADO = ESTADO;
	}

	public String getCLAVE() {
		return CLAVE;
	}

	public void setCLAVE(String CLAVE) {
		this.CLAVE = CLAVE;
	}
}
