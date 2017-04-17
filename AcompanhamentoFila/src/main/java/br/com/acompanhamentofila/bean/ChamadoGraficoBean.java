package br.com.acompanhamentofila.bean;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.omnifaces.util.Messages;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;

import br.com.acompanhamentofila.dao.ChamadoDAO;
import br.com.acompanhamentofila.domain.Chamado;

@ManagedBean(name = "chamadoGraficoMB")
public class ChamadoGraficoBean {

	private BarChartModel barModel;

	public BarChartModel getBarModel() {
		return barModel;
	}

	@PostConstruct
	public void init() {
		createBarModels();
	}

	private BarChartModel initBarModel() {
		
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		List<Chamado> listaChamadosAbertos = new ArrayList<>();
		
		try{
			listaChamadosAbertos = chamadoDAO.listarChamadosAbertos();
		} catch(RuntimeException exception){
			Messages.addGlobalError("Ocorreu um erro ao gerar a lista de chamados");
			exception.printStackTrace();
		}
		
		int chAvencer = 0;
		int chHoje = 0;
		int chAmanha = 0;
		int chVencido = 0;
		
		DateTime dataAtual = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		
		for(Chamado c : listaChamadosAbertos){
			
			DateTime slaChamado = new DateTime(c.getVencimentoSla());
			
			if(!slaChamado.isEqual(null)){
				
				String SslaCh = slaChamado.toString(fmt);
				DateTime sla = fmt.parseDateTime(SslaCh);
				
				String SdtAtual = dataAtual.toString(fmt);
				DateTime dtAtual = fmt.parseDateTime(SdtAtual);
				
				if(sla.isBefore(dtAtual)){
					chVencido += 1;
				}
				
				if(sla.isEqual(dtAtual)){
					chHoje += 1;
				}
				
				if(sla.isEqual(dtAtual.plusDays(1))){
					chAmanha += 1;
				}
				
				if (sla.isAfter(dtAtual.plusDays(1))){
					chAvencer += 1;
				}
			}
		}
				
		BarChartModel model = new BarChartModel();
		
		ChartSeries vencido = new ChartSeries();
		vencido.setLabel("Vencidos");
		vencido.set("Categorias",chVencido);

		ChartSeries hoje = new ChartSeries();
		hoje.setLabel("Hoje");
		hoje.set("", chHoje);
		
		ChartSeries amanha = new ChartSeries();
		amanha.setLabel("Amanhã");
		amanha.set("", chAmanha);
		
		ChartSeries aVencer = new ChartSeries();
		aVencer.setLabel("A vencer");
		aVencer.set("", chAvencer);

		model.addSeries(vencido);
		model.addSeries(hoje);
		model.addSeries(amanha);
		model.addSeries(aVencer);
				
		return model;
	}

	private void createBarModels() {
		createBarModel();
	}

	private void createBarModel() {
		barModel = initBarModel();

		barModel.setTitle("Chamado em atendimento por vencimento");
		barModel.setLegendPosition("s");
		//Posição da legenda -> n, s, w, e, se, sw, ne, nw
		barModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		barModel.setLegendCols(4);

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade de chamados");
		yAxis.setMin(0);
		yAxis.setMax(200);

	}

}
