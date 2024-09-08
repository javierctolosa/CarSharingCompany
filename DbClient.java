package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbClient {

    private static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private final JdbcDataSource dataSource = new JdbcDataSource();

    public DbClient() {
        dataSource.setURL(DB_URL);
    }

    public void run(String statement) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement)
        ) {
            conn.setAutoCommit(true);
            prepStatement.executeUpdate();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void run(String statement, Company company) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement)
        ) {
            conn.setAutoCommit(true);

            int id = company.getId();
            String name = company.getName();

            String statementType = statement.split(" ")[0];
            switch (statementType) {
                case "INSERT" -> prepStatement.setString(1, name);
                case "UPDATE" -> {
                    prepStatement.setString(1, name);
                    prepStatement.setInt(2, id);
                }
                case "DELETE" -> prepStatement.setInt(1, id);
            }
            prepStatement.executeUpdate();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void run(String statement, Car car) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement)
        ) {
            conn.setAutoCommit(true);

            int id = car.getId();
            String name = car.getName();
            int company_id = car.getCompany_id();

            String statementType = statement.split(" ")[0];
            switch (statementType) {
                case "INSERT" -> {
                    prepStatement.setString(1, name);
                    prepStatement.setInt(2, company_id);
                }
                case "UPDATE" -> {
                    prepStatement.setString(1, name);
                    prepStatement.setInt(2, company_id);
                    prepStatement.setInt(3, id);
                }
                case "DELETE" -> prepStatement.setInt(1, id);
            }
            prepStatement.executeUpdate();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void run(String statement, Customer customer) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement)
        ) {
            conn.setAutoCommit(true);

            int id = customer.getId();
            String name = customer.getName();
            int rented_car_id = customer.getRented_car_id();

            String statementType = statement.split(" ")[0];
            switch (statementType) {
                case "INSERT" -> {
                    prepStatement.setString(1, name);
                }
                case "UPDATE" -> {
                    if (rented_car_id == 0) {
                        prepStatement.setNull(1, Types.INTEGER);
                    } else {
                        prepStatement.setInt(1, rented_car_id);
                    }
                    prepStatement.setInt(2, id);
                }
                case "DELETE" -> prepStatement.setInt(1, id);
            }
            prepStatement.executeUpdate();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public List<Company> selectCompanyList(String statement) {
        List<Company> companies = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement);
             ResultSet resultSetItem = prepStatement.executeQuery()
        ) {
            conn.setAutoCommit(true);
            while (resultSetItem.next()) {
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                Company company = new Company(id, name);
                companies.add(company);
            }
            return companies;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return companies;
    }

    public List<Car> selectCarList(String statement) {
        List<Car> cars = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement);
             ResultSet resultSetItem = prepStatement.executeQuery()
        ) {
            conn.setAutoCommit(true);
            while (resultSetItem.next()) {
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                int company_id = resultSetItem.getInt("company_id");
                Car car = new Car(id, name, company_id);
                cars.add(car);
            }
            return cars;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return cars;
    }

    public List<Customer> selectCustomerList(String statement) {
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement);
             ResultSet resultSetItem = prepStatement.executeQuery()
        ) {
            conn.setAutoCommit(true);
            while (resultSetItem.next()) {
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                int rented_car_id = resultSetItem.getInt("rented_car_id");
                Customer customer = new Customer(id, name, rented_car_id);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return customers;
    }

    public List<Company> selectCompanyList(String statement, int id) {
        List<Company> companies = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement)
        ) {
            prepStatement.setInt(1, id);
            ResultSet resultSetItem = prepStatement.executeQuery();
            conn.setAutoCommit(true);
            while (resultSetItem.next()) {
                String name = resultSetItem.getString("name");
                Company company = new Company(id, name);
                companies.add(company);
            }
            return companies;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return companies;
    }

    public List<Car> selectCarList(String statement, int id) {
        List<Car> cars = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement)
        ) {
            prepStatement.setInt(1, id);
            ResultSet resultSetItem = prepStatement.executeQuery();
            conn.setAutoCommit(true);
            while (resultSetItem.next()) {
                int carId = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                int company_id = resultSetItem.getInt("company_id");
                Car car = new Car(carId, name, company_id);
                cars.add(car);
            }
            return cars;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return cars;
    }

    public List<Customer> selectCustomerList(String statement, int id) {
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement prepStatement = conn.prepareStatement(statement)
        ) {
            prepStatement.setInt(1, id);
            ResultSet resultSetItem = prepStatement.executeQuery();
            conn.setAutoCommit(true);
            while (resultSetItem.next()) {
                String name = resultSetItem.getString("name");
                int rented_car_id = resultSetItem.getInt("rented_car_id");
                Customer customer = new Customer(id, name, rented_car_id);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return customers;
    }
}
