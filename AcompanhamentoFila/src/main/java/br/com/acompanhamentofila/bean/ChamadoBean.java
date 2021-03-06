package br.com.acompanhamentofila.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;


import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.acompanhamentofila.dao.ChamadoDAO;
import br.com.acompanhamentofila.dao.OperadorDAO;
import br.com.acompanhamentofila.dao.StatusChamadoDAO;
import br.com.acompanhamentofila.domain.Chamado;
import br.com.acompanhamentofila.domain.Operador;
import br.com.acompanhamentofila.domain.StatusChamado;


@SuppressWarnings("serial")
@ManagedBean(name = "chamadoMB")
@ViewScoped
public class ChamadoBean implements Serializable {

	private Chamado chamado;
	private Chamado chamadoSelecionado;
	private Operador operador;
	private StatusChamado statusChamado;
	private List<Chamado> listaDeChamados;
	private List<Operador> listaDeOperadores;
	private List<Chamado> chamadosFiltrados;
	private List<String> listaDeCriticidade;
	private List<String> listaDeSistemas;
	private List<StatusChamado> listaDeStatusChamado;
	

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamadoSelecionado(Chamado chamadoSelecionado) {
		this.chamadoSelecionado = chamadoSelecionado;
	}

	public Chamado getChamadoSelecionado() {
		return chamadoSelecionado;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	public Operador getOperador() {
		return operador;
	}
	
	public void setStatusChamado(StatusChamado statusChamado) {
		this.statusChamado = statusChamado;
	}
	
	public StatusChamado getStatusChamado() {
		return statusChamado;
	}

	public List<Chamado> getListaDeChamados() {
		return listaDeChamados;
	}

	public void setListaDeChamados(List<Chamado> listaDeChamados) {
		this.listaDeChamados = listaDeChamados;
	}

	public void setListaDeOperadores(List<Operador> listaDeOperadores) {
		this.listaDeOperadores = listaDeOperadores;
	}

	public List<Operador> getListaDeOperadores() {
		return listaDeOperadores;
	}

	public void setChamadosFiltrados(List<Chamado> chamadosFiltrados) {
		this.chamadosFiltrados = chamadosFiltrados;
	}

	public List<Chamado> getChamadosFiltrados() {
		return chamadosFiltrados;
	}

	public void setListaDeCriticidade(List<String> listaDeCriticidade) {
		this.listaDeCriticidade = listaDeCriticidade;
	}

	public List<String> getListaDeCriticidade() {
		return listaDeCriticidade;
	}
	
	public void setListaDeSistemas(List<String> listaDeSistemas) {
		this.listaDeSistemas = listaDeSistemas;
	}
	
	public List<String> getListaDeSistemas() {
		return listaDeSistemas;
	}
	
	public void setListaDeStatusChamado(List<StatusChamado> listaDeStatusChamado) {
		this.listaDeStatusChamado = listaDeStatusChamado;
	}
	
	public List<StatusChamado> getListaDeStatusChamado() {
		return listaDeStatusChamado;
	}
	

	public void novo() {
		chamado = new Chamado();
		operador = new Operador();
		statusChamado = new StatusChamado();
	}

	public void carregar() {

		try {

			// Caminho da origem do arquivo .csv temporário
			Path src = Paths.get(chamado.getCaminho());

			// Caminho de destino onde o arquivo .cvs será salvo
			Path dest = Paths.get("C:/Desenvolvimento/Upload/planilhaUpl.csv");

			// copiando o arquivo da origem para o destino
			Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);

		} catch (

		IOException exception) {
			Messages.addGlobalError("Ocorreou um erro ao carregar a lita de chamados");
			exception.printStackTrace();
		}

	}

