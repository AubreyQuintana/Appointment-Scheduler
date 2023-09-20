package utility;

import java.time.*;

public class TimeConversion {

    LocalDate myLD = LocalDate.of(2023, 9, 19); //dont need if using date picker

    LocalTime myLT = LocalTime.of(10, 16); //use combo box on add/update controller to display time options every 30 minutes or every hour

    LocalDateTime myLDT = LocalDateTime.of(myLD, myLT);

    ZoneId myZoneID = ZoneId.systemDefault();

    ZonedDateTime myZDT = ZonedDateTime.of(myLDT, myZoneID);

    ZoneId utcZoneID = ZoneId.of("UTC");
    ZonedDateTime utcZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), utcZoneID);

}
