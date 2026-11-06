public abstract class Conta{
    private final int numero;
    private double saldo;
    private int pix;
    private Cliente cliente;
    private final Agencia agencia;
    private static int codigo = 1000;

    protected Conta(int pix, Cliente cliente, Agencia agencia){
        if(!Double.isFinite(saldo) || saldo < 0){
            throw new IllegalArgumentException("O saldo inicial deve ser finito e maior ou igual a zero.");
        }

        this.numero = codigo++;
        this.saldo = 500;
        this.pix = pix;
        this.cliente = cliente;
        this.agencia = agencia;
    }

    public int getNumero(){ return numero; }
    public double getSaldo(){ return saldo; }

    public int getPix(){ return pix; }
    public void setPix(int pix){ this.pix = pix; }

    public Cliente getCliente(){ return cliente; }
    public void setCliente(Cliente cliente){ this.cliente = cliente; }

    public Agencia getAgencia(){ return agencia; }

    public static int getProximoCodigo(){ return codigo; }

    public boolean transferir(Conta destino, double valor){
        if(destino == null || destino == this || !valorValido(valor) || saldo < valor || !Double.isFinite(destino.saldo + valor)){
            return false;
        }

        this.saldo -= valor;
        destino.saldo += valor;
        return true;
    }

    public boolean depositar(double valor){
        if(!valorValido(valor) || !Double.isFinite(saldo + valor)){
            return false;
        }

        this.saldo += valor;
        return true;
    }

    public boolean sacar(double valor){
        if(!valorValido(valor) || saldo < valor){
            return false;
        }

        this.saldo -= valor;
        return true;
    }

    public void consultarSaldo(){
        System.out.printf("Saldo atual: %.2f%n", saldo);
    }

    public boolean recarga(double valor){
        if(!valorValido(valor) || saldo < valor){
            return false;
        }

        this.saldo -= valor;
        return true;
    }

    public boolean recarga(){
        return recarga(20.00);
    }

    private static boolean valorValido(double valor){
        return Double.isFinite(valor) && valor > 0;
    }

    public void exibirDados(){
        System.out.println("Numero da conta: " + numero);
        System.out.println("Pix: " + pix);
        consultarSaldo();

        if(cliente != null){
            cliente.exibirDados();
        }
    }
}
