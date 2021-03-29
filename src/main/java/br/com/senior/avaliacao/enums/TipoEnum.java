package br.com.senior.avaliacao.enums;

public enum TipoEnum {
	
	PRODUTO("Produto"),
	SERVICO("Servico");
	
	private String descricao;
	
	private TipoEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoEnum toEnum(String descricao) {
		if(descricao == null)
			return null;
		for(TipoEnum x : TipoEnum.values()) {
			if(descricao.equals(x.getDescricao()))
				return x;
		}
		throw new IllegalArgumentException("Descrição tipo Inválido: " + descricao);
	}
}