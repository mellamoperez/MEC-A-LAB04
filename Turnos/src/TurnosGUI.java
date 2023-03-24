import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

public class TurnosGUI extends JFrame implements ActionListener {

    private final JTextField tfNombre;
    private final JTextField tfEdad;
    private final JTextField tfAfiliacion;
    private final JTextField tfCondicion;
    private final JTextArea taTurnos;
    private final JButton btnNuevoTurno;
    private final JButton btnExtenderTiempo;
    private final JLabel lblTurno;
    private final JLabel lblTiempoRestante;
    private final JLabel lblTurnosPendientes;
    private final Queue<Paciente> colaTurnos;
    private Paciente pacienteEnCurso;
    private int tiempoRestante;

    public TurnosGUI() {
        // Configurar la ventana
        setTitle("Asignación de turnos en EPS");
        setSize(650, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear los componentes de la interfaz
        tfNombre = new JTextField();
        tfEdad = new JTextField();
        tfAfiliacion = new JTextField();
        tfCondicion = new JTextField();
        taTurnos = new JTextArea();
        btnNuevoTurno = new JButton("Nuevo turno");
        btnExtenderTiempo = new JButton("Extender tiempo");
        lblTurno = new JLabel("Turno en curso:");
        lblTiempoRestante = new JLabel("Tiempo restante:");
        lblTurnosPendientes = new JLabel("Turnos pendientes:");

        // Añadir los componentes a la ventana
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 4));
        panel.add(new JLabel("Nombre completo :"));
        panel.add(tfNombre);
        panel.add(new JLabel("Edad:"));
        panel.add(tfEdad);
        panel.add(new JLabel("Afiliación (POS o PC):"));
        panel.add(tfAfiliacion);
        panel.add(new JLabel("Condición especial (embarazo o limitación motriz):"));
        panel.add(tfCondicion);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevoTurno);
        panelBotones.add(btnExtenderTiempo);

        JPanel panelTurnos = new JPanel();
        panelTurnos.setLayout(new BoxLayout(panelTurnos, BoxLayout.Y_AXIS));
        panelTurnos.add(lblTurno);
        panelTurnos.add(lblTiempoRestante);
        panelTurnos.add(lblTurnosPendientes);
        panelTurnos.add(taTurnos);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);
        container.add(panelBotones, BorderLayout.CENTER);
        container.add(panelTurnos, BorderLayout.SOUTH);

        // Inicializar la cola de turnos
        colaTurnos = new LinkedList<>();

        // Añadir los escuchadores de eventos a los botones
        btnNuevoTurno.addActionListener(this);
        btnExtenderTiempo.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNuevoTurno) {
            // Crear un nuevo paciente con los datos ingresados por el usuario
            String nombre = tfNombre.getText();
            int edad = Integer.parseInt(tfEdad.getText());
            String afiliacion = tfAfiliacion.getText();
            String condicion = tfCondicion.getText();
            Paciente paciente = new Paciente(nombre, edad, afiliacion, condicion);

            // Añadir el paciente a la cola de turnos
            colaTurnos.offer(paciente);

            // Mostrar los turnos pendientes en la cola
taTurnos.setText("Turnos pendientes:\n");
for (Paciente p : colaTurnos) {
taTurnos.append("- " + p.getNombre() + "\n");
}
        // Si no hay ningún paciente en curso, empezar a atender al primero de la cola
        if (pacienteEnCurso == null) {
            atenderSiguientePaciente();
        }
    } else if (e.getSource() == btnExtenderTiempo) {
        // Extender el tiempo del paciente en curso en 5 segundos
        tiempoRestante += 5;
        actualizarTiempoRestante();
    }
}

private void atenderSiguientePaciente() {
    // Tomar el siguiente paciente de la cola
    pacienteEnCurso = colaTurnos.poll();

    // Mostrar el turno del paciente en curso
    lblTurno.setText("Turno en curso: " + pacienteEnCurso.getNombre());

    // Empezar el contador de tiempo restante
    tiempoRestante = 5;
    actualizarTiempoRestante();

    // Mostrar los turnos pendientes en la cola
    taTurnos.setText("Turnos pendientes:\n");
    for (Paciente p : colaTurnos) {
        taTurnos.append("- " + p.getNombre() + "\n");
    }

    // Si hay pacientes en la cola, programar el siguiente turno para 5 segundos después
    if (!colaTurnos.isEmpty()) {
        Timer timer = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atenderSiguientePaciente();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

private void actualizarTiempoRestante() {
    // Mostrar el tiempo restante en la etiqueta correspondiente
    lblTiempoRestante.setText("Tiempo restante: " + tiempoRestante + " segundos");

    // Si se acaba el tiempo, programar el siguiente turno inmediatamente
    if (tiempoRestante == 0) {
        atenderSiguientePaciente();
    } else {
        // Si no se acaba el tiempo, restar un segundo y programar la actualización de nuevo
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                actualizarTiempoRestante();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

private static class Paciente {
    private String nombre;
    private int edad;
    private String afiliacion;
    private String condicion;

    public Paciente(String nombre, int edad, String afiliacion, String condicion) {
        this.nombre = nombre;
        this.edad = edad;
        this.afiliacion = afiliacion;
        this.condicion = condicion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getAfiliacion() {
        return afiliacion;
    }

    public String getCondicion() {
        return condicion;
    }
}

public static void main(String[] args) {
    // Crear y mostrar la interfaz de usuario
    TurnosGUI gui = new TurnosGUI();
    gui.setVisible(true);
}

    private static class IbITurno {

        public IbITurno() {
        }
    }
}