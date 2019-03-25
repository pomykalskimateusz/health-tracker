package health.tracker.repository.plan;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Builder
public class Plan
{
    private Long id;
    private String day;
    private Long productId;

    public static Plan empty()
    {
        return Plan
                .builder()
                .id(0L)
                .day("")
                .productId(0L)
                .build();
    }
}
