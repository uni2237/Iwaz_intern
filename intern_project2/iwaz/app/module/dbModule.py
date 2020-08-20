import pymysql

db = pymysql.connect(host='192.168.0.102',
                     port=3306,
                     user='cnu',
                     passwd='cnu123',
                     db='ner_core_tagging_intern',
                     charset='utf8')
try:
    with db.cursor() as cursor:
        sql = """
            select NER_CNTNTS, count(*) from ner_ner_info
            where USE_AT='y' and NER_TYPE='EVT'
            group by NER_CNTNTS 
            order by count(*) DESC;
        """
        cursor.execute(sql)
        result = cursor.fetchall()
        print(result)
finally:
    db.close()