import java.util.Scanner;
import java.util.ArrayList;

public class Main{
    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Conta> listaDeContas = new ArrayList<>();
    private static final ArrayList<Cliente> listaDeClientes = new ArrayList<>();

    private static void menu(){
        System.out.println("\n===============================");
        System.out.println("       Banco Tio Patinhas      ");
        System.out.println("===============================");
        System.out.println("1. Sacar");
        System.out.println("2. Depositar");
        System.out.println("3. Consultar Saldo");
        System.out.println("4. Fazer recarga");
        System.out.println("5. Fazer transferência");
        System.out.println("6. Trocar de Conta / Voltar ao Início");
        System.out.println("7. Sair do Sistema");
        System.out.print("Escolha uma opção: ");
    }

    private static void startMenu(){
        System.out.println("\n===============================");
        System.out.println("       Banco Tio Patinhas      ");
        System.out.println("===============================");
        System.out.println("1. Criar Nova Conta");
        System.out.println("2. Entrar na Conta");
        System.out.println("3. Listar Clientes");
        System.out.println("4. Sair do Sistema");
        System.out.print("Escolha uma opção: ");   
    }

    private static void criarNovaConta(){
        int pix;
        String nome;
        double saldo;

        while(true){
            System.out.print("Digite o nome do Cliente: ");
            nome = input.nextLine();

            if(nome.trim().isEmpty()){
                System.out.println("Não digite um nome vazio!");
            }
            else{
                break;
            }
        }

        System.out.println("Tipo de cliente: 1. Pessoa Física | 2. Pessoa Jurídica");
        System.out.print("Escolha uma opção: ");
        int tipoCliente = input.nextInt();
        input.nextLine();

        Cliente novoCliente;
        if(tipoCliente == 1){
            System.out.print("Digite o CPF: ");
            String cpf = input.nextLine();
            novoCliente = new PessoaFisica(nome, cpf);
        } else if(tipoCliente == 2){
            System.out.print("Digite o CNPJ: ");
            String cnpj = input.nextLine();
            novoCliente = new PessoaJuridica(nome, cnpj);
        } else{
            System.out.println("Tipo de cliente inválido. Criação cancelada.");
            return;
        }
        Agencia minhaAgencia = new Agencia(1984);

        System.out.println("--- Criação da Conta ---");

        while(true){
            System.out.print("Digite o saldo inicial da conta: ");
            saldo = input.nextDouble();
            input.nextLine();

            if(!Double.isFinite(saldo) || saldo < 0){
                System.out.println("Digite um saldo válido (positivo ou zero)!");
            }
            else{
                break;
            }
        }

        System.out.print("Digite a chave pix (somente números): ");
        pix = input.nextInt();
        input.nextLine();

        System.out.println("Tipo de conta: 1. Conta Corrente | 2. Conta Poupança");
        System.out.print("Escolha uma opção: ");
        int tipoConta = input.nextInt();
        input.nextLine();

        Conta novaConta;
        if(tipoConta == 1){
            novaConta = new ContaCorrente(saldo, pix, novoCliente, minhaAgencia);
        } else if(tipoConta == 2){
            novaConta = new ContaPoupanca(saldo, pix, novoCliente, minhaAgencia);
        } else {
            System.out.println("Tipo de conta inválido. Criação cancelada.");
            return;
        }

        listaDeClientes.add(novoCliente);
        listaDeContas.add(novaConta);

        System.out.println("Cliente e Conta criados com sucesso!");
    }

    private static Conta selecionarConta(){
        if(listaDeContas.isEmpty()){
            System.out.println("Nenhuma conta cadastrada!");
            return null;
        }

        System.out.println("\n--- Contas Disponíveis ---");
        for(int i = 0; i < listaDeContas.size(); i++){
            Conta conta = listaDeContas.get(i);
            String tipo = conta instanceof ContaCorrente ? "Conta Corrente" : "Conta Poupança";
            System.out.println("[" + i + "] " + tipo + " Número: " + conta.getNumero());
        }

        System.out.print("Digite o índice da conta que deseja acessar: ");
        int indice = input.nextInt();
        input.nextLine();

        if(indice >= 0 && indice < listaDeContas.size()){
            return listaDeContas.get(indice);
        }
        else{
            System.out.println("Índice de conta inválido!");
            return null;
        }
    }

    private static void listarCliente(){
        if(listaDeClientes.isEmpty()){
            System.out.println("Nenhuma conta cadastrada!");
            return;
        }

        for(int i = 0; i < listaDeClientes.size(); i++){
           Cliente cliente = listaDeClientes.get(i);
           System.out.println("[" + i + "]");
           cliente.exibirDados();
        }
        
        System.out.println("\n");
    }

