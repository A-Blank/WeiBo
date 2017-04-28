package com.weibo.Utils;

/**
 * Created by 丶 on 2017/3/19.
 */

public class RequestUrls {

    private final static String TrendingTop_URL = "http://api.weibo.cn/2/cardlist?" +
            "c=android&containerid=106003type%3D25%26t%3D3%26disable_hot%3D1%26filter_type%3Drealtimehot" +
            "&gsid=_2A2518LAwDeRxGeRM7lER8SbEyz6IHXVU3EA3rDV6PUJbkdANLW3kkWoqtizRzUEQKnXdoyxg3zFHq12qHg..&s=d8dd255e";

    private final static String Trending_URL = "https://api.weibo.cn/2/guest/cardlist?" +
            "c=android&s=2113eefd&gsid=_2AkMvp9uzf8NhqwJRmP8TzGziaIh1zQ_EieKZ-ypoJRM3HRl-3D9jqksFtRUA_66dOsHRmaYELckzGscn77lTuA..&containerid=230584";

    private final static String Star_URL = "https://api.weibo.cn/2/guest/cardlist?" +
            "c=android&s=2113eefd&gsid=_2AkMvp9uzf8NhqwJRmP8TzGziaIh1zQ_EieKZ-ypoJRM3HRl-3D9jqksFtRUA_66dOsHRmaYELckzGscn77lTuA..&containerid=230781&count=10";

    private final static String Hot_Topic_URL = "http://api.weibo.cn/2/guest/cardlist?" +
            "c=android&i=a76efdd&s=9ad5aa04&gsid=_2AkMviY1ef8NhqwJRmP0TyWPiaoV-zQnEieKZ1XyFJRM3HRl-3T9kqmdYtRVkZs8Qlb_TC3Fpsqgy8jGAKrsRwQ..&containerid=230584";

    private final static String HotWords_Url = "https://api.weibo.cn/2/guest/cardlist?" +
            "uicode=10000228&c=android&s=2113eefd&gsid=_2AkMvp9uzf8NhqwJRmP8TzGziaIh1zQ_EieKZ-ypoJRM3HRl-3D9jqksFtRUA_66dOsHRmaYELckzGscn77lTuA..&containerid=106003type%3D1";

    private final static String Comment_Url = "https://api.weibo.cn/2/comments/build_comments?" +
            "max_id=0&is_show_bulletin=2&uicode=10000002&moduleID=700&checktoken=0dd2cecad5e9f03f5bba4e22510a46b8&trim_user=0&is_reload=1&is_encoded=0&c=android&i=af2241a&s=2113eefd&wm=90061_90008&ext=rid%3A1_0_0_2633269926947401458&v_f=2&v_p=45&from=1073195010&gsid=_2AkMuXjisf8NhqwJRmP8TzGziaIh1zQ_EieKYAsl3JRM3HRl-3T9jqlQftRW-i8hCuykkxesckYGP_QsR5VVMjw..";

    public static String getTrendingTop_URL() {
        return TrendingTop_URL;
    }

    public static String getHot_Topic_URL() {
        return Hot_Topic_URL;
    }

    public static String getTrending_URL() {
        return Trending_URL;
    }

    public static String getStar_URL() {
        return Star_URL;
    }

    public static String getHotWords_URL() {
        return HotWords_Url;
    }

    public static String getComment_Url() {
        return Comment_Url;
    }

}
