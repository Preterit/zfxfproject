package com.zfxf.douniu_network.entry;

import java.util.List;

/**
 * @author :  lwb
 * Date: 2020/6/9
 * Desc:
 */
public class ChartInfoBean extends BaseInfoOfResult {

    /**
     * appOnlineCountList : [{"abscissa":"周一","value":"2"},{"abscissa":"周二","value":"1"}]
     * appOnlineMoneyList : [{"abscissa":"周一","value":"2.00"},{"abscissa":"周二","value":"10.00"}]
     * businessCode : 10
     * businessMessage :
     * vipCountList : []
     * vipMoneyList : []
     */
    public List<ChartValueBean> appOnlineCountList;
    public List<ChartValueBean> appOnlineMoneyList;
    public List<ChartValueBean> vipCountList;
    public List<ChartValueBean> vipMoneyList;

    public static class ChartValueBean {
        /**
         * abscissa : 周一
         * value : 2
         */
        public String abscissa;
        public String value;
    }

}
