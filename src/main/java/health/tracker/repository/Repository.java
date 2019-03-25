package health.tracker.repository;

import java.util.List;

public interface Repository<MODEL>
{
    boolean save(MODEL model);

    List<MODEL> findAll();

    MODEL findById(Long id);
}
