package carsharing;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll();
    List<Customer> findById(int id);
    void add(Customer customer);
    void update(Customer customer);
    void delete(Customer customer);
}
