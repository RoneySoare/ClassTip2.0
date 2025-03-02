import java.util.ArrayList;
import java.util.List;

class Pedido {
    private double valor;
    private String cliente;
    
    public Pedido(double valor, String cliente) {
        this.valor = valor;
        this.cliente = cliente;
    }
    
    public double getValor() {
        return valor;
    }
    
    public String getCliente() {
        return cliente;
    }
}

interface DescontoStrategy {
    double aplicarDesconto(double valor);
}

class DescontoClienteComum implements DescontoStrategy {
    public double aplicarDesconto(double valor) {
        return valor * 0.95;
    }
}

class DescontoClienteVip implements DescontoStrategy {
    public double aplicarDesconto(double valor) {
        return valor * 0.90;
    }
}

interface Notificador {
    void notificar(String mensagem);
}

class EmailNotificador implements Notificador {
    public void notificar(String mensagem) {
        System.out.println("Enviando e-mail: " + mensagem);
    }
}

class SmsNotificador implements Notificador {
    public void notificar(String mensagem) {
        System.out.println("Enviando SMS: " + mensagem);
    }
}

class PedidoRepository {
    private List<Pedido> pedidos = new ArrayList<>();
    
    public void salvarPedido(Pedido pedido) {
        pedidos.add(pedido);
        System.out.println("Pedido salvo no banco de dados.");
    }
}

class ProcessadorDePedidos {
    private DescontoStrategy descontoStrategy;
    private Notificador notificador;
    private PedidoRepository pedidoRepository;
    
    public ProcessadorDePedidos(DescontoStrategy descontoStrategy, Notificador notificador, PedidoRepository pedidoRepository) {
        this.descontoStrategy = descontoStrategy;
        this.notificador = notificador;
        this.pedidoRepository = pedidoRepository;
    }
    
    public void processarPedido(Pedido pedido) {
        double valorComDesconto = descontoStrategy.aplicarDesconto(pedido.getValor());
        Pedido pedidoComDesconto = new Pedido(valorComDesconto, pedido.getCliente());
        pedidoRepository.salvarPedido(pedidoComDesconto);
        notificador.notificar("Pedido de " + pedido.getCliente() + " processado com sucesso! Valor final: " + valorComDesconto);
    }
}

public class Main {
    public static void main(String[] args) {
        Pedido pedido = new Pedido(100, "João");
        DescontoStrategy desconto = new DescontoClienteVip();
        Notificador notificador = new EmailNotificador();
        PedidoRepository pedidoRepository = new PedidoRepository();
        
        ProcessadorDePedidos processador = new ProcessadorDePedidos(desconto, notificador, pedidoRepository);
        processador.processarPedido(pedido);
    }
}