	public void salvar() {

		/*
		 * Método carregar recupera o caminho (Path) do arquivo temporário
		 * Defini um caminho de destino para o arquivo temporário e copia o
		 * arquivo temporário do caminho de origem para o destino, tornando o
		 * arquivo definitivo.
		 */
		carregar();

		/*
		 * Criando um obj de acesso ao bando de dados
		 */
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		OperadorDAO operadorDAO = new OperadorDAO();
		StatusChamadoDAO statusChamadoDAO = new StatusChamadoDAO();

		/*
		 * Criando um obj File apontando para o arquivo .csv utilizano o caminho
		 * completo
		 * 
		 */
		File file = new File("C:/Desenvolvimento/Upload/planilhaUpl.csv");

		/*
		 * Obj SimpleDateFormat criado para formatar as datas recebidas no
		 * arquivo .csv para salvar no banco de dados corretamente
		 */
		SimpleDateFormat fmtEnUs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmtPtBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		try {

			// Definindo um obj scanner para minipular o arquivo
			Scanner scanner = new Scanner(file);

			// String para receber as linhas do arquivo .csv
			String rows = new String();

			/*
			 * Pulando as duas primeiras linhas do arquivo .csv 1ª linha em
			 * branco 2ª linha (header) cabeçaçho com os nomes dos campos
			 */
			scanner.nextLine();
			scanner.nextLine();

			/*
			 * Criando duas lista de obj Chamado oldList contém os chamados que
			 * já estão gravados (salvos) no banco de dados newList receberá os
			 * chamados do arquivo que será importado
			 */
			List<Chamado> oldList = new ArrayList<>();
			List<Chamado> newList = new ArrayList<>();

			/*
			 * oldList recebendo os chamados do banco de dados através do método
			 * listar()
			 */
			oldList = chamadoDAO.listar();

			/*
			 * Laço de repetição para iterar as linhas do arquivo.
			 */
			while (scanner.hasNext()) {

				rows = scanner.nextLine();
				/*
				 * Array de String recebendo os valores dos campos do arquivo
				 * .csv, "quebrando" as linhas pelo caractere ';'
				 */

				String[] values = rows.split(";");

				/*
				 * Método novo(), ele somente da um new no obj Chamado esse
				 * passo é necessário pois caso um new não seja chamado o obj
				 * chamado será sobrescrito, ficando somente com a informação do
				 * último chamado importado no arquivo
				 */
				novo();

				/*
				 * Criando as variaveis para receber os dados do arquivo .csv
				 * para salvar no banco de dados posteriormente. A expressão
				 * regular values[2].replaceAll("\"", "") serve para "remover"
				 * (subistituir) o caractere
				 * '"' (aspas) por "" (vazio) pois no arquivo csv os campos tipo String vem com aspas '"
				 * '
				 */

				long numCh = Long.parseLong(values[0]);

				if (values[1].isEmpty()) {
					Date dtAbertura = null;
					chamado.setDataAbertura(dtAbertura);

				} else {					
					Date dtAbertura = fmtEnUs.parse(values[1]);
					chamado.setDataAbertura(dtAbertura);
				}

				String statChamado = values[2].replaceAll("\"", "");
				statusChamado = statusChamadoDAO.buscarStatus(statChamado);
				chamado.setStatusChamado(statusChamado);

				
				String codigoCliente = values[3].replaceAll("\"", "");

				String nomeCliente = values[4].replaceAll("\"", "");

				String setorAbertura = values[5].replaceAll("\"", "");

				String problemaAbertura = values[6].replaceAll("\"", "");

				String criticidade = values[7].replaceAll("\"", "");

				String descricaoProblema = values[8].replaceAll("\"", "");

				if (values[9].isEmpty()) {
					Date dtUltimaResponsabilidade = null;
					chamado.setDataUltimaResp(dtUltimaResponsabilidade);
				} else {
					Date dtUltimaResponsabilidade = fmtPtBr.parse(values[9]);
					chamado.setDataUltimaResp(dtUltimaResponsabilidade);
				}

				String ultimaIntervenção = values[10].replaceAll("\"", "");

				String ultimaSolicitacao = values[11].replaceAll("\"", "");

				if (values[12].isEmpty()) {
					operador = operadorDAO.buscarOperadorPorNome("Sem operador");
					chamado.setOperadores(operador);

				} else {
					String operadores = values[12].replaceAll("\"", "");
					operador = operadorDAO.buscarOperadorPorNome(operadores);
					chamado.setOperadores(operador);
				}

				if (values[13].isEmpty()) {
					Date vencimanentoSLA = null;
					chamado.setVencimentoSla(vencimanentoSLA);
				} else {
					Date vencimanentoSLA = fmtPtBr.parse(values[13]);
					chamado.setVencimentoSla(vencimanentoSLA);
				}

				String visibilidade = values[14].replaceAll("\"", "");

				/*
				 * Setando os valores das variáveis criadas acima no obj
				 * chamado. Note que a data de abertura, status do chamado,
				 * dtUltimaResponsabilidade, operador e vencimanentoSLA foram
				 * setadas nas condições acima.
				 */

				chamado.setNumeroChamado(numCh);
				chamado.setCodigoCliente(codigoCliente);
				chamado.setNomeCliente(nomeCliente);
				chamado.setSetorAbertura(setorAbertura);
				chamado.setProblemaAbertura(problemaAbertura);
				chamado.setCriticidade(criticidade);
				chamado.setDescricaoProblema(descricaoProblema);
				chamado.setUltimaIntervecao(ultimaIntervenção);
				chamado.setUltimaSolicitacao(ultimaSolicitacao);
				chamado.setVisibilidade(visibilidade);

				/*
				 * Adicionado os chamados a lista newList
				 */
				newList.add(chamado);

				/*
				 * Salvando os chamados no banco de dados
				 */
				chamadoDAO.merge(chamado);

			}
			scanner.close();

			/*
			 * Criando um lista temporária (tmpList) para comparar os chamados
			 * que já estão salvos no bando de dados com os novos chamados, que
			 * estão sendo importados pelo aqruivo .csv. Observe que a tmpList
			 * recebe todos os chamados da oldList (tmpList.addAll(oldList)) e
			 * então os chamados duplicados são removidos da lista
			 * (tmpList.removeAll(newList)) na tmpList estão os chamado que
			 * foram encerrados, resolvidos ou transferido de fila e que serão
			 * atualizados pelo método encerrarChamado.
			 */

			List<Chamado> tmpList = new ArrayList<>();
			tmpList.addAll(oldList);
			tmpList.removeAll(newList);

			chamadoDAO.fecharChamado(tmpList);

			listaDeChamados = chamadoDAO.listarChamadosAbertos();

		} catch (IOException | ParseException | RuntimeException exception) {
			Messages.addGlobalError("Ocorreu um erro ao salvar a lista de chamados");
			exception.printStackTrace();
		}

	}

