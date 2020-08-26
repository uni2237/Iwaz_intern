# file name : index.py
# pwd : /project_name/app/main/index.py
import pymysql
from flask import Blueprint, request, render_template, flash, redirect, url_for
from flask import current_app as current_app

main = Blueprint('main', __name__, url_prefix='/')


def my_db():
    global db
    db = pymysql.connect(host='192.168.0.102',
                         port=3306,
                         user='cnu',
                         passwd='cnu123',
                         db='ner_core_tagging',
                         charset='utf8')


def link_bio(result,string_num):
    global i
    try:
        for i in range(len(result)):
            if result[i][1] == 'B':
                string_num = string_num + "  " + result[i][0]

            elif result[i][1] == 'I':
                if result[i + 1][1] == 'B' or result[i + 1][1] == 'I':
                    string_num = string_num + result[i][0]

    except:
        pass
    return string_num


def run(sql,string_num):
    my_db()
    global cursor,i
    try:
        with db.cursor() as cursor:

            cursor.execute(sql)
            result = cursor.fetchall()

            new_result = [[0] * 2 for _ in range(len(result))]

            for i in range(len(result)):
                new_result[i][0] = result[i][1]
                new_result[i][1] = result[i][2]
            # print(result)

            s=link_bio(new_result,string_num)

            # print("0:" + string_num)
    finally:
        db.close()
    return s

string_trm = ""
string_evt = ""
string_month_one = ""
string_month_two = ""
string_month_three = ""
string_month_four = ""

sql_trm = """
        select distinct(NEWS_SEQ),NER_CNTNTS, bio from ner_ner_info
        where USE_AT='y' and (bio='B' or bio='I') and NER_TYPE ='TRM' and MORP_SEQ is not null
        order by MORP_SEQ;     
                """

sql_evt = """
        select distinct(NEWS_SEQ),NER_CNTNTS, bio from ner_ner_info
        where USE_AT='y' and (bio='B' or bio='I') and NER_TYPE ='EVT' and MORP_SEQ is not null
        order by MORP_SEQ;     
"""
sql_month_one= """
        select a.NEWS_SEQ, NER_CNTNTS, bio from ner_news_info as a
        join ner_ner_info as b on a.NEWS_SEQ=b.NEWS_SEQ
        where write_dt like '2020-01%' and a.USE_AT='y' and (BIO='B' OR BIO='I') and morp_seq is not null
        ORDER BY a.NEWS_SEQ, SNTNC_SEQ, MORP_seq, ner_bgn asc;
"""
sql_month_two= """
        select a.NEWS_SEQ, NER_CNTNTS, bio from ner_news_info as a
        join ner_ner_info as b on a.NEWS_SEQ=b.NEWS_SEQ
        where write_dt like '2020-02%' and a.USE_AT='y' and (BIO='B' OR BIO='I') and morp_seq is not null 
        ORDER BY a.NEWS_SEQ, SNTNC_SEQ, MORP_seq, ner_bgn asc;
"""
sql_month_three= """
        select a.NEWS_SEQ, NER_CNTNTS, bio from ner_news_info as a
        join ner_ner_info as b on a.NEWS_SEQ=b.NEWS_SEQ
        where write_dt like '2020-03%' and a.USE_AT='y' and (BIO='B' OR BIO='I') and morp_seq is not null 
        ORDER BY a.NEWS_SEQ, SNTNC_SEQ, MORP_seq, ner_bgn asc;
"""
sql_month_four= """
        select a.NEWS_SEQ, NER_CNTNTS, bio from ner_news_info as a
        join ner_ner_info as b on a.NEWS_SEQ=b.NEWS_SEQ
        where write_dt like '2020-04%' and a.USE_AT='y' and (BIO='B' OR BIO='I') and morp_seq is not null
        ORDER BY a.NEWS_SEQ, SNTNC_SEQ, MORP_seq, ner_bgn asc;
"""


string_trm = run(sql_trm,string_trm)
string_evt = run(sql_evt, string_evt)
string_month_one = run(sql_month_one, string_month_one)
string_month_two = run(sql_month_two, string_month_two)
string_month_three = run(sql_month_three, string_month_three)
string_month_four = run(sql_month_four, string_month_four)


@main.route('/main', methods=['GET'])
def index():
    return render_template('/main/index.html')