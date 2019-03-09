package health.tracker.repository;

public interface Repository<MODEL>
{
    boolean save(MODEL model);
}
