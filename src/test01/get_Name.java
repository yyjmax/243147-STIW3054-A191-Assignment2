package src.test01;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
