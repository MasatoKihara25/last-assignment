package com.example.lures;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LureMapper {

    @Select("SELECT * FROM lures")
    List<Lure> findAll();

    @Select("SELECT * FROM lures WHERE product LIKE CONCAT('%',#{keyword},'%')")
    List<Lure> findLures(String keyword);

    @Select("SELECT * FROM lures WHERE id = #{id}")
    Optional<Lure> findById(Integer id);

}