    public static void main(String[] args){
        boolean sistemaAtivo = true;

        while(sistemaAtivo){
            startMenu();
            int escolhaStart = input.nextInt();
            input.nextLine();

            switch(escolhaStart){
                case 1:
                    criarNovaConta();
                    break;

                case 2:
                    Conta contaAtual = selecionarConta();

                    if(contaAtual != null){
                        boolean noMenuOperacional = true;

                        while(noMenuOperacional){
                            menu();
                            int escolhaMenu = input.nextInt();
                            input.nextLine();

                            switch(escolhaMenu){
                                case 1:
                                    System.out.print("Digite um valor para sacar: ");
                                    double valorSaque = input.nextDouble();
                                    input.nextLine();

                                    if(contaAtual.sacar(valorSaque)){
                                        contaAtual.consultarSaldo();
                                    } 
                                    else{
                                        System.out.println("Valor inválido ou saldo insuficiente!");
                                    }
                                    break;

                                case 2:
                                    System.out.print("Digite um valor para depositar: ");
                                    double valorDeposito = input.nextDouble();
                                    input.nextLine();

                                    if(contaAtual.depositar(valorDeposito)){
                                        contaAtual.consultarSaldo();
                                    } 
                                    else{
                                        System.out.println("Valor inválido!");
                                    }
                                    break;

                                case 3:
                                    contaAtual.consultarSaldo();
                                    break;

                                case 4:
                                    System.out.println("1. Recarga padrão (20.00)");
                                    System.out.println("2. Digite um valor para recarga");
                                    System.out.print("Escolha uma opção: ");

                                    int opcaoRecarga = input.nextInt();
                                    input.nextLine();

                                    if(opcaoRecarga == 1){
                                        if(contaAtual.recarga()){
                                            contaAtual.consultarSaldo();
                                        } else {
                                            System.out.println("Saldo insuficiente para a recarga!");
                                        }
                                    } 
                                    else if(opcaoRecarga == 2){
                                        System.out.print("Digite o valor: ");
                                        double valorRecarga = input.nextDouble();
                                        input.nextLine();
                                        if(contaAtual.recarga(valorRecarga)){
                                            contaAtual.consultarSaldo();
                                        } else {
                                            System.out.println("Valor inválido ou saldo insuficiente!");
                                        }
                                    } else {
                                        System.out.println("Opção inválida!");
                                    }
                                    break;

                                case 5:
                                    System.out.println("Escolha uma das contas para fazer a transferência: ");
                                    int numeroDaContaAtual = contaAtual.getNumero();
                                    int indiceContaAtual = -1;

                                    System.out.println("\n--- Contas Disponíveis ---");
                                    for(int i = 0; i < listaDeContas.size(); i++){
                                        if(listaDeContas.get(i).getNumero() == numeroDaContaAtual){
                                            indiceContaAtual = i;
                                            continue;
                                        }
                                        else{
                                            System.out.println("[" + i + "] Conta Número: " + listaDeContas.get(i).getNumero());
                                        }
                                    }
                                    System.out.print("Digite o índice da conta que deseja transferir: ");
                                    int indice = input.nextInt();
                                    input.nextLine();

                                    if(indice >= 0 && indice < listaDeContas.size() && indice != indiceContaAtual){
                                        double valorDeDeposito;
                                        while(true){
                                            System.out.println("Digite o valor para transferir: ");
                                            valorDeDeposito = input.nextDouble();

                                            if(valorDeDeposito <= 0){
                                                System.out.println("Digite um valor válido!");
                                                continue;
                                            }
                                            else{
                                                break;
                                            }
                                        }

                                        Conta contaDestino = listaDeContas.get(indice);
                                        if(contaAtual.transferir(contaDestino, valorDeDeposito)){
                                            System.out.println("Transferência feita com sucesso!");
                                        } else {
                                            System.out.println("Transferência não realizada: valor inválido ou saldo insuficiente!");
                                        }
                                    }
                                    else{
                                        System.out.println("Índice de conta inválido!");
                                    }
                                    break;

                                case 6:
                                    System.out.println("Saindo da conta atual...");
                                    noMenuOperacional = false;
                                    break;

                                case 7:
                                    System.out.println("Obrigado por usar nosso banco!");
                                    noMenuOperacional = false;
                                    sistemaAtivo = false;
                                    break;

                                default:
                                    System.out.println("Opção inválida!");
                                    break;
                            }
                        }
                    }
                    break;

                case 3:
                    listarCliente();
                    break;

                case 4:
                    System.out.println("Encerrando o programa... Até logo!");
                    sistemaAtivo = false;
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

        input.close();
   }
}
