package br.com.acompanhamentofila.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

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
		BarChartModel model = new BarChartModel();

		ChartSeries boys = new ChartSeries();

		boys.setLabel("Boys");
		boys.set("2004", 120);

		ChartSeries girls = new ChartSeries();

		girls.setLabel("Girls");
		girls.set("2004", 66);

		model.addSeries(boys);
		model.addSeries(girls);

		return model;
	}

	private void createBarModels() {
		createBarModel();
	}

	private void createBarModel() {
		barModel = initBarModel();

		barModel.setTitle("Gráfico teste");
		barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Gênero");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Nascimento");
		yAxis.setMin(0);
		yAxis.setMax(200);

	}

}
