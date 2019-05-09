package com.yhb.elasticsearch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/5/7 17:14
 * @Description:
 */
@NoArgsConstructor
@Data
@Document(indexName = "phone")
public class HuaWeiPhoneBean {

    /**
     * productTitle : p30-pro
     * productName : HUAWEI P30 Pro
     * marketingName : HUAWEI P30 Pro
     * colorsItemMode : [{"colorName":"天空之境","colorValue":"#bdf1ff","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/list-image/phones/p30-pro/p30-pro-skyblue.png"},{"colorName":"赤茶橘","colorValue":"#ff4c28","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/list-image/phones/p30-pro/p30-pro-orange.png"},{"colorName":"极光色","colorValue":"#0d9ead","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/list-image/phones/p30-pro/p30-pro-twilight.png"},{"colorName":"亮黑色","colorValue":"#303030","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/list-image/phones/p30-pro/p30-pro-black.png"},{"colorName":"珠光贝母","colorValue":"#f2eee4","img":"/content/dam/huawei-cbg-site/greate-china/cn/mkt/list-image/phones/p30-pro/p30-pro-white.png"}]
     * sellingPoints : ["超感光徕卡四摄，潜望式 10 倍混合变焦","6.47 英寸 OLED 曲面屏，磁悬屏幕发声","4200mAh 大电池，华为 40W 超级快充"]
     * productId : 18542
     * adminBuyButtonMode : {"buttonType":"third-party-site","buyButtonText":"购买","openInNewPage":"true","thirdPartySiteLink":"https://www.vmall.com/product/10086501288078.html?cid=72163","mobileThirdPartySiteLink":"https://www.vmall.com/product/10086501288078.html?cid=72163","partnerCountryFilterEffective":"false","chooseAPartners":[]}
     * priceDisplayType : 1
     * arrFilter : ["series%@%p-series"]
     * canBuy : no
     * detailLink : /cn/phones/p30-pro.html
     * whetherNewType :
     */
    @Id
    private String productId;
    private String productTitle;
    private String productName;
    private String marketingName;
    private AdminBuyButtonMode adminBuyButtonMode;
    private String priceDisplayType;
    private String canBuy;
    private String detailLink;
    private String whetherNewType;
    private List<ColorsItemMode> colorsItemMode;
    private List<String> sellingPoints;
    private List<String> arrFilter;

    @NoArgsConstructor
    @Data
    public static class AdminBuyButtonMode {
        /**
         * buttonType : third-party-site
         * buyButtonText : 购买
         * openInNewPage : true
         * thirdPartySiteLink : https://www.vmall.com/product/10086501288078.html?cid=72163
         * mobileThirdPartySiteLink : https://www.vmall.com/product/10086501288078.html?cid=72163
         * partnerCountryFilterEffective : false
         * chooseAPartners : []
         */

        private String buttonType;
        private String buyButtonText;
        private String openInNewPage;
        private String thirdPartySiteLink;
        private String mobileThirdPartySiteLink;
        private String partnerCountryFilterEffective;
        private List<?> chooseAPartners;
    }

    @NoArgsConstructor
    @Data
    public static class ColorsItemMode {
        /**
         * colorName : 天空之境
         * colorValue : #bdf1ff
         * img : /content/dam/huawei-cbg-site/greate-china/cn/mkt/list-image/phones/p30-pro/p30-pro-skyblue.png
         */

        private String colorName;
        private String colorValue;
        private String img;
    }
}
