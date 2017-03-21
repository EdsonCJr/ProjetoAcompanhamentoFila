package br.com.acompanhamentofila.enumeration;

public enum StatusChamado {

	AGUARDANDO_INTERVENCAO,
	AGUARDANDO_RETORNO_DO_USUARIO,
	DESIGNADO,
	ATRIBUIDO,
	EM_PROCESSAMENTO,
	REATRIBUIDO,
	RESOLVIDO;
	
	@Override
	public String toString() {
		
		switch (this){
		case AGUARDANDO_INTERVENCAO:
			return "Aguardando intervenção";
		case AGUARDANDO_RETORNO_DO_USUARIO:
			return "Aguardando Retorno do Usuário";
		case DESIGNADO:
			return "Designado";
		case ATRIBUIDO:
			return "Atribuido";
		case EM_PROCESSAMENTO:
			return "Em processamento";
		case REATRIBUIDO:
			return "Reatribuido";
		case RESOLVIDO:
			return "Resolvido";
		default:
			return null;
		}
	}

}
