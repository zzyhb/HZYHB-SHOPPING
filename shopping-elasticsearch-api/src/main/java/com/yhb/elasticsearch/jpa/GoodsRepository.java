package com.yhb.elasticsearch.jpa;

import com.yhb.elasticsearch.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/5/7 10:19
 * @Description:
 */
public interface GoodsRepository extends ElasticsearchCrudRepository<Goods,Long> {
    /**
     * 全文关键字搜索
     * @param keyword
     * @return
     */
    List<Goods> findByGoodsNameLike(String keyword);
}
