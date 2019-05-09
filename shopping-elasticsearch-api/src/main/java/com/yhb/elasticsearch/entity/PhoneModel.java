package com.yhb.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/5/7 15:51
 * @Description:
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "phone")
public class PhoneModel implements Serializable {
    /* ID */
    @Id
    private String id;

    /* 名称 */
    private String name;

    /* 颜色，用英文分号(;)分隔 */
    private String colors;

    /* 卖点，用英文分号(;)分隔 */
    private String sellingPoints;

    /* 价格 */
    private String price;

    /* 产量 */
    private Long yield;

    /* 销售量 */
    private Long sale;

    /* 上市时间 */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date marketTime;

    /* 数据抓取时间 */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
