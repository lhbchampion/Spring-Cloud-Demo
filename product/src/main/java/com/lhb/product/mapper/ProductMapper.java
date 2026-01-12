package com.lhb.product.mapper;


import com.lhb.product.pojo.DO.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    /**
     * 总数统计（offset 分页用）
     */
    @Select("SELECT COUNT(*) FROM product")
    long count();

    /**
     * offset 分页
     */
    @Select("""
        SELECT id, name
        FROM product
        LIMIT #{limit} OFFSET #{offset}
    """)
    List<Product> findPage(@Param("offset") int offset,
                           @Param("limit") int limit);

    /**
     * 游标分页（防止深分页）
     */
    @Select("""
        SELECT id, name
        FROM product
        WHERE id < #{lastId}
        LIMIT #{size}
    """)
    List<Product> findByLastId(@Param("lastId") Long lastId,
                                @Param("size") int size);

    @Insert("INSERT INTO product(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    @Select("SELECT id, name FROM product WHERE id = #{id}")
    Product findById(Long id);

    @Update("UPDATE product SET name = #{name} WHERE id = #{id}")
    int update(Product product);

    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteById(Long id);
}
