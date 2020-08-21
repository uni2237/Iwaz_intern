import pymysql

import wordcloud
#import WordCloud
import matplotlib.pyplot as plt


conn = pymysql.connect(host="192.168.0.102"
                       ,user="cnu"
                       ,password="cnu123"
                       ,db="ner_core_tagging_intern"
                       ,charset="utf8")
#curs = conn.cursor()
curs = conn.cursor(pymysql.cursors.DictCursor)
#sql = "select * from ner_ner_info"
#curs.execute(sql)
#sql = "select * from ner_ner_info where NER_TYPE=%s"
#curs.execute(sql,'TRM')
sql = "select *" \
      "from ner_ner_info " \
      "where NER_TYPE ='TRM' and USE_AT = 'y' "
curs.execute(sql)
rows = curs.fetchall()
#print(rows) #전체 다 보임

a=[]
for row in rows:
    #print(row)
    #print(row[3],row[7])
    print(row['NER_CNTNTS'],row['bio'])
    a.append(row['NER_CNTNTS'])

print(a)
#for i in a:
#    wordcloud=wordcloud.WordCloud(font_path='font/NanumGothic.ttf',background_color='white').generate(i)
#    plt.figure(figsize=(22,22))
#    plt.imshow(wordcloud,interpolation='lanczos')
#    plt.axis('off')
#    plt.show()
#    plt.savefig()
conn.close()
