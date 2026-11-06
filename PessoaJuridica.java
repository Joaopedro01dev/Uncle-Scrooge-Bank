public class PessoaJuridica extends Cliente{
    private final String cnpj;

    public PessoaJuridica(String nome, String cnpj){
        super(nome);
        this.cnpj = cnpj;
    }

    public String getCnpj(){
        return cnpj;
    }

    @Override
    public void exibirDados(){
        super.exibirDados();
        System.out.println("CNPJ: " + cnpj);
    }
}
