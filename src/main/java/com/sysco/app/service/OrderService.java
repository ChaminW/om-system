package com.sysco.app.service;

import com.sysco.app.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Service;

>>>>>>> 501fc5a9a25943a59359cae02b976aadb841289f
import java.util.List;

@Service("orderService")
public interface OrderService {

    void createOrder(Order order);

    List<Order> readOrder();

    Page<Order> readOrdersPageable(PageRequest pageRequest);

    List<Order> readOrder(Order order);

    Order readOrder(String id);

    void updateOrder(Order order);

    void deleteOrder(String id);
}
