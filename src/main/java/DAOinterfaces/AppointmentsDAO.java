package DAOinterfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

public class AppointmentsDAO {

    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments() {

        return allAppointments;
    }
    public static void addAppointment(Appointment appointment){

        allAppointments.add(appointment);
    }

}
