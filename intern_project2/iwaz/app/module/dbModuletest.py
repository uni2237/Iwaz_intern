import pymysql
from wordcloud import WordCloud
import matplotlib.pyplot as plt

db = pymysql.connect(host='192.168.0.102',
                     port=3306,
                     user='cnu',
                     passwd='cnu123',
                     db='ner_core_tagging',
                     charset='utf8')

try:
    with db.cursor() as cursor:
        sql = """
                select distinct(NEWS_SEQ),NER_CNTNTS, bio from ner_ner_info
        where USE_AT='y' and (bio='B' or bio='I') and NER_TYPE ='TRM' and MORP_SEQ is not null
        order by MORP_SEQ;     
                """
        cursor.execute(sql)
        result = cursor.fetchall()
        string = ""

        new_result = [[0] * 2 for _ in range(len(result))]

        for i in range(len(result)):
            new_result[i][0] = result[i][1]
            new_result[i][1] = result[i][2]
        print(new_result)

        try:
            for i in range(len(new_result)):
                if new_result[i][1] == 'B':
                    string = string + "  " + new_result[i][0]

                elif new_result[i][1] == 'I':
                    if new_result[i + 1][1] == 'B' and new_result[i + 1][1] == 'I':
                        string = string + new_result[i][0]

        except:
            pass

        print(string) # string 배열 완성

        wordcloud = WordCloud(font_path='font/H2GTRE.TTF', background_color='white').generate(string)
        plt.figure(figsize=(22, 22))  # 이미지 사이즈 지정
        plt.imshow(wordcloud, interpolation='lanczos')  # 이미지의 부드럽기 정도
        plt.axis('off')  # x y 축 숫자 제거
        #plt.show()
        plt.savefig('fig1.jpg', dpi=300)

finally:
    db.close()