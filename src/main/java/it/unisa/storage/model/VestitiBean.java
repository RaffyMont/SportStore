package it.unisa.storage.model;

public class VestitiBean extends ProdottoBean{

	private static final long serialVersionUID = 1L;
	
	private String tipovita;
	private String tessuto;
	private String stagione;
	public enum CategoriaVestiti{MAGLIETTA, PANTALONE, TUTA};
	private CategoriaVestiti categoria_vestito;
	private String tipo_collo;
	public enum Manica{CORTA, LUNGA};
	private Manica manica;
	public enum Gamba{CORTI, LUNGHI};
	private Gamba gamba;
   
	public VestitiBean()
	{
		
	}

	public String getTipovita() {
		return tipovita;
	}

	public void setTipovita(String tipovita) {
		this.tipovita = tipovita;
	}

	public String getTessuto() {
		return tessuto;
	}

	public void setTessuto(String tessuto) {
		this.tessuto = tessuto;
	}

	public String getStagione() {
		return stagione;
	}

	public void setStagione(String stagione) {
		this.stagione = stagione;
	}

	public CategoriaVestiti getCategoriaVestito() {
		return categoria_vestito;
	}

	public void setCategoriaVestito(CategoriaVestiti categoria_vestito) {
		this.categoria_vestito = categoria_vestito;
	}

	public String getTipo_collo() {
		return tipo_collo;
	}

	public void setTipo_collo(String tipo_collo) {
		this.tipo_collo = tipo_collo;
	}

	public Manica getManica() {
		return manica;
	}

	public void setManica(Manica manica) {
		this.manica = manica;
	}

	public Gamba getGamba() {
		return gamba;
	}

	public void setGamba(Gamba gamba) {
		this.gamba = gamba;
	}
	
	

}
