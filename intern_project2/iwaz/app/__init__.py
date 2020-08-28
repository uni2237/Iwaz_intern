# file name : __init__.py
# pwd : /project_name/app/__init__.py

from flask import Flask

app= Flask(__name__)

# 추가할 모듈이 있다면 추가

from app.templates.main.index import main as main

from app.templates.trm.test import test as trm
from app.templates.evt.test import evt as evt

from app.templates.month_one.test import one as one
from app.templates.month_two.test import two as two
from app.templates.month_three.test import three as three
from app.templates.month_four.test import four as four

# 위에서 추가한 파일을 연동해주는 역할
app.register_blueprint(main)# as main으로 설정해주었으므로

app.register_blueprint(trm)
app.register_blueprint(evt)

app.register_blueprint(one)
app.register_blueprint(two)
app.register_blueprint(three)
app.register_blueprint(four)


