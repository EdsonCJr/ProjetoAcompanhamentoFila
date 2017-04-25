package br.com.acompanhamentofila.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.omnifaces.util.Messages;
import org.primefaces.extensions.component.gchart.model.GChartModel;
import org.primefaces.extensions.component.gchart.model.GChartModelBuilder;
import org.primefaces.extensions.component.gchart.model.GChartType;

import br.com.acompanhamentofila.dao.ChamadoDAO;
import br.com.acompanhamentofila.domain.Chamado;

@ManagedBean(name = "graficoIdadeMB")
public class GraficoIdadeBean {

	/*
	 * Faixas de idade de chamados 
	 * até 5 dias 
	 * de 6 a 15 dias 
	 * de 16 a 25 dias 
	 * de 26 a 35 dias 
	 * de 36 a 45 dias 
	 * maior que 45 dias
	 */

	private GChartModel gChartModel;

	private List<Chamado> listaCh05Dias = new ArrayList<>();
	private List<Chamado> listaCh06A15Dias = new ArrayList<>();
	private List<Chamado> listaCh16A25Dias = new ArrayList<>();
	private List<Chamado> listaCh26A35Dias = new ArrayList<>();
	private List<Chamado> listaCh36A45Dias = new ArrayList<>();
	private List<Chamado> listaChMaior45Dias = new ArrayList<>();

	public GChartModel getgChartModel() {
		return gChartModel;
	}

	public List<Chamado> getListaCh05Dias() {
		return listaCh05Dias;
	}

	public void setListaCh05Dias(List<Chamado> listaCh05Dias) {
		this.listaCh05Dias = listaCh05Dias;
	}

	public List<Chamado> getListaCh06A15Dias() {
		return listaCh06A15Dias;
	}

	public void setListaCh06A15Dias(List<Chamado> listaCh06A15Dias) {
		this.listaCh06A15Dias = listaCh06A15Dias;
	}

	public List<Chamado> getListaCh16A25Dias() {
		return listaCh16A25Dias;
	}

	public void setListaCh16A25Dias(List<Chamado> listaCh16A25Dias) {
		this.listaCh16A25Dias = listaCh16A25Dias;
	}

	public List<Chamado> getListaCh26A35Dias() {
		return listaCh26A35Dias;
	}

	public void setListaCh26A35Dias(List<Chamado> listaCh26A35Dias) {
		this.listaCh26A35Dias = listaCh26A35Dias;
	}

	public List<Chamado> getListaCh36A45Dias() {
		return listaCh36A45Dias;
	}

	public void setListaCh36A45Dias(List<Chamado> listaCh36A45Dias) {
		this.listaCh36A45Dias = listaCh36A45Dias;
	}

	public List<Chamado> getListaChMaior45Dias() {
		return listaChMaior45Dias;
	}

	public void setListaChMaior45Dias(List<Chamado> listaChMaior45Dias) {
		this.listaChMaior45Dias = listaChMaior45Dias;
	}

	@PostConstruct
	public void init() {
		createGBarModel();
	}

	public void createGBarModel(){
		List<Chamado> listaChamadosAbertos = new ArrayList<>();
		
		try{
			ChamadoDAO chamadoDAO = new ChamadoDAO();
			
			listaChamadosAbertos = chamadoDAO.listarChamadosAbertos();
			
		} catch(RuntimeException exception){
			Messages.addGlobalError("Ocorreou um erro ao gerar a lista de chamados");
			exception.printStackTrace();
		}
		
		int qtdCh05Dias = 0;
		int qtdCh06A15Dias = 0;
		int qtdCh16A25Dias = 0;
		int qtdCh26A35Dias = 0;
		int qtdCh36A45Dias = 0;
		int qtdChMaior45Dias = 0;
		
		DateTime hoje = new DateTime();
		
		for(Chamado c : listaChamadosAbertos){
			
			DateTime dtAberturaCh = new DateTime(c.getDataAbertura());
			
			int dias = Days.daysBetween(dtAberturaCh, hoje).getDays();
			
			if(dias <= 5){
				qtdCh05Dias += 1;
				listaCh05Dias.add(c);
			}
			
			if (dias >= 6 && dias <= 15){
				qtdCh06A15Dias += 1;
				listaCh06A15Dias.add(c);
			}
			
			if(dias >= 16 && dias <= 25){
				qtdCh16A25Dias += 1;
				listaCh16A25Dias.add(c);
			}
			
			if(dias >= 26 && dias <= 35){
				qtdCh26A35Dias += 1;
				listaCh26A35Dias.add(c);
			}
			
			if (dias >=36 & dias <= 45){
				qtdCh36A45Dias += 1;
				listaCh36A45Dias.add(c);
			}
			
			if (dias > 46){
				qtdChMaior45Dias += 1;
				listaChMaior45Dias.add(c);
			}
		}

		gChartModel = new GChartModelBuilder().setChartType(GChartType.COLUMN)
				.addColumns("Dias","Até 5 dias","De 6 até 15 Dias","De 16 até 25 dias","De 26 até 35 dias","De 36 até 45 Dias","Maior que 45 dias")
				.addRow("Quantidade",qtdCh05Dias,qtdCh06A15Dias,qtdCh16A25Dias,qtdCh26A35Dias,qtdCh36A45Dias,qtdChMaior45Dias).build();
	}
}
