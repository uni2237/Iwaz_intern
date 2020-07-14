import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class news_num {
   public static void main(String[] args) {
      JSONParser jsonParser = new JSONParser();

      try {
         JSONArray a = (JSONArray) jsonParser.parse(new FileReader("c:\\\\csvdata\\\\2020-01-01.json"));
         for (Object o : a) {
            JSONObject news = (JSONObject) o;
            JSONArray sentence = (JSONArray) news.get("sentence");
            for (Object s : sentence) {
               for (Object S : sentence) {
                  JSONObject t = (JSONObject) S;
                  String text = (String) t.get("text");
                  System.out.println(text);
               }

            }
         }

      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }
} 
