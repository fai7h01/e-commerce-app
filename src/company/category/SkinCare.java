package company.category;

import java.time.LocalDateTime;
import java.util.UUID;

public class SkinCare extends Category{

    public SkinCare(UUID id, String name) {
        super(id, name);
    }

    @Override
    public LocalDateTime calculateDeliveryTime() {
        return LocalDateTime.now();
    }
}
