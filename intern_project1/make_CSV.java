import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class make_CSV { 
   static String[] a = new String[100];

   public static void main(String args[]) {
      BufferedReader bReader = null;
      JSONParser parser = new JSONParser();

      String path_1 = "c:\\csvdata\\";
      File f = new File(path_1);
      if (!f.exists())
         f.mkdirs();
      String createfile = "c:\\csvdata\\csvfile03.csv";
      String strDirPath = "C:\\Users\\a0102\\Desktop\\아이와즈\\0708_JSON데이터변환실습\\2020_ner\\2020\\03\\2020-03-01";

      try {
         FileWriter fw = new FileWriter(createfile);
         fw.append("뉴스순번");
         fw.append(',');
         fw.append("뉴스ES_ID");
         fw.append(',');
         fw.append("뉴스카테고리");
         fw.append(',');
         fw.append("언론사명");
         fw.append(',');
         fw.append("게재일시");
         fw.append(',');
         fw.append("기자명");
         fw.append(',');
         fw.append("문자수");
         fw.append(',');
         fw.append("제목");
         fw.append(',');
         fw.append("기사");
         fw.append(',');
         fw.append("사용여부");
         fw.append('\n');

         File path = new File(strDirPath);
         File[] fList = path.listFiles(); // json 파일 여기에 다있음

         for (int i = 0; i < fList.length; i++) {
            String p = fList[i].getPath(); // 전체줄
            File file = new File(p);

            String s;

            String pattern = "\\[[^\\[]*\\]";

            bReader = new BufferedReader(new FileReader(file));
            while ((s = bReader.readLine()) != null) {
               s = s.replace("{", "[");
               s = s.replace("}", "]");
               // String[] s1 = s.split("{\\n");
               String s1 = s.replaceAll(pattern, "");

               Pattern ptn = Pattern
                     .compile("text[\"]\\s:\\s[\"]([a-zA-Z가-힣\"\\s0-9$&+,‘’:·;=?@#|'<>.-^*()%!]*)[\"]");
               // Pattern ptn =
               // Pattern.compile("([a-zA-Z가-힣\\\\s0-9$&+,:;=?@#|'<>.-^*()%!]*)([\"]\\s[\"]category)");
               // Pattern ptn = Pattern.compile("[a-zA-Z가-힣\\s0-9$&+,:;=?@#|'<>.-^*()%!]*");
               Matcher matcher = ptn.matcher(s1);

               while (matcher.find()) {
                  for (int j = 0; j < 100; j++) {
                     a[j] = matcher.group(1);
                  }
                  System.out.println(a[0]);

                  String pp = p.substring(p.lastIndexOf('\\')); // 끝줄만 생각

                  fw.append("" + (i + 1)); // 반복문
                  fw.append(',');
                  fw.append(pp.substring(15, pp.lastIndexOf('.')));
                  fw.append(',');
                  fw.append(pp.substring(12, 14));
                  fw.append(',');
                  fw.append(null);
                  fw.append(',');
                  fw.append(pp.substring(1, 11));
                  fw.append(',');
                  fw.append(null);
                  fw.append(',');
                  fw.append("" + (a[0].length())); // 아직
                  fw.append(',');
                  fw.append(null);
                  fw.append(',');
                  fw.append(a[0]); // 아직
                  fw.append(',');
                  fw.append("Y");
                  fw.append('\n');
               }

               // System.out.println(s1);
            }
         } 

         fw.flush();
         fw.close();
      } catch (IOException e) {
         // TODO 자동 생성된 catch 블록
         e.printStackTrace();
      }

   }

}
