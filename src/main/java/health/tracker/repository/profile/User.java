package health.tracker.repository.profile;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Builder
public class User
{
    private Long id;
    private String name;
    private Double age;
    private Double height;
    private Double weight;

    public static User empty()
    {
        return User
                .builder()
                .name("")
                .age(0d)
                .height(0d)
                .weight(0d)
                .build();
    }
}
