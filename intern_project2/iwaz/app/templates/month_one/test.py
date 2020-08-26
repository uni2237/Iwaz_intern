# file name : trm.py
# pwd : /project_name/app/trm/trm.py

import pymysql
from wordcloud import WordCloud
import matplotlib.pyplot as plt

import numpy as np

from flask import Blueprint, request, render_template, flash, redirect, url_for
from flask import current_app as current_app

from app.module import dbModule
from app.templates.main.index import string_month_one

one = Blueprint('one', __name__, url_prefix='/')

wordcloud = WordCloud(font_path='font/H2GTRE.TTF', background_color='white').generate(string_month_one)
plt.figure(figsize=(22, 22))  # 이미지 사이즈 지정
plt.imshow(wordcloud, interpolation='lanczos')  # 이미지의 부드럽기 정도
plt.axis('off')  # x y 축 숫자 제거
# plt.show()
plt.savefig('./static/fig3.jpg', dpi=300)

@one.route('/one', methods=['GET'])
def index(): # 여기에서 이미지를 만들어야한다.

        return render_template('/month_one/test.html')