package com.study.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 页面url处理
 *
 * @author lhy
 * @version 1.0 2020/4/5
 */
public class MyPageProcessor implements PageProcessor {

  public void process(Page page) {
    page.putField("url", page.getUrl().get());
    page.putField("name", page.getHtml().xpath("//title/text()").get());

    // 导航
    List<Selectable> navigations = page.getHtml().xpath("//div[@id='page_left']/div[1]//a").nodes();
    List<Map<String, String>> navigationMaps = new ArrayList<>();
    for (Selectable navigation : navigations) {
      Map<String, String> stringStringMap = new HashMap<>();
      stringStringMap.put("name", navigation.xpath("//a/text()").get());
      stringStringMap.put("url", navigation.xpath("//a/@href").toString());
      navigationMaps.add(stringStringMap);
    }
    page.putField("navigation", navigationMaps);
    if (navigationMaps.size() > 0) {
      page.putField("city", navigationMaps.get(navigationMaps.size() - 1).get("name"));
    }
    page.putField("content", page.getHtml().toString());

    List<String> urls = page.getHtml().links().all();
    page.addTargetRequests(
        urls.stream().filter(item -> item.matches("^http://www.tcmap.com.cn/fujian/.+"))
            .collect(Collectors.toList()));
  }

  public Site getSite() {
    return Site.me().setRetryTimes(3).setSleepTime(100);
  }
}
