# file name : __init__.py
# pwd : /project_name/app/__init__.py

from flask import Flask

app= Flask(__name__)

# 추가할 모듈이 있다면 추가

from app.templates.main.index import main as main
from app.templates.test.test import test as test

# 위에서 추가한 파일을 연동해주는 역할
app.register_blueprint(main)# as main으로 설정해주었으므로
app.register_blueprint(test)