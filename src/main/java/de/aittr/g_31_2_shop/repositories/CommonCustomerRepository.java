package de.aittr.g_31_2_shop.repositories;

import de.aittr.g_31_2_shop.domain.CommonCart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.repositories.interfaces.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import static de.aittr.g_31_2_shop.repositories.DBConnector.getConnection;

@Repository
public class CommonCustomerRepository implements CustomerRepository {

    private final String ID = "id";
    private final String NAME = "name";

    @Override
    public Customer save(Customer customer) {
        try (Connection connection = getConnection()) {

            String query = String.format("INSERT INTO `customer` (`name`, `is_active`) VALUES ('%s', '1');", customer.getName());
            connection.createStatement().execute(query);

            query = "select id from customer order by id desc limit 1;";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            resultSet.next();

            int customer_id = resultSet.getInt(ID);
            customer.setId(customer_id);

            query = String.format("INSERT INTO `cart` (`customer_id`) VALUES ('%d');", customer_id);
            connection.createStatement().execute(query);

            query = "select id from cart order by id desc limit 1;";
            resultSet = connection.createStatement().executeQuery(query);
            resultSet.next();

            int cart_id = resultSet.getInt(ID);
            customer.setCart(new CommonCart(cart_id));

            return customer;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getAll() {
        try (Connection connection = getConnection()) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
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
