public class PessoaFisica extends Cliente{
    private final String cpf;

    public PessoaFisica(String nome, String cpf){
        super(nome);
        this.cpf = cpf;
    }

    public String getCpf(){
        return cpf;
    }

    @Override
    public void exibirDados(){
        super.exibirDados();
        System.out.println("CPF: " + cpf);
    }
}
