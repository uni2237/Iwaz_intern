# file name : trm.py
# pwd : /project_name/app/trm/trm.py

import pymysql
from wordcloud import WordCloud
import matplotlib.pyplot as plt

import numpy as np

from flask import Blueprint, request, render_template, flash, redirect, url_for
from flask import current_app as current_app

from app.module import dbModule

four = Blueprint('four', __name__, url_prefix='/')

from PIL import Image
from app.templates.main.index import string_month_four,news_four
import numpy as np

dalong=np.array(Image.open("./static/fonts/dalong2.png"))
wordcloud = WordCloud(font_path='font/H2GTRE.TTF', background_color='white',width=1000,height=800,mask=dalong).generate(string_month_four)
plt.figure(figsize=(12, 12))  # 이미지 사이즈 지정
plt.imshow(wordcloud, interpolation='None')  # 이미지의 부드럽기 정도
plt.axis('off')  # x y 축 숫자 제거
plt.tight_layout(pad=0)
# plt.show()
plt.savefig('./static/fig6.jpg', dpi=300)

@four.route('/four', methods=['GET'])
def index(): # 여기에서 이미지를 만들어야한다.

        return render_template('/month_four/test.html', p = news_four)