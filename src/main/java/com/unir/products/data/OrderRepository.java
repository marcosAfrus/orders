package com.unir.products.data;

import com.unir.products.data.utils.SearchCriteria;
import com.unir.products.data.utils.SearchOperation;
import com.unir.products.data.utils.SearchStatement;
import com.unir.products.model.pojo.Order;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final OrderJpaRepository repository;

    public List<Order> getOrders() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Order save(Order product) {
        return repository.save(product);
    }

    public void delete(Order product) {
        repository.delete(product);
    }

    public List<Order> search(String userName, String status) {
        SearchCriteria<Order> spec = new SearchCriteria<>();
        if (StringUtils.isNotBlank(userName)) {
            spec.add(new SearchStatement("userName", userName, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(status)) {
            spec.add(new SearchStatement("status", status, SearchOperation.MATCH));
        }
        return repository.findAll(spec);
    }

}
