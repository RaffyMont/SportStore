package it.unisa.storage.model;

import java.io.Serializable;

public class ImmagineBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String pathname;
	private String mime_type;
	private ProdottoBean id_prodotto;
	
	public ImmagineBean()
	{
		
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public String getMime_type() {
		return mime_type;
	}

	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}

	public ProdottoBean getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(ProdottoBean id_prodotto) {
		this.id_prodotto = id_prodotto;
	}
	
	public boolean hasImage() {
        return pathname != null && !pathname.isEmpty();
    }
}
