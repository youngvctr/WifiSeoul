package servlet.mission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetBookmark {
    public int id, bookmark_order;
    public String bookmark_name, main_nm, register_date, update_date;

    public GetBookmark(int id, String bookmark_name, String main_nm, int bookmark_order, String register_date, String update_date) {
        this.id = id;
        this.bookmark_name = bookmark_name;
        this.main_nm = main_nm;
        this.bookmark_order = bookmark_order;
        this.register_date = register_date;
        this.update_date = update_date;
    }

    public GetBookmark(String bookmark_name, int bookmark_order) {
        this.bookmark_name = bookmark_name;
        this.bookmark_order = bookmark_order;
    }

    public GetBookmark(int id, int bookmark_order, String bookmark_name) {
        this.id = id;
        this.bookmark_order = bookmark_order;
        this.bookmark_name = bookmark_name;
    }
}