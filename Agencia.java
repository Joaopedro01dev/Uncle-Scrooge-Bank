public class Agencia{
    private final int numero;
    private static int codigo = 1000;

    public Agencia(int numero){
        this.numero = codigo++;
    }

    public int getNumero(){
        return numero;
    }

    public static int getProximoCodigo(){
        return codigo;
    }
}
