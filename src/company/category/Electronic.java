package company.category;

import java.time.LocalDateTime;
import java.util.UUID;

public class Electronic extends Category{

    public Electronic(UUID id, String name) {
        super(id, name);
    }

    @Override
    public LocalDateTime calculateDeliveryTime() {
        return LocalDateTime.now().plusDays(4);
    }

    @Override
    public String generateCategoryCode() {
        return "EL-" + getId().toString().substring(0,8);
    }
}
