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
}
