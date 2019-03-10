package health.tracker.repository.profile;

public class ProfileModel
{
    private Integer id;
    private String name;
    private Double age;
    private Double height;
    private Double weight;

    public ProfileModel(String name, Double age, Double height, Double weight)
    {
        setName(name);
        setAge(age);
        setHeight(height);
        setWeight(weight);
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getAge()
    {
        return age;
    }

    public void setAge(Double age)
    {
        this.age = age;
    }

    public Double getHeight()
    {
        return height;
    }

    public void setHeight(Double height)
    {
        this.height = height;
    }

    public Double getWeight()
    {
        return weight;
    }

    public void setWeight(Double weight)
    {
        this.weight = weight;
    }
}
