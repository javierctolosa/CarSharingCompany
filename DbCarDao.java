package carsharing;

import java.util.List;

public class DbCarDao implements CarDao {
    private final DbClient dbClient;

    public DbCarDao() {
        dbClient = new DbClient();
        String CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS CAR (
                ID INT PRIMARY KEY AUTO_INCREMENT,
                NAME VARCHAR(255) UNIQUE NOT NULL,
                COMPANY_ID INT NOT NULL,
                CONSTRAINT fk_company FOREIGN KEY (company_id)
                REFERENCES company(id)
                );
                """;
        dbClient.run(CREATE_TABLE);
    }

    @Override
    public List<Car> findAll() {
        String SELECT_ALL = """
                SELECT *
                FROM CAR;
                """;
        return dbClient.selectCarList(SELECT_ALL);
    }

    @Override
    public Car findById(int id) {
        String SELECT_ID = """
                SELECT *
                FROM CAR
                WHERE ID = ?;
                """;
        return dbClient.selectCarList(SELECT_ID, id).getFirst();
    }

    @Override
    public List<Car> findByCompanyId(int id) {
        String SELECT_ID = """
                SELECT *
                FROM CAR
                WHERE COMPANY_ID = ?;
                """;
        return dbClient.selectCarList(SELECT_ID, id);
    }

    @Override
    public List<Car> findByCompanyIdNotRented(int id) {
        String SELECT_ID = """
                SELECT *
                FROM CAR
                WHERE COMPANY_ID = ?
                AND ID NOT IN (SELECT RENTED_CAR_ID
                               FROM CUSTOMER
                               WHERE RENTED_CAR_ID IS NOT NULL);
                """;
        return dbClient.selectCarList(SELECT_ID, id);
    }


    @Override
    public void add(Car car) {
        String INSERT_INTO = """
                INSERT INTO CAR (NAME, COMPANY_ID)
                VALUES (?, ?);
                """;
        dbClient.run(INSERT_INTO, car);
    }

    @Override
    public void update(Car car) {
        String UPDATE = """
                UPDATE CAR
                SET NAME = ?, COMPANY_ID = ?
                WHERE ID = ?;
                """;
        dbClient.run(UPDATE, car);
    }

    @Override
    public void delete(Car car) {
        String DELETE = """
                DELETE FROM CAR
                WHERE ID = ?;
                """;
        dbClient.run(DELETE, car);
    }
}
