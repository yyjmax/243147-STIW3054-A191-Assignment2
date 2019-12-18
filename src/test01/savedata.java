package src.test01;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


//This class is used for save each follower of teacher information
//Since I saved the data into excel without using thread, hence many code is repeatedly written into this class.
public class savedata {
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


    public static void SaveExcel() throws IOException{


        get_Content gc = new get_Content();
        String url1 = "https://github.com/zhamri?tab=followers"; //page1
        String HtmlContent1 = gc.getContentByJsoup(url1);
        String divContent1 = gc.getDivContentByJsoup(HtmlContent1);

        String url2 = "https://github.com/zhamri?after=Y3Vyc29yOnYyOpK5MjAxOS0wMi0yMFQxMTo1NDozOCswODowMM4Cfdf_&tab=followers";//page2
        String HtmlContent2 = gc.getContentByJsoup(url2);
        String divContent2 = gc.getDivContentByJsoup(HtmlContent2);

        String url3 = "https://github.com/zhamri?after=Y3Vyc29yOnYyOpK5MjAxOC0wOS0xNFQxMTozNzozMiswODowMM4CPUKe&tab=followers";//page3
        String HtmlContent3 = gc.getContentByJsoup(url3);
        String divContent3 = gc.getDivContentByJsoup(HtmlContent3);

        String url4 = "https://github.com/zhamri?after=Y3Vyc29yOnYyOpK5MjAxNy0wOS0xM1QyMTo1MDoxMCswODowMM4BoGXN&tab=followers";//page4
        String HtmlContent4 = gc.getContentByJsoup(url4);
        String divContent4 = gc.getDivContentByJsoup(HtmlContent4);

        int num1 = getLinksByJsoup(divContent1).size();
        int num2 = getLinksByJsoup(divContent2).size();
        int num3 = getLinksByJsoup(divContent3).size();
        int num4 = getLinksByJsoup(divContent4).size();
        System.out.println(num1);
        List<String> nameList=new ArrayList<String>();
        List<String> allNameList=new ArrayList<String>();
        List<String> reposList=new ArrayList<String>();
        List<String> followerList=new ArrayList<String>();
        List<String> followingList=new ArrayList<String>();
        List<String> starList=new ArrayList<String>();
        for(int i = 0;i < num1;i++){
            get_Name gn1 = new get_Name(url1,divContent1);
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
        for(int i =0;i<nameList.size();i++){
            get_link gl1 = new get_link(allNameList.get(i));
            String link1 = gl1.getLinksByJsoup();
            String HtmlContent01 = gc.getContentByJsoup(link1);
            String divContent01 = gc.getDivContentByJsoup(HtmlContent01);
            crawler follower = new crawler(link1,divContent01);

            String repositoriesNum = follower.getRepositoriesByJsoup();
            reposList.add(repositoriesNum);
            String followerNum = follower.getFollowerNumByJsoup();
            followerList.add(followerNum);
            String followingNum = follower.getFollowingNumByJsoup();
            followingList.add(followingNum);
            String StarNum = follower.getStarByJsoup();
            starList.add(StarNum);
        }
//                System.out.println("name==="+matricNums.size());
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("行业统计");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("No");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("login id");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("Number of respositories");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("Number of Follower");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("Number of Following");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("Number of Star");
        cell.setCellStyle(style);
        for (int i = 0; i < allNameList.size(); i++)
        {
            row = sheet.createRow((int) i + 1);
            // 第四步，创建单元格，并设置值
            String id = Integer.toString(i+1);
            row.createCell((short) 0).setCellValue(new HSSFRichTextString(id));
            row.createCell((short) 1).setCellValue(new HSSFRichTextString(nameList.get(i)));
            row.createCell((short) 2).setCellValue(new HSSFRichTextString(reposList.get(i)));
            row.createCell((short) 3).setCellValue(new HSSFRichTextString(followerList.get(i)));
            row.createCell((short) 4).setCellValue(new HSSFRichTextString(followingList.get(i)));
            row.createCell((short) 5).setCellValue(new HSSFRichTextString(starList.get(i)));
        }
        FileOutputStream fout = new FileOutputStream("D:/information.xls");
        wb.write(fout);
        fout.close();
        System.out.println("The MS Excel file have succesfully created in D:/information.xls");
    }
    public static void main(String[] args) throws IOException{
        SaveExcel();
    }
}
