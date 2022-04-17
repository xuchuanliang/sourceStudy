package zhx;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.BufferedReader;
import java.io.FileReader;

public class Read {
    public static void main(String[] args) {
        read();
    }

    public static void read() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\xuchu\\Desktop\\data5.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            final String fullText = stringBuilder.toString();
            final JSONObject jsonObject = JSONUtil.parseObj(fullText);
            final JSONObject returnValue = JSONUtil.parseObj(jsonObject.get("ReturnValue"));
            final JSONArray datas = returnValue.getJSONArray("datas");
            //so_id  order_date  shop_name
            datas.forEach(d->{
                JSONObject j = ((JSONObject)d);
                System.out.print(j.get("so_id")+","+j.get("order_date")+","+j.get("shop_name"));
                System.out.println("");
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