	/*
	 * Método utilizado para visualizar os dados completos do chamado pela
	 * coluna Visualizar, caso a opção do ícone seja preferida esse método deve
	 * ser ativado (descomentado)
	 */
	public void visualizar(ActionEvent event) {
		try {

			chamado = (Chamado) event.getComponent().getAttributes().get("chamadoSelecionado");

		} catch (RuntimeException exception) {
			Messages.addGlobalError("Ocorreu um erro ao selecionar o chamado.");
			exception.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		carregaOperadores();
		carregaCriticidade();
		carregarSistemas();
		carregaStatusChamado();
		try {
			ChamadoDAO chamadoDao = new ChamadoDAO();
			listaDeChamados = chamadoDao.listarChamadosAbertos();
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao listar os chamados");
			e.printStackTrace();
		}
	}

	public void uploadFile(FileUploadEvent event) {
		try {
			// Arquivo escolhido pelo usuário para realizar o upload (arquivo do
			// upload)
			UploadedFile uploadedFile = event.getFile();

			/*
			 * O primeiro parâmetro null do createTempFile está relacionado ao
			 * nome do arquivo temporário;
			 * 
			 * O segundo parâmetro null do createTempFile está relacionado a
			 * extensão do arquivo por padrão ele utiliza a extensão .tmp
			 * 
			 * O retorno do Files.createTempFile(null, null) é do tipo Path
			 * (caminho do arquivo) O Path necessário é do java.nio.file
			 */
			Path tempFile = Files.createTempFile(null, null);

			/*
			 * Fazendo a cópia do arquivo "uploadedFile" para o sistema
			 * operacional
			 */
			Files.copy(uploadedFile.getInputstream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

			chamado.setCaminho(tempFile.toString());

			Messages.addGlobalInfo("Upload realizado com sucesso.");

		} catch (IOException exception) {
			Messages.addGlobalError("Ocorreu um erro ao fazer o upload do arquivo");
			exception.printStackTrace();
		}
	}

	/*
	 * Método utilizado para selecionar a linha com um clique
	 * 
	 * public void onRowSelect(SelectEvent event) { chamadoSelecionado =
	 * (Chamado) event.getObject(); }
	 */

	public void carregaOperadores() {
		OperadorDAO operadorDAO = new OperadorDAO();
		try {
			listaDeOperadores = operadorDAO.listar();

		} catch (RuntimeException exception) {
			Messages.addGlobalError("Ocorreou um erro ao carregar a lista de operadores");
			exception.printStackTrace();
		}
	}

	public void carregaCriticidade() {
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		try {
			listaDeCriticidade = chamadoDAO.listaDeCriticidade();
		} catch (RuntimeException exception) {
			Messages.addGlobalError("Ocorreou um erro ao gerar a lista de criticidade");
			exception.printStackTrace();
		}
	}
	
	public void carregarSistemas(){
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		
		try{
			listaDeSistemas = chamadoDAO.listaDeSistemas();
		} catch(RuntimeException exception){
			Messages.addGlobalError("Ocorreu um erro ao gerar a lista de sistemas");
			exception.printStackTrace();
		}
	}
	
	public void carregaStatusChamado(){
		
		StatusChamadoDAO statusChamadoDAO = new StatusChamadoDAO();
		
		try{
			
			listaDeStatusChamado = statusChamadoDAO.listarStatusOrdenado();
			
		} catch(RuntimeException exception){
			Messages.addGlobalError("Ocorreou um erro ao gerar a lista de status de chamado");
			exception.printStackTrace();
		}
		
	}

}
