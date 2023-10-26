import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class radioFaros {

    // Todos los valores de tiempo están en segundos.
    private static final int NUMERO_HILOS = 2;
    private static final int PAUSA_INICIAL_A = 0;
    private static final int PAUSA_REPETICION_A = 3;
    private static final int PAUSA_INICIAL_B = 3;
    private static final int PAUSA_REPETICION_B = 2;
    private static final int DURACION_EJECUTOR = 21;
    Emision emisionA = new Emision();
    Emision emisionB = new Emision();

    // Objeto "Runnable".
    class Tarea_A implements Runnable {
        @Override
        public void run() {
            emisionA.setEmision(true);
            String hayColision = null;
            if(emisionB.isEmision()){
                hayColision = "COLISIÓN";
            }else{
                hayColision = "NO COLISIÓN";
            }
            Calendar l_Calendario = new GregorianCalendar();
            System.out.print(l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" +
                    l_Calendario.get(Calendar.MINUTE) + ":" + l_Calendario.get(Calendar.SECOND));
            System.out.println(" >  A .- > " + hayColision);
            emisionA.setEmision(false);
        }   //run()
    }   // TAREA_A
    class Tarea_B implements Runnable {
        @Override
        public void run() {
            emisionB.setEmision(true);
            String hayColision = null;
            if(emisionA.isEmision()){
                hayColision = "COLISIÓN";
            }else{
                hayColision = "NO COLISIÓN";
            }
            Calendar l_Calendario = new GregorianCalendar();
            System.out.print(l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" +
                    l_Calendario.get(Calendar.MINUTE) + ":" + l_Calendario.get(Calendar.SECOND));
            System.out.println(" >  B -... > " + hayColision);
            emisionB.setEmision(false);
        }   //run()
    }   // TAREA_B


    public static void main(final String... args) throws InterruptedException, ExecutionException {
        // Mostrar la hora en la que empieza ejecutarse el main().
        Calendar l_Calendario = new GregorianCalendar();

        System.out.println(l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE) +
                ":" + l_Calendario.get(Calendar.SECOND) + " >" + " Inicio aplicación.");

        // Crear un pool de 2 hilos.
        final ScheduledExecutorService l_EjecutorPlan = Executors.newScheduledThreadPool(NUMERO_HILOS);

        // Crear un objeto Runnable.
        final Runnable l_ObjRunnable_A = new radioFaros().new Tarea_A();
        final Runnable l_ObjRunnable_B = new radioFaros().new Tarea_B();

        // Programa el ejecutor, se inicia a los 2 segundos y después se va ejecutando cada 3 segundos.
        l_EjecutorPlan.scheduleWithFixedDelay(l_ObjRunnable_A, PAUSA_INICIAL_A, PAUSA_REPETICION_A, TimeUnit.SECONDS);
        l_EjecutorPlan.scheduleWithFixedDelay(l_ObjRunnable_B, PAUSA_INICIAL_B, PAUSA_REPETICION_B, TimeUnit.SECONDS);

        // Indicar al ejecutor que espere 10 segundos cuando reciba la orden de terminar.
        l_EjecutorPlan.awaitTermination(DURACION_EJECUTOR, TimeUnit.SECONDS);

        // Terminar: no se admiten nuevas tareas, y se finalizan las que aún estén en ejecución.
        l_EjecutorPlan.shutdownNow();
        System.out.println(l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE) +
                ":" + l_Calendario.get(Calendar.SECOND) + " >" + " Fin aplicación.");
    }   // main()

}  // TareaProgramada
