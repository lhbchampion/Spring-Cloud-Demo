package com.lhb.product.controller;


import com.lhb.base.response.ApiResponse;
import com.lhb.product.pojo.DO.Product;
import com.lhb.product.pojo.DTO.PageResult;
import com.lhb.product.pojo.DTO.ProductRequest;
import com.lhb.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }
    /**
     * 分页查询商品
     * USER 及以上角色
     */
    @GetMapping
    public ApiResponse<PageResult<Product>> page(
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long lastId) {

        return ApiResponse.success(service.page(page, size, lastId));
    }

    /**
     * 新增商品
     * EDITOR 及以上角色
     */
    @PostMapping
    public ApiResponse<Product> create(
            @RequestBody  ProductRequest request) {

        return ApiResponse.success(service.create(request.getName()));
    }


    /**
     * 修改商品
     * EDITOR 及以上角色
     */
    @PutMapping("/{id}")
    public ApiResponse<Product> update(
            @PathVariable Long id,
            @RequestBody  ProductRequest request) {

        return ApiResponse.success(service.update(id, request.getName()));
    }


    /**
     * 删除商品
     * EDITOR 及以上角色
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success(null, "deleted");
    }
}
