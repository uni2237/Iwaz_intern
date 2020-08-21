import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class remake_json {

   public static void main(String[] args) throws IOException {

      String[] a = new String[10000];
      int i = 0;
      File file = new File(
            "C:\\Users\\a0102\\Desktop\\test.json");
      if (file.exists()) {
         BufferedReader inFile = new BufferedReader(new FileReader(file));
         String sLine = null;
         while ((sLine = inFile.readLine()) != null) {
            a[i] = sLine;
            i++;
         }

         a[0] = '[' + a[0];
         a[i - 1] = a[i - 1] + ']';
         for (int m = 0; m < i; m++) {
            // System.out.println(a[j]);
            if (a[m] != null && a[m + 1] != null && a[m].equals("}") && a[m + 1].equals("{")) {
               a[m] = a[m] + ',';
            }

         } 
      }
      try (FileWriter fout = new FileWriter("c:\\csvdata\\2020-01-01.json")) {
         PrintWriter out = new PrintWriter(fout);
         for (String line : a) {
            if (line != null)
               out.println(line);
         }
      }
   }
}
