package src.test01;

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

