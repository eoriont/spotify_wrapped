
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class webBridge {
    public static String call(String[] tracks) {
        try {
            String baseUrl = "https://colbyb1123.pythonanywhere.com/";
            //Pass in list of top listened to songs
            //This example works for now

            List<String> dataList = new ArrayList<>();
            for (String s : tracks) {
                dataList.add(s);
            }

            String queryString = dataList.stream()
                    .map(s -> "data=" + s)
                    .collect(Collectors.joining("&"));
            String url = baseUrl + "?" + queryString;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String result = response.toString();
            System.out.println(result);
            result = result.substring(0,3);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String[] tracks = {"Baby", "Hello"};
        System.out.println(call(tracks));
    }
}