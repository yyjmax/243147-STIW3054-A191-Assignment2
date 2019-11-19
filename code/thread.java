package src.test01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Element;



public class thread extends Thread {
    private int i;
    public thread(int i){
        this.i = i;
    }
    public static String getContentByJsoup(String url){
        String content="";
        try {
            Document doc=Jsoup.connect(url)
                    .data("jquery", "java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(4000)
                    .get();
            content=doc.toString();//获取网站的源码html内容   require html content from github
        } catch (IOException e) {
        }
        return content;
    }
    public static String getDivContentByJsoup(String content){
        String divContent;
        Document doc=Jsoup.parse(content);
        Elements divs=doc.getElementsByClass("col-lg-9 col-md-8 col-12 float-md-left pl-md-2");
        divContent=divs.toString();
        return divContent;
    }

    public  List<String> getAllName() throws InterruptedException{
        List<String> allNameList = new ArrayList<String>();
        String url="https://github.com/zhamri?tab=followers";
        String HtmlContent=getContentByJsoup(url);
        String divContent=getDivContentByJsoup(HtmlContent);
        //page1
        String url2 = "https://github.com/zhamri?after=Y3Vyc29yOnYyOpK5MjAxOS0wMi0yMFQxMTo1NDozOCswODowMM4Cfdf_&tab=followers";
        String HtmlContent2=getContentByJsoup(url2);
        String divContent2=getDivContentByJsoup(HtmlContent2);
        //page2
        String url3 = "https://github.com/zhamri?after=Y3Vyc29yOnYyOpK5MjAxOC0wOS0xNFQxMTozNzozMiswODowMM4CPUKe&tab=followers";
        String HtmlContent3=getContentByJsoup(url3);
        String divContent3=getDivContentByJsoup(HtmlContent3);
        //page3
        String url4 = "https://github.com/zhamri?after=Y3Vyc29yOnYyOpK5MjAxNy0wOS0xM1QyMTo1MDoxMCswODowMM4BoGXN&tab=followers";
        String HtmlContent4=getContentByJsoup(url4);
        String divContent4=getDivContentByJsoup(HtmlContent4);
        //page4
        int num1 = getLinksByJsoup(divContent).size();
        int num2 = getLinksByJsoup(divContent2).size();
        int num3 = getLinksByJsoup(divContent3).size();
        int num4 = getLinksByJsoup(divContent4).size();
        List<String> nameList=new ArrayList<String>();
        for(int i = 0;i < num1;i++){
            get_Name gn1 = new get_Name(url,divContent);
            String name1 = gn1.getNameByJsoup().get(i);
            nameList.add(name1);
        }
        for(int i = 0;i < num2;i++){
            get_Name gn2 = new get_Name(url2,divContent2);
            String name2 = gn2.getNameByJsoup().get(i);
            nameList.add(name2);
        }
        for(int i = 0;i < num3;i++){
            get_Name gn3 = new get_Name(url3,divContent3);
            String name3 = gn3.getNameByJsoup().get(i);
            nameList.add(name3);
        }
        for(int i = 0;i < num4;i++){
            get_Name gn4 = new get_Name(url4,divContent4);
            String name4 = gn4.getNameByJsoup().get(i);
            nameList.add(name4);
        }
        allNameList.addAll(nameList);
        return allNameList;
    }
    public static List<String> getLinksByJsoup(String divContent){
        String abs = "https://github.com/zhamri?tab=followers";
        Document doc = Jsoup.parse(divContent,abs);
        List<String> linkList=new ArrayList<String>();
        Elements linkStrs = doc.getElementsByClass("position-relative");
        linkStrs.toString();
        for(Element linkStr:linkStrs){
            List<String> url=linkStr.getElementsByClass("link-gray pl-1").eachText();
            for(int i = 0;i<url.size();i++){
                String name = url.get(i);
                String link = "https://github.com/{}?page={}&tab=followers";
                String full_link=link.replace("{}", name);
                linkList.add(full_link);
            }
        }
        return linkList;
    }
    @Override
    public void run(){
        try {
            List<String> allNameList=new ArrayList<String>();
            allNameList = getAllName();
            get_link gl = new get_link(allNameList.get(i));
            String link = gl.getLinksByJsoup();
            String HtmlContent01 = getContentByJsoup(link);
            String divContent01 = getDivContentByJsoup(HtmlContent01);
            crawler follower = new crawler(link,divContent01);

            String allName = allNameList.get(i);
            String repositoriesNum1 = follower.getRepositoriesByJsoup();
            String followerNum1 = follower.getFollowerNumByJsoup();
            String followingNum1 = follower.getFollowingNumByJsoup();
            String StarNum1 = follower.getStarByJsoup();

            System.out.printf("|%-5s|%-20s|%-30s|%-20s|%-21s|%-16s\n",i+1,allName,
                    repositoriesNum1,
                    followerNum1,
                    followingNum1,
                    StarNum1);
        } catch (InterruptedException ex) {
            Logger.getLogger(thread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
