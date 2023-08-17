package servlet.mission;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtil {
    private static final String baseUrl = "http://openapi.seoul.go.kr:8088/" + "676c786c69686f6f35326573636e6d" + "/json/TbPublicWifiInfo/";
    private static Gson gson = new Gson();

    protected static String apiDataReader(int startIdx, int endIdx) throws IOException {
        URL url = new URL(baseUrl + startIdx + "/" + endIdx);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader br = null;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        }  else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();
        conn.disconnect();
        return new String(sb);
    }

    protected static int getDataCount(String singleEntryResponse) {
        return gson.fromJson(singleEntryResponse, JsonObject.class)
                .getAsJsonObject().get("TbPublicWifiInfo")
                .getAsJsonObject().get("list_total_count").getAsInt();
    }

    protected static JsonArray getDataRows(String response) {
        return gson.fromJson(response, JsonObject.class)
                .getAsJsonObject().get("TbPublicWifiInfo")
                .getAsJsonObject().get("row").getAsJsonArray();
    }

    protected static GetData[] getAllWifiSpotsInSeoul() throws IOException {
        int count = getDataCount(apiDataReader(1, 1));
        GetData[] wifiSpots = new GetData[count];

        for (int i = 0; i <= count / 1000; i++) {
            int limit = (i + 1) * 1000;
            if (i == count / 1000) {
                limit = i * 1000 + count % 1000;
            }

            int j = i * 1000;
            for (JsonElement row : getDataRows(apiDataReader(i * 1000 + 1, limit))) {
                wifiSpots[j++] = getWifiSpots(j, row.getAsJsonObject());
            }
        }
        return wifiSpots;
    }

    private static GetData getWifiSpots(int id, JsonObject o) {
        return new GetData(id,
                o.get("X_SWIFI_MGR_NO").getAsString(),
                o.get("X_SWIFI_WRDOFC").getAsString(),
                o.get("X_SWIFI_MAIN_NM").getAsString(),
                o.get("X_SWIFI_ADRES1").getAsString(),
                o.get("X_SWIFI_ADRES2").getAsString(),
                o.get("X_SWIFI_INSTL_FLOOR").getAsString(),
                o.get("X_SWIFI_INSTL_TY").getAsString(),
                o.get("X_SWIFI_INSTL_MBY").getAsString(),
                o.get("X_SWIFI_SVC_SE").getAsString(),
                o.get("X_SWIFI_CMCWR").getAsString(),
                o.get("X_SWIFI_CNSTC_YEAR").getAsString(),
                o.get("X_SWIFI_INOUT_DOOR").getAsString(),
                o.get("X_SWIFI_REMARS3").getAsString(),
                o.get("LAT").getAsDouble(),  //x latitude
                o.get("LNT").getAsDouble(), //y longitude
                o.get("WORK_DTTM").getAsString());
    }
}
