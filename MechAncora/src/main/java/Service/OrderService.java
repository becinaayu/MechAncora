package Service;


import Entity.Order;
import Entity.User;
import Repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }
    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public void createOrder(Order order){
        orderRepository.save(order);
    }

    public void updateOrder(Order order, Long id){
        orderRepository.findById(order.getId()).orElseThrow(() -> new RuntimeException("Pedido com Id"+ order.getId()+" não foi encontrado."));
        orderRepository.save(order);
    }

    public void deleteOrder(Long id){
        orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido com Id"+ id +" não foi encontrado."));
        orderRepository.deleteById(id);
    }

}
