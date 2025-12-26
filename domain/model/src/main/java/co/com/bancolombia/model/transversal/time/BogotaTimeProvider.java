package co.com.bancolombia.model.transversal.time;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class BogotaTimeProvider {
    private static final ZoneId BOGOTA_ZONE = ZoneId.of("America/Bogota");

    private BogotaTimeProvider() {
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(BOGOTA_ZONE);
    }
}
