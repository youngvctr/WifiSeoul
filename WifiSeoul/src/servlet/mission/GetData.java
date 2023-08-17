package servlet.mission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetData {
    private static final double EARTH_RADIUS = 6371.01;
    public int id;
    private String mgr_no, wrdofc, main_nm, address1, address2, instl_floor, instl_ty, instl_mby, svc_se, cmcwr, cnstc_year, in_out, remars3, work_dttm;
    private String req_datetime;
    private double lat, lng, dist;

    public GetData(int id, double lat, double lng, String req_datetime) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.req_datetime = req_datetime;
    }

    public GetData(int id, String mgr_no, String wrdofc, String main_nm, String address1, String address2, String instl_floor, String instl_ty, String instl_mby, String svc_se, String cmcwr, String cnstc_year, String in_out, String remars3, double lat, double lng, String work_dttm) {
        this.id = id;
        this.mgr_no = mgr_no;
        this.wrdofc = wrdofc;
        this.main_nm = main_nm;
        this.address1 = address1;
        this.address2 = address2;
        this.instl_floor = instl_floor;
        this.instl_ty = instl_ty;
        this.instl_mby = instl_mby;
        this.svc_se = svc_se;
        this.cmcwr = cmcwr;
        this.cnstc_year = cnstc_year;
        this.in_out = in_out;
        this.remars3 = remars3;
        this.lat = lat;
        this.lng = lng;
        this.work_dttm = work_dttm;
    }

    /**
     * detail.jsp
     */
    public String getStringDist() {
        return String.format("%.4f", this.dist);
    }

    public double setDistance(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c; // km
    }
}