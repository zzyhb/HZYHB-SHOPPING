package com.yhb.elasticsearch.jpa;

import com.yhb.elasticsearch.entity.Goods;
import com.yhb.elasticsearch.entity.HuaWeiPhoneBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/5/7 10:19
 * @Description:
 */
public interface PhoneRepository extends ElasticsearchCrudRepository<HuaWeiPhoneBean,String> {
}
