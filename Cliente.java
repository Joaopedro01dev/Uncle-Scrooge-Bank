public class Cliente{
    private final int numero;
    private String nome;
    private static int codigo = 1000;

    public Cliente(String nome){
        this.numero = codigo++;
        this.nome = nome;
    }

    public int getNumero(){
        return numero;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public static int getProximoCodigo(){
        return codigo;
    }

    public void exibirDados(){
        System.out.println("Codigo do cliente: " + numero);
        System.out.println("Nome do cliente: " + nome);
    }
}
