package carsharing;

import java.util.List;

public interface CompanyDao {
    List<Company> findAll();;
    List<Company> findById(int id);
    void add(Company company);
    void update(Company company);
    void delete(Company company);
}
