package carsharing;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

        DbCompanyDao dbCompanyDao = new DbCompanyDao();
        DbCarDao dbCarDao = new DbCarDao();
        DbCustomerDao dbCustomerDao = new DbCustomerDao();

        boolean mainMenu = true;
        while (mainMenu) {
            System.out.println("""
                    1. Log in as a manager
                    2. Log in as a customer
                    3. Create a customer
                    0. Exit
                    """);
            Scanner scanner = new Scanner(System.in);
            int selection = scanner.nextInt();

            switch (selection) {
                case 1 -> managerMenu(dbCompanyDao, dbCarDao, scanner);
                case 2 -> customerMenu(dbCustomerDao, dbCarDao, dbCompanyDao, scanner);
                case 3 -> createCustomer(dbCustomerDao, scanner);
                case 0 -> mainMenu = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void managerMenu(DbCompanyDao dbCompanyDao, DbCarDao dbCarDao, Scanner scanner) {
        boolean inManagerMenu = true;
        while (inManagerMenu) {
            System.out.println("""
                    
                    1. Company list
                    2. Create a company
                    0. Back
                    """);

            int selection = scanner.nextInt();

            switch (selection) {
                case 1 -> {
                    List<Company> companyList = dbCompanyDao.findAll();
                    if (companyList.isEmpty()) {
                        System.out.println("The company list is empty!\n");
                    } else {
                        System.out.println("\nChoose the company:");
                        companyList.forEach(company -> System.out.println(company.toString()));
                        System.out.println("0. Back");
                        carMenu(dbCompanyDao, dbCarDao, scanner);
                    }
                }
                case 2 -> {
                    System.out.println("Enter the company name:\n");
                    scanner.nextLine();
                    String companyName = scanner.nextLine();
                    if (!companyName.isEmpty()) {
                        Company newCompany = new Company(companyName);
                        dbCompanyDao.add(newCompany);
                        System.out.println("The company was created!\n");
                    } else {
                        System.out.println("Invalid company name.\n");
                    }
                }
                case 0 -> inManagerMenu = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void carMenu(DbCompanyDao dbCompanyDao,DbCarDao dbCarDao, Scanner scanner) {
        int company_id = scanner.nextInt();
        List<Company> companyList = dbCompanyDao.findById(company_id);

        if (company_id != 0 && companyList.isEmpty()) {
            System.out.println("Invalid company id.");
        }
        else if (company_id != 0) {
            Company company = companyList.getFirst();
            System.out.printf("\n'%1$s' company", company.getName());

            boolean inCarMenu = true;
            while (inCarMenu) {
            System.out.println("""
                
                1. Car list
                2. Create a car
                0. Back
                """);

                int selection = scanner.nextInt();

                switch (selection) {
                    case 1 -> {
                        List<Car> carList = dbCarDao.findByCompanyId(company_id);
                        if (carList.isEmpty()) {
                            System.out.println("The car list is empty!\n");
                        } else {
                            System.out.println("Car list:");
                            IntStream.range(0, carList.size())
                                    .forEach( i -> System.out.printf("%d. %s\n", i + 1, carList.get(i).getName()));
                        }
                    }
                    case 2 -> {
                        System.out.println("Enter the car name:\n");
                        scanner.nextLine();
                        String carName = scanner.nextLine();
                        if (!carName.isEmpty()) {
                            Car newCar = new Car(carName, company_id);
                            dbCarDao.add(newCar);
                            System.out.println("The car was created!\n");
                        } else {
                            System.out.println("Invalid car name.\n");
                        }
                    }
                    case 0 -> inCarMenu = false;
                    default -> System.out.println("Invalid option.");
                }
            }
        }
    }

    private static void customerMenu(DbCustomerDao dbCustomerDao,
                                     DbCarDao dbCarDao,
                                     DbCompanyDao dbCompanyDao,
                                     Scanner scanner) {

        List<Customer> allCustomersList = dbCustomerDao.findAll();
        if (allCustomersList.isEmpty()) {
            System.out.println("\nThe customer list is empty!");
        } else {
            System.out.println("Customer list:");
            allCustomersList.forEach(customer -> System.out.println(customer.toString()));
            System.out.println("0. Back");

            int customer_id = scanner.nextInt();

            List<Customer> customerList = dbCustomerDao.findById(customer_id);

            if (customer_id != 0) {
                if (customerList.isEmpty()) {
                    System.out.println("Invalid customer id.");

                } else {

                    boolean inCustomerMenu = true;
                    while (inCustomerMenu) {

                        Customer customer = dbCustomerDao.findById(customer_id).getFirst();

                        System.out.println("""
                                
                                1. Rent a car
                                2. Return a rented car
                                3. My rented car
                                0. Back
                                """);

                        int cust_rented_car_id = customer.getRented_car_id();

                        int selection = scanner.nextInt();
                        switch (selection) {
                            case 1 -> {
                                if(cust_rented_car_id != 0) {
                                    System.out.println("You've already rented a car!");
                                } else {
                                    List<Company> companiesList = dbCompanyDao.findAll();
                                    if (!companiesList.isEmpty()) {
                                        System.out.println("Choose a company:");
                                        companiesList.forEach(company -> System.out.println(company.toString()));
                                        System.out.println("0. Back");

                                        int company_id = scanner.nextInt();

                                        List<Company> companyList = dbCompanyDao.findById(company_id);
                                        if (company_id != 0) {
                                            if (companyList.isEmpty()) {
                                                System.out.println("Invalid company id.");
                                            } else {
                                                List<Car> carsList = dbCarDao.findByCompanyIdNotRented(company_id);
                                                System.out.println("Choose a car");
                                                if (carsList.isEmpty()) {
                                                    System.out.println("The car list is empty!\n");
                                                } else {
                                                    System.out.println("Car list:");
                                                    IntStream.range(0, carsList.size())
                                                            .forEach( i -> System.out.printf("%d. %s\n",
                                                                    i + 1, carsList.get(i).getName()));
                                                    System.out.println("0. Back");

                                                    int car_selection = scanner.nextInt();

                                                    if (car_selection != 0) {
                                                    int car_id = carsList.get(car_selection - 1).getId();
                                                    String carName = carsList.get(car_selection - 1).getName();
                                                    Customer updateCustomer = new Customer(customer_id,
                                                                                    customer.getName(),
                                                                                    car_id);
                                                    dbCustomerDao.update(updateCustomer);
                                                    System.out.printf("You rented '%s'\n", carName);
                                                    }
                                                }

                                            }
                                        }
                                    } else {
                                        System.out.println("The company list is empty!");
                                    }
                                }
                            }
                            case 2 -> {
                                if (cust_rented_car_id != 0) {
                                    Customer updateCustomer = new Customer(customer_id, customer.getName(), 0);
                                    dbCustomerDao.update(updateCustomer);
                                    System.out.println("You've returned a rented car!");
                                } else {
                                    System.out.println("You didn't rent a car!");
                                }
                            }
                            case 3 -> {
                                if (cust_rented_car_id != 0) {
                                    Car car = dbCarDao.findById(cust_rented_car_id);
                                    int company_id = car.getCompany_id();
                                    Company company = dbCompanyDao.findById(company_id).getFirst();
                                    System.out.printf("""
                                            
                                            Your rented car:
                                            %s
                                            Company:
                                            %s
                                            """, car.getName(), company.getName());

                                } else {
                                    System.out.println("\nYou didn't rent a car!");
                                }
                            }
                            case 0 -> inCustomerMenu = false;
                            default -> System.out.println("Invalid option.");
                        }
                    }
                }
            }
        }
    }

    private static void createCustomer(DbCustomerDao dbCustomerDao, Scanner scanner) {
        System.out.println("Enter the customer name:\n");
        scanner.nextLine();
        String customerName = scanner.nextLine();
        if (!customerName.isEmpty()) {
            Customer newCustomer = new Customer(customerName);
            dbCustomerDao.add(newCustomer);
            System.out.println("The customer was added!\n");
        } else {
            System.out.println("Invalid customer name.\n");
        }
    }
}



















