package it.unisa.storage.model;

/* DA RIVEDERE MEGLIO */
public class ScarpeBean extends ProdottoBean{

	private static final long serialVersionUID = 1L;
	
	private String tipo_suola;
	private String materiale;
	
	public ScarpeBean()
	{
		super();
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
