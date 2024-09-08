package carsharing;

import java.util.List;

public interface CarDao {
    List<Car> findAll();
    Car findById(int id);
    public List<Car> findByCompanyId(int id);
    public List<Car> findByCompanyIdNotRented(int id);
    void add(Car car);
    void update(Car car);
    void delete(Car car);
}
