package com.yhb.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhb.common.result.BaseResult;
import com.yhb.elasticsearch.entity.Goods;
import com.yhb.elasticsearch.entity.HuaWeiPhoneBean;
import com.yhb.elasticsearch.entity.PhoneModel;
import com.yhb.elasticsearch.jpa.GoodsRepository;
import com.yhb.elasticsearch.jpa.PhoneRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/5/7 10:36
 * @Description:
 */
@RestController
@RequestMapping("goods")
@CrossOrigin
public class GoodsController {
    /**
     * 对ES进行简单的增删改查
     */
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    PhoneRepository phoneRepository;
    /**
     * 对ES进行复杂的增删改查
     */
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("/add")
    public BaseResult add() {
        Goods goods = Goods.builder().id(1L).createTime(new Date()).
                goodsName("一叶一菩提项链").goodsTitle("手工首饰系列").
                goodsDetail("河南是禅宗文化发祥地，以禅宗美学为理念手工制作的首饰系列，意欲阐释一份简约，空灵与轻巧。").build();
        Goods result = goodsRepository.save(goods);
        return BaseResult.setSuccessResult(result);
    }

    @GetMapping("/all")
    public BaseResult getAllGoods() {
        Iterable<Goods> iterable = goodsRepository.findAll();
        List<Goods> list = new ArrayList<>();
        iterable.forEach(list::add);
        return BaseResult.setSuccessResult(list);
    }
    @GetMapping("/getPhonesList")
    public BaseResult getPhonesList() {
        Iterable<HuaWeiPhoneBean> iterable = phoneRepository.findAll();
        List<HuaWeiPhoneBean> list = new ArrayList<>();
        iterable.forEach(list::add);
        return BaseResult.setSuccessResultSize(list,list.size());
    }

    @GetMapping("/deleteAll")
    public BaseResult deleteAll() {
        goodsRepository.deleteAll();
        return BaseResult.setSuccessMessage("删除成功");
    }

    @GetMapping("/findByKeyword")
    public BaseResult findByKeyWord(@RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "size", required = false) Integer size) {
        if (StringUtils.isEmpty(keyword)) {
            return BaseResult.setErrorResult(-1, "请输入关键字");
        } else {
            // 校验参数
            if (null == page) {
                page = 0; // if page is null, page = 0
            }
            if (null == size) {
                size = 10; // if size is null, size default 10
            }
            // 构造分页类
            Pageable pageable = PageRequest.of(page, size);
            // 构造查询 NativeSearchQueryBuilder
            NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                    .withPageable(pageable);
            //设置高亮
            withHighlight(searchQueryBuilder);
            // keyword must not null
            searchQueryBuilder.withQuery(QueryBuilders.matchQuery("productTitle",keyword));
            /*
            SearchQuery
            这个很关键，这是搜索条件的入口，
            elasticsearchTemplate 会 使用它 进行搜索
             */
            SearchQuery searchQuery = searchQueryBuilder.build();
            // page search
            Page<HuaWeiPhoneBean> phonesPage = elasticsearchTemplate.queryForPage(searchQuery, HuaWeiPhoneBean.class);
            return BaseResult.setSuccessResult(phonesPage);
        }
    }

    /**
     * 添加高亮条件
     * @param searchQuery
     */
    private void withHighlight(NativeSearchQueryBuilder searchQuery){
        HighlightBuilder.Field hfield= new HighlightBuilder.Field("content")
                .preTags("<em style='color:red'>")
                .postTags("</em>")
                .fragmentSize(100);
        searchQuery.withHighlightFields(hfield);
    }

    @GetMapping(value = "/spider.do")
    public List<HuaWeiPhoneBean> spider() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault(); // 创建httpclient实例
        //HttpGet httpget = new HttpGet("https://consumer.huawei.com/cn/phones/?ic_medium=hwdc&ic_source=corp_header_consumer"); // 创建httpget实例
        HttpGet httpget = new HttpGet("https://consumer.huawei.com/cn/tablets/");

        CloseableHttpResponse response = httpclient.execute(httpget); // 执行get请求
        HttpEntity entity=response.getEntity(); // 获取返回实体
        String content = EntityUtils.toString(entity, "utf-8");
        response.close(); // 关闭流和释放系统资源

        Document document = Jsoup.parse(content);
        Elements elements = document.select("#content-v3-plp #pagehidedata .plphidedata");
        List<HuaWeiPhoneBean> result = new ArrayList<>();
        for (Element element : elements) {
            String jsonStr = element.text();
            List<HuaWeiPhoneBean> list = JSON.parseArray(jsonStr, HuaWeiPhoneBean.class);
            for (HuaWeiPhoneBean bean : list) {
                result.add(bean);
            }
        }
        phoneRepository.saveAll(result);
        return result;
    }
}
