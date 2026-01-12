package com.lhb.product.service;


import com.lhb.product.pojo.DO.Product;
import com.lhb.product.pojo.DTO.PageResult;

public interface ProductService {

    PageResult<Product> page(Integer page, Integer size, Long lastId);

    Product create(String name);

    Product update(Long id, String name);

    void delete(Long id);
}
