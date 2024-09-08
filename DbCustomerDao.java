package carsharing;

import java.util.List;

public class DbCustomerDao implements CustomerDao {
    private final DbClient dbClient;

    public DbCustomerDao() {
        this.dbClient = new DbClient();
        String CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS CUSTOMER (
                ID INT PRIMARY KEY AUTO_INCREMENT,
                NAME VARCHAR(255) UNIQUE NOT NULL,
                RENTED_CAR_ID INT DEFAULT NULL,
                CONSTRAINT fk_car FOREIGN KEY (rented_car_id)
                REFERENCES car(id)
                );
                """;
        dbClient.run(CREATE_TABLE);
    }

    @Override
    public List<Customer> findAll() {
        String SELECT_ALL = """
                SELECT *
                FROM CUSTOMER;
                """;
        return dbClient.selectCustomerList(SELECT_ALL);
    }

    @Override
    public List<Customer> findById(int id) {
        String SELECT_ID = """
                SELECT *
                FROM CUSTOMER
                WHERE ID = ?;
                """;
        return dbClient.selectCustomerList(SELECT_ID, id);
    }

    @Override
    public void add(Customer customer) {
        String INSERT_INTO = """
                INSERT INTO CUSTOMER (NAME)
                VALUES (?);
                """;
        dbClient.run(INSERT_INTO, customer);
    }

    @Override
    public void update(Customer customer) {
        String UPDATE = """
                UPDATE CUSTOMER
                SET RENTED_CAR_ID = ?
                WHERE ID = ?;
                """;
        dbClient.run(UPDATE, customer);
    }

    @Override
    public void delete(Customer customer) {
        String DELETE = """
                DELETE FROM CUSTOMER
                WHERE ID = ?;
                """;
        dbClient.run(DELETE, customer);
    }
}
