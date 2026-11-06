import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ContaPoupanca extends Conta{
    private static final double TAXA_POR_MINUTO = 0.01;
    private ScheduledExecutorService agendador;

    public ContaPoupanca(int pix, Cliente cliente, Agencia agencia){
        super(pix, cliente, agencia);
    }

    public void iniciarInvestimentos(){
        if(agendador != null && !agendador.isShutdown()){
            return;
        }

        agendador = Executors.newSingleThreadScheduledExecutor();

        agendador.scheduleAtFixedRate(() -> {
            double rendimento = getSaldo() * TAXA_POR_MINUTO;

            if(rendimento > 0){
                depositar(rendimento);
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

    public void pararInvestimentos(){
        if(agendador != null){
            agendador.shutdown();
            agendador = null;
        }
    }
}
