import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class Conta{
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final int numero;
    private double saldo;
    private int pix;
    private Cliente cliente;
    private final Agencia agencia;
    private static int codigo = 1000;
    private final List<String> extrato = new ArrayList<>();

    protected Conta(int pix, Cliente cliente, Agencia agencia){
        this.numero = codigo++;
        this.saldo = 500.00;
        this.pix = pix;
        this.cliente = cliente;
        this.agencia = agencia;
        registrarTransacao("Saldo inicial", "Abertura da conta", saldo);
    }

    public int getNumero(){ return numero; }

    public synchronized double getSaldo(){ return saldo; }

    public int getPix(){ return pix; }
    public void setPix(int pix){ this.pix = pix; }

    public Cliente getCliente(){ return cliente; }
    public void setCliente(Cliente cliente){ this.cliente = cliente; }

    public Agencia getAgencia(){ return agencia; }

    public static int getProximoCodigo(){ return codigo; }

    public boolean transferir(Conta destino, double valor){
        if(destino == null || destino == this || !valorValido(valor)){
            return false;
        }

        Conta primeira = numero < destino.numero ? this : destino;
        Conta segunda = primeira == this ? destino : this;

        synchronized(primeira){
            synchronized (segunda){
                if(saldo < valor || !Double.isFinite(destino.saldo + valor)){
                    return false;
                }

                saldo -= valor;
                destino.saldo += valor;
                registrarTransacao("Transferência enviada", "Para a conta " + destino.numero, -valor);
                destino.registrarTransacao("Transferência recebida", "Da conta " + numero, valor);
                return true;
            }
        }
    }

    public synchronized boolean depositar(double valor){
        if(!valorValido(valor) || !Double.isFinite(saldo + valor)){
            return false;
        }

        saldo += valor;
        registrarTransacao("Depósito", "Depósito em conta", valor);
        return true;
    }

    protected synchronized boolean aplicarRendimento(double valor){
        if(!valorValido(valor) || !Double.isFinite(saldo + valor)){
            return false;
        }

        saldo += valor;
        registrarTransacao("Rendimento", "Rendimento do investimento", valor);
        return true;
    }

    public synchronized boolean sacar(double valor){
        if(!valorValido(valor) || saldo < valor){
            return false;
        }

        saldo -= valor;
        registrarTransacao("Saque", "Retirada da conta", -valor);
        return true;
    }

    public void consultarSaldo(){
        System.out.printf("Saldo atual: %s%n", formatarMoeda(getSaldo()));
    }

    public synchronized boolean recarga(double valor){
        if(!valorValido(valor) || saldo < valor){
            return false;
        }

        saldo -= valor;
        registrarTransacao("Recarga", "Recarga de celular", -valor);
        return true;
    }

    public boolean recarga(){
        return recarga(20.00);
    }

    private static boolean valorValido(double valor){
        return Double.isFinite(valor) && valor > 0;
    }

    private synchronized void registrarTransacao(String tipo, String descricao, double valor){
        String sinal = valor >= 0 ? "+" : "-";
        String linha = String.format("%s | %-23s | %-32s | Valor: %s%s | Saldo: %s", LocalDateTime.now().format(FORMATO_DATA), tipo, descricao, sinal, formatarMoeda(Math.abs(valor)), formatarMoeda(saldo));
        extrato.add(linha);
    }

    private static String formatarMoeda(double valor){
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        return formato.format(valor);
    }

    public void exibirDados(){
        System.out.println("Número da conta: " + numero);
        System.out.println("Chave Pix: " + pix);
        consultarSaldo();

        if(cliente != null){
            cliente.exibirDados();
        }
    }

    public synchronized void imprimirExtrato(){
        System.out.println("\n================ EXTRATO ================");
        System.out.println("Conta: " + numero);
        System.out.println("Emitido em: " + LocalDateTime.now().format(FORMATO_DATA));
        System.out.println("-----------------------------------------");

        if (extrato.isEmpty()){
            System.out.println("Nenhuma movimentação registrada.");
        }
        else{
            for(String transacao : extrato){
                System.out.println(transacao);
            }
        }

        System.out.println("-----------------------------------------");
        System.out.println("Saldo disponível: " + formatarMoeda(saldo));
        System.out.println("=========================================\n");
    }
}
