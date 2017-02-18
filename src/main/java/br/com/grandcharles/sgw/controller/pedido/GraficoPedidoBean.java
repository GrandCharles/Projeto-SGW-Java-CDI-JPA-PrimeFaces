package br.com.grandcharles.sgw.controller.pedido;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.repository.pedido.PedidoRepository;
import br.com.grandcharles.sgw.security.UsuarioLogado;
import br.com.grandcharles.sgw.security.UsuarioSistema;

@Named
@RequestScoped
public class GraficoPedidoBean  {
	
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");
	
	@Inject
	private PedidoRepository repository;
	
	@Inject
	@UsuarioLogado
	private UsuarioSistema usuarioLogado;
	
	private CartesianChartModel model;

	public void preRender(){
		this.model = new LineChartModel();
		this.model.setTitle("Pedidos criados");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);
		
		this.model.getAxes().put(AxisType.X, new CategoryAxis());
		
		adicionarSerie("Todos os pedidos", null);
		adicionarSerie("Meus pedidos", usuarioLogado.getUsuarioTO());
	}
	
	private void adicionarSerie(String rotulo, UsuarioTO usuarioTO) {
		Map<Date, BigDecimal> valoresPorData = this.repository.valoresTotaisPorData(15, usuarioTO);
		
		ChartSeries series = new ChartSeries(rotulo);

		for (Date data : valoresPorData.keySet()) {
			series.set(DATE_FORMAT.format(data), valoresPorData.get(data));
		}

		this.model.addSeries(series);
	}




	public CartesianChartModel getModel() {
		return model;
	}
	public void setModel(CartesianChartModel model) {
		this.model = model;
	}
	
		
}
