package cn.com.spring.Dao;

import cn.com.spring.bean.OaNoCode;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface OaNoCodeDao extends Repository<OaNoCode, String> {
    //方法名by---+字段名不然爆出No property id found for type OaNoCode!
    OaNoCode getByCodeid(String codeid);

    List<OaNoCode> findAll();
}
