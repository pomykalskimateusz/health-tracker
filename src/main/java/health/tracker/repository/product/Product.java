package health.tracker.repository.product;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Builder
public class Product
{
    private Long id;
    private String name;
    private Double calorific;

    public static Product empty()
    {
        return Product
                .builder()
                .id(0L)
                .name("")
                .calorific(0d)
                .build();
    }
}
