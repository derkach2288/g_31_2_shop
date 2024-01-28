package de.aittr.g_31_2_shop.repositories.jdbc;

import de.aittr.g_31_2_shop.domain.jdbc.CommonCart;
import de.aittr.g_31_2_shop.domain.jdbc.CommonCustomer;
import de.aittr.g_31_2_shop.domain.jdbc.CommonProduct;
import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.repositories.interfaces.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.aittr.g_31_2_shop.repositories.jdbc.DBConnector.getConnection;

@Repository
public class CommonCustomerRepository implements CustomerRepository {
    private final String CUSTOMER_ID = "cu.id";
    private final String CART_ID = "ca.id";
    private final String PRODUCT_ID = "pr.id";
    private final String CUSTOMER_NAME = "cu.name";
    private final String PRODUCT_NAME = "pr.name";
    private final String PRICE = "price";


    private final String ID = "id";
    private final String NAME = "name";

    @Override
    public Customer save(Customer customer) {
        try (Connection connection = getConnection()) {

            String query = String.format("INSERT INTO `customer` (`name`, `is_active`) VALUES ('%s', '1');", customer.getName());
            Statement statement = connection.createStatement();
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet resultSet = statement.getGeneratedKeys();// вернет resultset, который вернет id
            resultSet.next();
            int customer_id = resultSet.getInt(1);
            customer.setId(customer_id);

            query = String.format("INSERT INTO `cart` (`customer_id`) VALUES ('%d');", customer_id);
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int cartId = resultSet.getInt(1);

            Cart cart = new CommonCart(cartId);
            customer.setCart(cart);
            return customer;


//            query = "select id from customer order by id desc limit 1;";
//            ResultSet resultSet = connection.createStatement().executeQuery(query);
//            resultSet.next();
//
//            int customer_id = resultSet.getInt(ID);
//            customer.setId(customer_id);
//
//            query = String.format("INSERT INTO `cart` (`customer_id`) VALUES ('%d');", customer_id);
//            connection.createStatement().execute(query);
//
//            query = "select id from cart order by id desc limit 1;";
//            resultSet = connection.createStatement().executeQuery(query);
//            resultSet.next();
//
//            int cart_id = resultSet.getInt(ID);
//            customer.setCart(new CommonCart(cart_id));
//
//            return customer;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getAll() {
        try (Connection connection = getConnection()) {

            String query = "select cu.id, cu.name, ca.id, pr.id, pr.name, pr.price from customer as cu " +
                    "join cart as ca on cu.id = ca.customer_id " +
                    "left join cart_product as cp on ca.id = cp.cart_id " +
                    "left join product as pr on cp.product_id = pr.id " +
                    "where cu.is_active = 1 and (pr.is_active = 1 or pr.is_active is null);";

            ResultSet resultSet = connection.createStatement().executeQuery(query);

            Map<Integer, Customer> customers = new HashMap<>();

            while (resultSet.next()) {

                int customerId = resultSet.getInt(CUSTOMER_ID);
                Customer customer;

                if (customers.containsKey(customerId)) {
                    customer = customers.get(customerId);

                } else {
                    int cartId = resultSet.getInt(CART_ID);
                    Cart cart = new CommonCart(cartId);
                    String customerName = resultSet.getString(CUSTOMER_NAME);
                    customer = new CommonCustomer(customerId, true, customerName, cart);
                    customers.put(customerId, customer);
                }

                int productId = resultSet.getInt(PRODUCT_ID);

                if (productId != 0) {
                    String productName = resultSet.getString(PRODUCT_NAME);
                    double price = resultSet.getDouble(PRICE);
                    Product product = new CommonProduct(productId, true, productName, price);

                    customer.getCart().addProduct(product);
                }
            }

            return new ArrayList<>(customers.values());

//            String query = "SELECT c.id AS customer_id, c.name AS customer_name, cart.id AS cart_id, p.id AS product_id, " +
//                    "p.name AS product_name, p.price AS product_price FROM customer c JOIN cart ON " +
//                    "c.id = cart.customer_id JOIN cart_product cp ON cart.id = cp.cart_id JOIN product p ON " +
//                    "cp.product_id = p.id WHERE c.is_active = '1' AND p.is_active = '1';";
//            ResultSet resultSet = connection.createStatement().executeQuery(query);
//            List<Customer> customers = new ArrayList<>();
//
//            while (resultSet.next()) {
//                int customer_id = resultSet.getInt("customer_id");
//                String customer_name = resultSet.getString("customer_name");
//                int cart_id = resultSet.getInt("cart_id");
//                int product_id = resultSet.getInt("product_id");
//                String product_name = resultSet.getString("product_name");
//                double product_price = resultSet.getDouble("product_price");
//
//                Customer customer = new CommonCustomer(customer_id, true, customer_name, new CommonCart(cart_id, List.of(new CommonProduct(product_id, true, product_name, product_price))));
//                customers.add(customer);
//            }
//
//            return customers;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(int id) {
        try (Connection connection = getConnection()) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void update(Customer customer) {
        try (Connection connection = getConnection()) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = getConnection()) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
