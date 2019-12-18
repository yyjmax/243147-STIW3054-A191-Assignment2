package src.test01;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

class get_Content {
    public String getContentByJsoup(String url) {
        String content = "";
        try {
            Document doc = Jsoup.connect(url)
                    .data("jquery", "java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(50000)
                    .get();
            content = doc.toString();//获取网站的源码html内容   require html content from github
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String getDivContentByJsoup(String content) {
        String divContent = "";
        Document doc = Jsoup.parse(content);
        Elements divs = doc.getElementsByClass("col-lg-9 col-md-8 col-12 float-md-left pl-md-2");
        divContent = divs.toString();
        return divContent;
    }
}