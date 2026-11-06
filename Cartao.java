public class Cartao{
    private final int numero;
    private String dtValidade;
    private Cliente titular;
    private static int codigo = 100;

    public Cartao(int numero, String dtValidade, Cliente titular){
        this.numero = codigo++;
        this.dtValidade = dtValidade;
        this.titular = titular;
    }

    public int getNumero(){
        return numero;
    }

    public String getDtValidade(){
        return dtValidade;
    }

    public void setDtValidade(String dtValidade){
        this.dtValidade = dtValidade;
    }

    public Cliente getTitular(){
        return titular;
    }

    public void setTitular(Cliente titular){
        this.titular = titular;
    }

    public static int getProximoCodigo(){
        return codigo;
    }
}
