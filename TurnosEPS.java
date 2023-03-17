import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public static void main(String[] args) {
    new TurnosEPS();
}

@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == crearBtn) {
        // Crear nuevo turno
        String nombre = nombreTxt.getText();
        int edad = Integer.parseInt(edadTxt.getText());
        String afiliacion = (String) afiliacionCmb.getSelectedItem();
        boolean embarazo = embarazoChk.isSelected();
        boolean limitacion = limitacionChk.isSelected();
        Paciente paciente = new Paciente(nombre, edad, afiliacion, embarazo, limitacion);

        // Agregar paciente a cola de turnos
        boolean prioritario = paciente.esPrioritario();
        if (prioritario && !colaTurnos.isEmpty()) {
            // Insertar paciente prioritario en el primer lugar de la cola
            colaTurnos.add(paciente);
            int i = 0;
            for (Paciente p : colaTurnos) {
                if (p == paciente) {
                    break;
                }
                i++;
            }
            colaTurnos.add(colaTurnos.remove(i));
        } else {
            // Agregar paciente a la cola al final
            colaTurnos.add(paciente);
        }

        // Mostrar turno al paciente
        turnoLbl.setText(paciente.getTurno());
        tiempoLbl.setText("");
        pendientesLbl.setText(Integer.toString(colaTurnos.size() - 1));
        extenderBtn.setEnabled(true);
        tiempoRestante = 300;
    } else if (e.getSource() == extenderBtn) {
        // Extender tiempo del paciente en curso
        tiempoRestante += 60;
    }
}

private class Paciente {
    private final String nombre;
    private final int edad;
    private final String afiliacion;
    private final boolean embarazo;
    private final boolean limitacion;
    private final String turno;

    public Paciente(String nombre, int edad, String afiliacion, boolean embarazo, boolean limitacion) {
        this.nombre = nombre;
        this.edad = edad;
        this.afiliacion = afiliacion;
        this.embarazo = embarazo;
        this.limitacion = limitacion;
        this.turno = generarTurno();
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

    public boolean isEmbarazo() {
        return embarazo;
    }

    public boolean isLimitacion() {
        return limitacion;
    }

    public String getTurno() {
        return turno;
    }

    public boolean esPrioritario() {
        return edad >= 60 || edad <= 12 || embarazo || limitacion || afiliacion.equals("PC");
    }

    private String generarTurno() {
        String letra = "";
        if (colaTurnos.isEmpty()) {
            letra = "A";
        } else {
            String ultimoTurno;
            ultimoTurno = colaTurnos.getLast().getTurno();
            char ultimaLetra = ultimoTurno.charAt(0);
            if (ultimaLetra == 'Z') {
                letra = "AA";
            } else if (ultimoTurno.length() == 1) {
                letra = String.valueOf((char) (ultimaLetra + 1));
            } else {
                char penultimaLetra = ultimoTurno.charAt(1);
                if (penultimaLetra == 'Z') {
                    letra = String.valueOf((char) (ultimaLetra + 1)) + "A";
                } else {
                    letra = String.valueOf(ultimaLetra) + String.valueOf((char) (penultimaLetra + 1));
                }
            }
        }
        return letra + (colaTurnos.size() + 1);
    }
}
}