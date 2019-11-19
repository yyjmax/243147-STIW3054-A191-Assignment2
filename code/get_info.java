package src.test01;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class get_Name {
    String url, divContent;

    public get_Name(String url, String divContent) {
        this.url = url;
        this.divContent = divContent;
    }

    public List<String> getNameByJsoup() {
        Document doc = Jsoup.parse(divContent, url);
        List<String> nameList = new ArrayList<String>();
        Elements linkStrs = doc.getElementsByClass("position-relative");
        linkStrs.toString();
        for (Element linkStr : linkStrs) {
            List<String> url = linkStr.getElementsByClass("link-gray pl-1").eachText();
            for (int i = 0; i < url.size(); i++) {
                String name = url.get(i);
                nameList.add(name);
            }
        }
        return nameList;
    }
}

public class get_link {
    String name;

    public get_link(String name) {
        this.name = name;
    }

    public String getLinksByJsoup() {
        String full_link;
        String link = "https://github.com/{}?&tab=followers";
        full_link = link.replace("{}", name);
        return full_link;
    }
}





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

