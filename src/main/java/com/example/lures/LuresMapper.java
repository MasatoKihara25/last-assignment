package com.example.lures;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LuresMapper {

    @Select("SELECT * FROM lures")
    List<Lures> findAll();

    @Select("SELECT * FROM lures WHERE product LIKE CONCAT('%',#{keyword},'%')")
    List<Lures> findLures(String keyword);


}
