import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TareaProgramada {

    // Todos los valores de tiempo están en segundos.
    private static final int NUMERO_HILOS = 2;
    private static final int PAUSA_INICIAL_A = 0;
    private static final int PAUSA_REPETICION_A = 3;
    private static final int DURACION_EJECUTOR_A = 21;
    private static final int PAUSA_INICIAL_B = 3;
    private static final int PAUSA_REPETICION_B = 2;
    private static final int DURACION_EJECUTOR_B = 21;


    // Objeto "Runnable".
    class Tarea implements Runnable {
        @Override
        public void run() {
            Calendar l_Calendario = new GregorianCalendar();
            System.out.println(l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" +
                    l_Calendario.get(Calendar.MINUTE) + ":" + l_Calendario.get(Calendar.SECOND));
        }   //run()
    }   // EjecutaHilo


    public static void main(final String... args) throws InterruptedException, ExecutionException {
        // Mostrar la hora en la que empieza ejecutarse el main().
        Calendar l_Calendario = new GregorianCalendar();

        System.out.println(l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE) +
                ":" + l_Calendario.get(Calendar.SECOND) + " >" + " Inicio aplicación.");
        // Crear un pool de 2 hilos.
        final ScheduledExecutorService l_EjecutorPlan_A = Executors.newScheduledThreadPool(NUMERO_HILOS);

        // Crear un objeto Runnable.
        final Runnable l_ObjRunnable_A = new TareaProgramada().new Tarea();

        // Programa el ejecutor, se inicia a los 2 segundos y después se va ejecutando cada 3 segundos.
        l_EjecutorPlan_A.scheduleWithFixedDelay(l_ObjRunnable_A, PAUSA_INICIAL_A, PAUSA_REPETICION_A, TimeUnit.SECONDS);

        // Indicar al ejecutor que espere 10 segundos cuando reciba la orden de terminar.
        l_EjecutorPlan_A.awaitTermination(DURACION_EJECUTOR_A, TimeUnit.SECONDS);

        //Lo mismo para b
        // Crear un pool de 2 hilos.
        final ScheduledExecutorService l_EjecutorPlan_B = Executors.newScheduledThreadPool(NUMERO_HILOS);


        // Crear un objeto Runnable.
        final Runnable l_ObjRunnable_B = new TareaProgramada().new Tarea();

        // Programa el ejecutor, se inicia a los 2 segundos y después se va ejecutando cada 3 segundos.
        l_EjecutorPlan_B.scheduleWithFixedDelay(l_ObjRunnable_B, PAUSA_INICIAL_B, PAUSA_REPETICION_B, TimeUnit.SECONDS);

        // Indicar al ejecutor que espere 10 segundos cuando reciba la orden de terminar.
        l_EjecutorPlan_B.awaitTermination(DURACION_EJECUTOR_B, TimeUnit.SECONDS);

        // Terminar: no se admiten nuevas tareas, y se finalizan las que aún estén en ejecución.
        l_EjecutorPlan_A.shutdownNow();
        l_EjecutorPlan_B.shutdownNow();
        System.out.println(l_Calendario.get(Calendar.HOUR_OF_DAY) + ":" + l_Calendario.get(Calendar.MINUTE) +
                ":" + l_Calendario.get(Calendar.SECOND) + " >" + " Final aplicación.");
    }   // main()

}  // TareaProgramada
