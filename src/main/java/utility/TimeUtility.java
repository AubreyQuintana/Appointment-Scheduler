package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.*;

/**
 * This class holds time conversion properties.
 * @author Aubrey Quintana
 */
public class TimeUtility {

    private static final ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static final ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();

    /**
     * This method converts the business hours 8AM-10PM EST to the timezone on the user's local system
     */
    public static void loadLocalTimes() {
        ZonedDateTime easternStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        LocalDateTime localStart = easternStart.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localEnd = localStart.plusHours(14);

        while (localStart.isBefore(localEnd)) {
            startTimes.add(localStart.toLocalTime());
            localStart = localStart.plusMinutes(15);
            endTimes.add(localStart.toLocalTime());
        }
    }

    public static ObservableList<LocalTime> getStartTimes() {
        if (startTimes.isEmpty()) {
            loadLocalTimes();
        }
        return startTimes;
    }

    public static ObservableList<LocalTime> getEndTimes() {
        if (endTimes.isEmpty()) {
            loadLocalTimes();
        }
        return endTimes;
    }
}
