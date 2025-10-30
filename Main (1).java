import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        String[] datosCompartidos = new String[15];
        Posicion indice = new Posicion();
        Scanner scanner = new Scanner(System.in);
        HiloEscribe hiloPrincipal = null; 
        boolean salir = false;
        System.out.println("Menu de Control de Hilos");
        
        while (!salir) {
            System.out.println("\n-------------------------");
            System.out.println("1. Crear/Reiniciar Hilo");
            System.out.println("2. Pausar Hilo"); 
            System.out.println("3. Continuar Hilo");
            System.out.println("4. Terminar Hilo");
            System.out.println("5. Mostrar Arreglo");
            System.out.println("6. Mostrar Estado del Hilo");
            System.out.println("0. Salir del Programa");
            System.out.print("Elige una opcion: ");
            int opcion = -1;
            try {
                opcion = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Entrada invalida. Intenta con un numero.");
                scanner.next(); 
                continue;
            }
            switch (opcion) {
                case 1:
                    if (hiloPrincipal != null && hiloPrincipal.isAlive()) {
                        terminarHilo(hiloPrincipal);
                        try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                    }
                    hiloPrincipal = crearHilo(datosCompartidos, indice);
                    break;
                case 2:
                    pausarHilo(hiloPrincipal);
                    break;
                case 3:
                    continuarHilo(hiloPrincipal);
                    break;
                case 4:
                    terminarHilo(hiloPrincipal);
                    break;
                case 5:
                    mostrarArreglo(datosCompartidos);
                    break;
                case 6:
                    mostrarEstado(hiloPrincipal);
                    break;
                case 0:
                    salir = true;
                    terminarHilo(hiloPrincipal); 
                    System.out.println("Saliendo del programa. Adios!");
                    break;
                default:
                    System.out.println("Opcion no reconocida. Vuelve a intentarlo.");
            }
        }
        scanner.close(); 
    }
    
    private static HiloEscribe crearHilo(String[] datos, Posicion p) {
        for (int i = 0; i < datos.length; i++) {
            datos[i] = null;
        }
        p.setP(0);
        HiloEscribe nuevoHilo = new HiloEscribe(p);
        nuevoHilo.setName("Escriba-1");
        nuevoHilo.setInfo(datos);
        nuevoHilo.setIni(100);
        nuevoHilo.setTmp(150);
        nuevoHilo.setTerminar(false);
        nuevoHilo.setPausar(false);
        nuevoHilo.start();
        System.out.println("Hilo Creado y **Iniciado** (Nombre: " + nuevoHilo.getName() + ")");
        return nuevoHilo;
    }
 
    private static void pausarHilo(HiloEscribe h) {
        if (h != null && h.isAlive()) {
            h.setPausar(true);
            System.out.println("Hilo **PAUSADO** (Nombre: " + h.getName() + ")");
        } else {
            System.out.println("No hay hilo vivo para pausar.");
        }
    }

    private static void continuarHilo(HiloEscribe h) {
        if (h != null && h.isAlive()) {
            h.setPausar(false);
            System.out.println("Hilo **CONTINUADO** (Nombre: " + h.getName() + ")");
        } else {
            System.out.println("No hay hilo para continuar o no ha sido creado.");
        }
    }
 
    private static void terminarHilo(HiloEscribe h) {
        if (h != null && h.isAlive()) {
            h.setTerminar(true); 
            h.setPausar(false); 
            h.interrupt();        
            System.out.println("Senal de terminacion enviada a " + h.getName());
        } else if (h != null) {
            System.out.println("Hilo **ya termino** o no esta vivo.");
        }
    }
    private static void mostrarArreglo(String[] datos) {
        System.out.println("\nContenido del Arreglo (Datos Compartidos):");
        for (int i = 0; i < datos.length; i++) {
            String contenido = (datos[i] != null) ? datos[i] : "---";
            System.out.println("  [" + i + "]: " + contenido);
        }
    }
    private static void mostrarEstado(HiloEscribe h) {
        if (h != null) {
            System.out.println("\nEstado del Hilo " + h.getName() + ":");
            System.out.println("  - Estado Java: **" + h.getState() + "**");
            System.out.println("  - Esta vivo?: " + h.isAlive());
            System.out.println("  - Fue interrumpido?: " + h.isInterrupted());
        } else {
            System.out.println("El hilo no ha sido creado aun.");
        }
    }
}
 