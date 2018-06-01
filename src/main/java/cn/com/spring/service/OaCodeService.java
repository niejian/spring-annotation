package cn.com.spring.service;

import cn.com.spring.Dao.OaNoCodeDao;
import cn.com.spring.bean.OaNoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "oaCodeService")
public class OaCodeService {
    @Autowired
    private OaNoCodeDao oaNoCodeDao;
   public OaNoCode getOaCodeById(String id){
        return this.oaNoCodeDao.getByCodeid(id);
    }
}
