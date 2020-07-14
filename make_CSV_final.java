import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class make_CSV_final {
	public static void main(String args[]) throws ParseException {

		String path_1 = "c:\\csvdata\\";

		for (int i = 1; i < 5; i++) { // 1월 부터 4월 까지의 폴더 생성
			File n = new File(path_1 + "0" + i);
			if (!n.exists())
				n.mkdirs();
		}

		try {
			for (int month = 1; month <= 4; month++) {
				for (int date_f = 0; date_f <= 3; date_f++) {
					for (int date_a = 0; date_a <= 9; date_a++) {
						if ((date_f == 0 && date_a == 0) || (date_f == 3 && date_a == 2) || (date_f == 3 && date_a == 3)
								|| (date_f == 3 && date_a == 4) || (date_f == 3 && date_a == 5)
								|| (date_f == 3 && date_a == 6) || (date_f == 3 && date_a == 7)
								|| (date_f == 3 && date_a == 8) || (date_f == 3 && date_a == 9)
								|| (month == 2 && date_f == 3 && date_a == 0)
								|| (month == 2 && date_f == 3 && date_a == 1)
								|| (month == 4 && date_f == 2 && date_a == 6)
								|| (month == 4 && date_f == 2 && date_a == 7)
								|| (month == 4 && date_f == 2 && date_a == 8)
								|| (month == 4 && date_f == 2 && date_a == 9)
								|| (month == 4 && date_f == 3 && date_a == 0)
								|| (month == 4 && date_f == 3 && date_a == 1)
								|| (month == 4 && date_f == 3 && date_a == 2))
							continue;

						String strDirPath = "C:\\Users\\a0102\\Desktop\\2020_ner_20200709\\2020\\0" + month + "\\2020-0"
								+ month + "-" + date_f + "" + date_a; // 폴더 경로 ex)
																		// C:\Users\a0102\Desktop\2020_ner_20200709\2020\01\2020-01-01
						String str = strDirPath.substring(strDirPath.length() - 14, strDirPath.length());
						String createfile = path_1 + str + ".csv";

						BufferedWriter fw = new BufferedWriter(
								new OutputStreamWriter(new FileOutputStream(createfile), "MS949")); // FileWriter ->
																									// BufferedWriter
																									// 사용으로 한글 깨짐 보완
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
						fw.append('\n'); // , 로 csv 파일 컬럼 구분

						File path = new File(strDirPath);
						File[] fList = path.listFiles(); // json 파일 여기에 다있음
						for (int t = 0; t < fList.length; t++) {
							String p = fList[t].getPath();
							String[] a = new String[100000000];
							int i = 0;
							File file = new File(p);
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
									// System.out.println(a[m]);
									if (a[m] != null && a[m + 1] != null && a[m].equals("}") && a[m + 1].equals("{")) {
										a[m] = a[m] + ',';
									}

								}
							}
							try (FileWriter fout = new FileWriter(p)) {
								PrintWriter out = new PrintWriter(fout);
								for (String line : a) {
									if (line != null)
										out.println(line);
								}
							}
						}
						// 여기까지 re_json: {} {} {} 으로 잘못된 형식의 파일을 [ {}, {}, {} ] 으로 바꿔주고 덮어씀

						for (int i = 0; i < fList.length; i++) {
							String T = "";
							int count = 0;
							JSONParser jsonParser = new JSONParser();
							String p = fList[i].getPath(); // 파일명의 전체 결로

							JSONArray a = (JSONArray) jsonParser.parse(new FileReader(p));
							for (Object o : a) {

								JSONObject news = (JSONObject) o;
								JSONArray sentence = (JSONArray) news.get("sentence");
								for (Object s : sentence) {
									for (Object S : sentence) {
										JSONObject t = (JSONObject) S;
										String text = (String) t.get("text"); // 기사
										count += text.length(); // 기사글수
										T += text; // T에 기사 속 한 문장인 text를 계속 더해서 하나의 기사를 저장하도록 함

									}
								}
							}

							// 여기까지 json parsing : 기사 내용과 문자 수 추출

							File file = new File(p);
							FileReader filereader = new FileReader(file);
							BufferedReader bufReader = new BufferedReader(filereader);
							String line = "";
							while ((line = bufReader.readLine()) != null) {
								// System.out.println(line);
							}

							int singleCh = 0;
							int json_num = 0; // 한 json 글자수
							while ((singleCh = filereader.read()) != -1) {
								// System.out.print((char)singleCh);
								json_num++;
							}
							// System.out.println(json_num);
							bufReader.close();

							String pp = p.substring(p.lastIndexOf('\\')); // 파일명의 끝줄만 생각

							fw.append("" + (i + 1)); // 뉴스순번
							fw.append(',');
							fw.append(pp.substring(15, pp.lastIndexOf('.'))); // 뉴스 ES_ID
							fw.append(',');
							fw.append(pp.substring(12, 14)); // 뉴스카테고리
							fw.append(',');
							fw.append(null); // 언론사명
							fw.append(',');
							fw.append(pp.substring(1, 11)); // 게재일시
							fw.append(',');
							fw.append(null); // 기자명
							fw.append(',');
							fw.append("" + count); // 문자수 ★
							fw.append(',');
							fw.append(null); // 제목
							fw.append(',');
							fw.append(T); // 기사 ★
							fw.append(',');
							fw.append("Y"); // 사용여부
							fw.append('\n');
						}

						fw.flush();
						fw.close();
					}
				}
			}
		} catch (IOException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}

	}
}
