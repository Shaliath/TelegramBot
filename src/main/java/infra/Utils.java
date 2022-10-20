package infra;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    public static String prettifyDate(Date date) {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                .withZone(ZoneId.of("Etc/UTC"))
                .format(date.toInstant());
    }

}
