package br.com.acompanhamentofila.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.omnifaces.util.Messages;
import org.primefaces.extensions.component.gchart.model.GChartModel;
import org.primefaces.extensions.component.gchart.model.GChartModelBuilder;
import org.primefaces.extensions.component.gchart.model.GChartType;
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

	private GChartModel chartModel;

	private List<Chamado> listaChamadoVencido = new ArrayList<>();
	private List<Chamado> listaChamadoHoje = new ArrayList<>();
	private List<Chamado> listaChamadoAmanha = new ArrayList<>();
	private List<Chamado> listaChamadoaVencer = new ArrayList<>();

	public BarChartModel getBarModel() {
		return barModel;
	}

	public GChartModel getChartModel() {
		return chartModel;
	}

	public List<Chamado> getListaChamadoVencido() {
		return listaChamadoVencido;
	}

	public List<Chamado> getListaChamadoHoje() {
		return listaChamadoHoje;
	}

	public List<Chamado> getListaChamadoAmanha() {
		return listaChamadoAmanha;
	}

	public List<Chamado> getListaChamadoaVencer() {
		return listaChamadoaVencer;
	}

	public void setListaChamadoVencido(List<Chamado> listaChamadoVencido) {
		this.listaChamadoVencido = listaChamadoVencido;
	}

	public void setListaChamadoHoje(List<Chamado> listaChamadoHoje) {
		this.listaChamadoHoje = listaChamadoHoje;
	}

	public void setListaChamadoAmanha(List<Chamado> listaChamadoAmanha) {
		this.listaChamadoAmanha = listaChamadoAmanha;
	}

	public void setListaChamadoaVencer(List<Chamado> listaChamadoaVencer) {
		this.listaChamadoaVencer = listaChamadoaVencer;
	}

	@PostConstruct
	public void init() {
		createBarModels();		
	}

	/*
	private BarChartModel initBarModel() {

		List<Chamado> listaChamadosAbertos = new ArrayList<>();

		try {
			ChamadoDAO chamadoDAO = new ChamadoDAO();
			listaChamadosAbertos = chamadoDAO.listarChamadosAbertos();
		} catch (RuntimeException exception) {
			Messages.addGlobalError("Ocorreu um erro ao gerar a lista de chamados");
			exception.printStackTrace();
		}

		int chAvencer = 0;
		int chHoje = 0;
		int chAmanha = 0;
		int chVencido = 0;

		DateTime dataAtual = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

		for (Chamado c : listaChamadosAbertos) {

			DateTime slaChamado = new DateTime(c.getVencimentoSla());

			if (!slaChamado.isEqual(null)) {

				String SslaCh = slaChamado.toString(fmt);
				DateTime sla = fmt.parseDateTime(SslaCh);

				String SdtAtual = dataAtual.toString(fmt);
				DateTime dtAtual = fmt.parseDateTime(SdtAtual);

				if (sla.isBefore(dtAtual)) {
					chVencido += 1;
				}

				if (sla.isEqual(dtAtual)) {
					chHoje += 1;
				}

				if (sla.isEqual(dtAtual.plusDays(1))) {
					chAmanha += 1;
				}

				if (sla.isAfter(dtAtual.plusDays(1))) {
					chAvencer += 1;
				}
			}
		}

		BarChartModel model = new BarChartModel();

		ChartSeries vencido = new ChartSeries();
		vencido.setLabel("Vencidos");
		vencido.set("Categorias", chVencido);

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
	*/

	private void createBarModels() {
		//createBarModel();
		createGbarModel();
	}

	/*
	private void createBarModel() {
		barModel = initBarModel();

		barModel.setTitle("Chamado em atendimento por vencimento");
		barModel.setLegendPosition("s");
		// Posição da legenda -> n, s, w, e, se, sw, ne, nw
		barModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		barModel.setLegendCols(4);

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Quantidade de chamados");
		yAxis.setMin(0);
		yAxis.setMax(200);

	}
	*/

	public void createGbarModel() {

		List<Chamado> listaChAbertos = new ArrayList<>();

		try {
			ChamadoDAO chamadoDAO = new ChamadoDAO();
			listaChAbertos = chamadoDAO.listarChamadosAbertos();
		} catch (RuntimeException exception) {
			Messages.addGlobalError("Ocorreu um erro ao gerar a lista de chamados");
			exception.printStackTrace();
		}

		int chAvencer = 0;
		int chHoje = 0;
		int chAmanha = 0;
		int chVencido = 0;

		DateTime dataAtual = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

		for (Chamado c : listaChAbertos) {

			DateTime slaChamado = new DateTime(c.getVencimentoSla());

			if (!slaChamado.isEqual(null)) {

				String SslaCh = slaChamado.toString(fmt);
				DateTime sla = fmt.parseDateTime(SslaCh);

				String SdtAtual = dataAtual.toString(fmt);
				DateTime dtAtual = fmt.parseDateTime(SdtAtual);

				if (sla.isBefore(dtAtual)) {
					chVencido += 1;
					listaChamadoVencido.add(c);
				}

				if (sla.isEqual(dtAtual)) {
					chHoje += 1;
					listaChamadoHoje.add(c);
				}

				if (sla.isEqual(dtAtual.plusDays(1))) {
					chAmanha += 1;
					listaChamadoAmanha.add(c);
				}

				if (sla.isAfter(dtAtual.plusDays(1))) {
					chAvencer += 1;
					listaChamadoaVencer.add(c);
				}
			}
		}

		chartModel = new GChartModelBuilder().setChartType(GChartType.COLUMN)
				.addColumns("Categorias", "Vencido", "Hoje", "Amanhã", "A vencer")
				.addRow("Categorias", chVencido, chHoje, chAmanha, chAvencer).build();
	}
}
