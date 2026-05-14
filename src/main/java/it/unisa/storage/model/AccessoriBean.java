package it.unisa.storage.model;

public class AccessoriBean extends ProdottoBean{

	private static final long serialVersionUID = 1L;

	private String tipo_accessori;
	private String materiali;
	
	public AccessoriBean()
	{
		
	}

	public String getTipo_accessori() {
		return tipo_accessori;
	}

	public void setTipo_accessori(String tipo_accessori) {
		this.tipo_accessori = tipo_accessori;
	}

	public String getMateriali() {
		return materiali;
	}

	public void setMateriali(String materiali) {
		this.materiali = materiali;
	}
	
	
}
