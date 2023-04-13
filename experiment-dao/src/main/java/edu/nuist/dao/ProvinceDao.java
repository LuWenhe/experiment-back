package edu.nuist.dao;

import edu.nuist.entity.Province;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProvinceDao {

    @Select("SELECT id, name FROM province")
    List<Province> getAllProvinces();

}
