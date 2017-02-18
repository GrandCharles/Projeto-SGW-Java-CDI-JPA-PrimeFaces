package br.com.grandcharles.sgw.controller.pedido;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.model.pedido.EnderecoEntregaTO;
import br.com.grandcharles.sgw.model.pedido.FormaPagamento;
import br.com.grandcharles.sgw.model.pedido.ItemPedidoTO;
import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.model.produto.ProdutoTO;
import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.repository.cliente.ClienteRepository;
import br.com.grandcharles.sgw.repository.produto.ProdutoRepository;
import br.com.grandcharles.sgw.repository.usuario.UsuarioRepository;
import br.com.grandcharles.sgw.service.pedido.PedidoService;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;
import br.com.grandcharles.sgw.validation.SKU;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private ClienteRepository clienteRepository;

	@Inject
	private ProdutoRepository produtoRepository;
	
	@Inject
	private PedidoService pedidoService;
	
	@Produces
	@PedidoEdicao
	private PedidoTO pedidoTO;
	
	private ProdutoTO produtoTO;
	
	@SKU
	private String sku;
	

	
	public CadastroPedidoBean() {
		limpar();
	}

	public void inicializar(){
		if (this.pedidoTO == null) {
			limpar();
		}	

		if (FacesUtil.isNotPostback()){
			this.pedidoTO.adicionarItemVazio();
			
			this.calcularTotalPedido();
		}
	}
	

	private void limpar(){
		pedidoTO = new PedidoTO();
		pedidoTO.setEnderecoEntregaTO(new EnderecoEntregaTO());
		//itensPedido = new ArrayList<>();
	}
	
	public void salvar(){
		this.pedidoTO.removerItemVazio();
		
		try {
			this.pedidoTO = this.pedidoService.salvar(pedidoTO);
			
			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		} finally {
			this.pedidoTO.adicionarItemVazio();
		}
	}
	
	public void pedidoAlterado(@Observes PedidoEvent event){
		this.pedidoTO = event.getPedidoTO();
	}
	
	
	
	public boolean isNovo() {
		//return this.pedidoTO.getId() != null;
		return pedidoTO.isNovo();
	}
	public boolean isExistente() {
		return !isNovo();
	}
	

	
	public List<ClienteTO> completarCliente(String nome){
		return this.clienteRepository.pesquisaPorNome(nome);
	}

	public List<UsuarioTO> completarUsuario(String nome){
		return this.usuarioRepository.pesquisaPorNome(nome);
	}
	
	public List<ProdutoTO> completarProduto(String descricao){
		return this.produtoRepository.pesquisaPorDescricao(descricao);
	}
	

	public void calcularTotalPedido() {
		if (this.pedidoTO != null) {
			this.pedidoTO.calculoTotalPedido();
		}
	}

	
	public void carregarProdutoPorSku(){
		if(StringUtils.isNotEmpty(this.sku)){
			this.produtoTO = this.produtoRepository.existeSKU(this.sku);
			this.carregarProdutoEdicao();
		}
	}

	
	public void carregarProdutoEdicao(){
		ItemPedidoTO item = this.pedidoTO.getLstItemPedido().get(0);
		
		if (this.produtoTO != null){
			if (this.produtoExistente(this.produtoTO)){
				FacesUtil.addErrorMessage("Produto ja lançado nesse pedido");
				this.produtoTO = null;
				this.sku = null;
		
			} else {
				item.setProdutoTO(this.produtoTO);
				item.setVlrUnitario(this.produtoTO.getVlrUnitario());
				
				this.pedidoTO.adicionarItemVazio();
				this.produtoTO = null;
				this.sku = null;
				this.pedidoTO.calculoTotalPedido();
			}
		}
	}
	
	
	private boolean produtoExistente(ProdutoTO produtoTO) {
		for (ItemPedidoTO item : this.getPedidoTO().getLstItemPedido()){
			if (produtoTO.equals(item.getProdutoTO())){
				return true;
			}
		}
		return false;
	}
	 
	public void atualizarQtde(ItemPedidoTO item, int linha){
		if (item.getQuantidade() < 1){
			if (linha == 0){
				item.setQuantidade(1);
			} else {
				this.getPedidoTO().getLstItemPedido().remove(linha);
			}
		}
		
		this.pedidoTO.calculoTotalPedido();
		this.pedidoTO.atualizaOrdem();
	}
	
	/* ============================================================================================== */
	public FormaPagamento[] getFormaPgto(){
		return FormaPagamento.values();
	}

	
	public PedidoTO getPedidoTO() {
		return pedidoTO;
	}
	public void setPedidoTO(PedidoTO pedidoTO) {
		this.pedidoTO = pedidoTO;
	}

	public ProdutoTO getProdutoTO() {
		return produtoTO;
	}
	public void setProdutoTO(ProdutoTO produtoTO) {
		this.produtoTO = produtoTO;
	}

	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	
}
