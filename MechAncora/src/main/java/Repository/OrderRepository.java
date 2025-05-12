package Repository;

import Entity.Order;
import Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Order findByUser(User user);
}
