package src.test01;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class crawler { //This class to get each follower's information include the number of their repositories,follower,following and star.
    String url,divContent,name;
    public crawler(String url,String divContent){
        this.url = url;
        this.divContent = divContent;
    }

    public String getRepositoriesByJsoup(){
        Document doc = Jsoup.parse(divContent,url);
        String repostoriesNum = "";
        Elements linkStrs = doc.getElementsByClass("UnderlineNav width-full user-profile-nav js-sticky top-0");
        for(Element linkStr:linkStrs){
            List<String> repositories=linkStr.getElementsByClass("Counter hide-lg hide-md hide-sm").eachText();
            repostoriesNum = repositories.get(0);
        }

        return repostoriesNum;
    }
    public String getFollowerNumByJsoup(){
        Document doc = Jsoup.parse(divContent,url);
        String followerNum = "";
        Elements linkStrs = doc.getElementsByClass("UnderlineNav width-full user-profile-nav js-sticky top-0");
        for(Element linkStr:linkStrs){
            List<String> follower=linkStr.getElementsByClass("Counter hide-lg hide-md hide-sm").eachText();
            followerNum = follower.get(3);
        }

        return followerNum;

    }
    public String getFollowingNumByJsoup(){
        Document doc = Jsoup.parse(divContent,url);
        String followingNum = "";
        Elements linkStrs = doc.getElementsByClass("UnderlineNav width-full user-profile-nav js-sticky top-0");
        for(Element linkStr:linkStrs){
            List<String> following=linkStr.getElementsByClass("Counter hide-lg hide-md hide-sm").eachText();
            followingNum = following.get(0);
        }

        return followingNum;
    }

    public String getStarByJsoup(){
        Document doc = Jsoup.parse(divContent,url);
        String starNum = "";
        Elements linkStrs = doc.getElementsByClass("UnderlineNav width-full user-profile-nav js-sticky top-0");
        for(Element linkStr:linkStrs){
            List<String> star=linkStr.getElementsByClass("Counter hide-lg hide-md hide-sm").eachText();
            starNum = star.get(2);
        }

        return starNum;
    }
}