package com.lhb.product.service.impl;


import com.lhb.product.config.BaseContext;
import com.lhb.product.mapper.ProductMapper;
import com.lhb.product.pojo.DO.Product;
import com.lhb.product.pojo.DTO.PageResult;
import com.lhb.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final int MAX_OFFSET_PAGE = 100;

    private final ProductMapper mapper;

    public ProductServiceImpl(ProductMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PageResult<Product> page(Integer page, Integer size, Long lastId) {

        // 任何登录用户都可以访问 COMMON 角色即可
        checkRole("COMMON");

        if (size == null || size < 1) {
            size = 10;
        }

        // 1️⃣ 游标分页（优先）
        if (lastId != null) {
            List<Product> records = mapper.findByLastId(lastId, size);
            return new PageResult<>(-1, -1, size, records);
        }

        // 2️⃣ 防止深分页
        if (page == null || page < 1) {
            page = 1;
        }
        if (page > MAX_OFFSET_PAGE) {
            throw new IllegalArgumentException(
                    "page too large, please use lastId for pagination"
            );
        }

        int offset = (page - 1) * size;
        long total = mapper.count();
        List<Product> records = mapper.findPage(offset, size);

        return new PageResult<>(total, page, size, records);
    }

    @Override
    public Product create(String name) {
        // 只有 EDITOR 及以上角色可以新增
        checkRole("EDITOR");

        Product product = new Product();
        product.setName(name);
        mapper.insert(product);
        return product;
    }

    @Override
    public Product update(Long id, String name) {
        // 只有 EDITOR 及以上角色可以修改
        checkRole("EDITOR");

        Product product = mapper.findById(id);
        if (product == null) {
            throw new IllegalArgumentException("product not found");
        }
        product.setName(name);
        mapper.update(product);
        return product;
    }

    @Override
    public void delete(Long id) {
        // 只有 EDITOR 及以上角色可以删除
        checkRole("EDITOR");

        mapper.deleteById(id);
    }

    /**
     * 权限检查方法
     * @param role 必须拥有的角色名，例如 COMMON, EDITOR
     */
    private void checkRole(String role) {
        List<String> roles = BaseContext.getRoles();
        if (roles == null || !roles.contains(role)) {
            throw new SecurityException(
                    "User does not have required role: " + role
            );
        }
    }
}
