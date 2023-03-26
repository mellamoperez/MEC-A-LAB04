import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Scanner;

public class Parqueadero {

    private static int numVehiculo = 1;
    private static final List<Vehiculo> vehiculos = new ArrayList<>();
    private static final Stack<Vehiculo> motosBicicletas = new Stack<>();
    private static final Stack<Vehiculo> carros = new Stack<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("Bienvenido parqueadero. Seleccione una opciÃ³n:");
            System.out.println("1. Ingreso de vehÃ­culo");
            System.out.println("2. Visualizar tabla actualizada con la informaciÃ³n ingresada e incluya el valor a pagar");
            System.out.println("3. Visualizar en una lista los vehÃ­culos de 2 ruedas e incluir el valor a pagar en total");
            System.out.println("4. Visualizar en una lista los vehÃ­culos de 4 ruedas e incluir el valor a pagar en total");
            System.out.println("5. Cantidad de vehÃ­culos en parqueadero y valor total a pagar");
            System.out.println("6. Eliminar algÃºn vehÃ­culo");
            System.out.println("7. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la lÃ­nea en blanco
            switch(opcion) {
                case 1:
                    ingresarVehiculo(scanner);
                    break;
                case 2:
                    visualizarTablaActualizada();
                    break;
                case 3:
                    visualizarListaVehiculos(motosBicicletas);
                    break;
                case 4:
                    visualizarListaVehiculos(carros);
                    break;
                case 5:
                    cantidadVehiculosParqueadero();
                    break;
                case 6:
                    eliminarVehiculo(scanner);
                    break;
                case 7:
                    System.out.println("Gracias por utilizar el parqueadero.");
                    break;
                default:
                    System.out.println("OpciÃ³n invÃ¡lida. Por favor seleccione una opciÃ³n vÃ¡lida.");
            }
        } while(opcion != 7);
    }
    
    private static void ingresarVehiculo(Scanner scanner) {
        System.out.println("Ingrese la placa del vehÃ­culo:");
        String placa = scanner.nextLine();
        System.out.println("Ingrese el tipo de vehÃ­culo (1 - bicicleta, 2 - ciclomotor, 3 - motocicleta, 4 - carro):");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Consumir la lÃ­nea en blanco
        System.out.println("Ingrese la hora de ingreso (formato 24 horas):");
        int hora = scanner.nextInt();
        scanner.nextLine(); // Consumir la lÃ­nea en blanco
        
        Vehiculo vehiculo = new Vehiculo(numVehiculo, placa, tipo, hora);
        vehiculos.add(vehiculo);
        numVehiculo++;
        
        switch(tipo) {
            case 1:
            case 2:
                motosBicicletas.push(vehiculo);
                break;
            case 3:
                motosBicicletas.push(vehiculo);
                break;
            case 4:
                carros.push(vehiculo);
                break;
            default:
                System.out.println("Tipo de vehÃ­culo invÃ¡lido. No se puede agregar a la lista de vehÃ­culos.");
    }
    
    System.out.println("El vehÃ­culo ha ingresado  al parqueadero.");
}

private static void visualizarTablaActualizada() {
    System.out.printf("%-15s%-15s%-15s%-15s%-15s\n", "NÂ° VehÃ­culo", "Placa", "Tipo", "Hora de ingreso", "Valor a pagar");
    vehiculos.forEach(vehiculo -> {
        System.out.printf("%-15d%-15s%-15s%-15d%-15d\n", vehiculo.getNumVehiculo(), vehiculo.getPlaca(), 
                vehiculo.getTipoVehiculo(), vehiculo.getHoraIngreso(), vehiculo.getValorAPagar());
        });
}

private static void visualizarListaVehiculos(Stack<Vehiculo> lista) {
    int valorTotalAPagar = 0;
    System.out.printf("%-15s%-15s%-15s\n", "NÂ° VehÃ­culo", "Placa", "Valor a pagar");
    valorTotalAPagar = lista.stream().map(vehiculo -> {
        System.out.printf("%-15d%-15s%-15d\n", vehiculo.getNumVehiculo(), vehiculo.getPlaca(), vehiculo.getValorAPagar());
            return vehiculo;
        }).map(vehiculo -> vehiculo.getValorAPagar()).reduce(valorTotalAPagar, Integer::sum);
    System.out.printf("Valor total a pagar: %d\n", valorTotalAPagar);
}

  