package sg.delivr.ui.models;

public class SearchDatas {

    public SearchDatas(){}

    String loc_id;
    String loc_name;
    String loc_date;

    public SearchDatas(String loc_id, String loc_name, String loc_date) {
        this.loc_id = loc_id;
        this.loc_name = loc_name;
        this.loc_date = loc_date;
    }

    public String getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(String loc_id) {
        this.loc_id = loc_id;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public String getLoc_date() {
        return loc_date;
    }

    public void setLoc_date(String loc_date) {
        this.loc_date = loc_date;
    }



}
