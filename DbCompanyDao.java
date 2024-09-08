package carsharing;

import java.util.List;

public class DbCompanyDao implements CompanyDao {

    private final DbClient dbClient;

    public DbCompanyDao() {
        dbClient = new DbClient();
        String CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS COMPANY (
                ID INT PRIMARY KEY AUTO_INCREMENT,
                NAME VARCHAR(255) UNIQUE NOT NULL
                )
                """;
        dbClient.run(CREATE_TABLE);
    }

    @Override
    public List<Company> findAll() {
        String SELECT_ALL = """
                SELECT *
                FROM COMPANY;
                """;
        return dbClient.selectCompanyList(SELECT_ALL);
    }

    @Override
    public List<Company> findById(int id) {
        String SELECT_ID = """
                SELECT *
                FROM COMPANY
                WHERE ID = ?;
                """;
        return dbClient.selectCompanyList(SELECT_ID, id);
    }

    @Override
    public void add(Company company) {
        String INSERT_INTO = """
                INSERT INTO COMPANY (NAME)
                VALUES (?);
                """;
        dbClient.run(INSERT_INTO, company);
    }

    @Override
    public void update(Company company) {
        String UPDATE = """
                UPDATE COMPANY
                SET NAME = ?
                WHERE ID = ?;
                """;
        dbClient.run(UPDATE, company);
    }

    @Override
    public void delete(Company company) {
        String DELETE = """
                DELETE FROM COMPANY
                WHERE ID = ?;
                """;
        dbClient.run(DELETE, company);
    }
}
