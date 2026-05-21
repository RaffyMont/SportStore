package it.unisa.storage.model;

public class ScarpeBean extends ProdottoBean{

	private static final long serialVersionUID = 1L;
	
	private int id_scarpa;
	private String tipo_suola;
	private String materiale;
	
	public ScarpeBean()
	{
		super();
	}
	
	public int getId_scarpa() {
		return id_scarpa;
	}

	public void setId_scarpa(int id_scarpa) {
		this.id_scarpa = id_scarpa;
	}

	public String getTipo_suola() {
		return tipo_suola;
	}

	public void setTipo_suola(String tipo_suola) {
		this.tipo_suola = tipo_suola;
	}

	public String getMateriale() {
		return materiale;
	}

	public void setMateriale(String materiale) {
		this.materiale = materiale;
	}
	
	
}
