----------------------------------------------------------------------------------
----------------------------------------------------------------------------------
로컬 백업 필수.
.classpath
.factorypath
.project 파일이 사라지는 증상이 있음.

Package Explorer 에서 새로고침 하시고 나서
메이븐 업데이트를 먼저 하세요.
----------------------------------------------------------------------------------
----------------------------------------------------------------------------------

ReadMe & 환경설정

공통부분 레이아웃.
https://mocawrite.cafe24.com/carbonbank/page.html

퍼블리싱 목록
https://mocawrite.cafe24.com/carbonbank/pagelist.html

프로그램 목록 (구글 스프레드 시트)
https://docs.google.com/spreadsheets/d/1dVuzsw_p1Ez28fm55nCcx_-SgLrBjUj4jGEhv6WHiEI/edit?gid=0#gid=0


디자인 화면 리스트주소입니다
----------------------------------------------------------------------------------
프론트
https://mocawrite.cafe24.com/carbonbank/pagelist.html

관리자
https://mocawrite.cafe24.com/admin/pagelist.html
----------------------------------------------------------------------------------

http://localhost:8200/adm/login.do
carbonbankadm / carbonbankadm

로그인 이후 :
http://localhost:8200/adm/content/NoticeMainList.do

프론트 메인 : 
http://localhost:8100/main/cbMain.do

http://localhost:8200/adm/content/EnvNewsMainList.do
http://localhost:8200/adm/content/HotNewsMainList.do

----------------------------------------------------------------------------------
----------------------------------------------------------------------------------
작업 메모 : 
CRBN_HOT_NEWS.CRE_DTM 칼럼명을 CRBN_HOT_NEWS.REG_DTM 으로 수정함.
같은 서비스를 사용하면 매퍼 구분이 가능한가?

썸네일 저장할 때 중간에 점 제거
왼쪽메뉴 하이라이트 (현재 선택메뉴) 기능 추가. (목록에서만)
첨부 파일없어도 최초 저장 가능하도록 처리.
모델 변경사항 commonVo.java에 추가해야 함(주의)
----------------------------------------------------------------------------------

TO-DO :
목록에 총건수 표시.
푸시발송? 정부장님.
검색기능 보류
페이징? 보류
좋아요, 싫어요, 슬퍼요, 화나요.외...
콘솔 네트워크에 list 잡히지 않는 이유?
환경뉴스등 NewCommonModel 외 모델 정보 수정.
----------------------------------------------------------------------------------

INSERT INTO carbonbankDB.CRBN_HOT_NEWS ( PARTY_CD, DOC_TITLE, IMG_SRC_NM, IMG_NAIL_NM, DOC_INFO, DOC_FROM, DOC_URL, DOC_READ, DOC_RCMND, DOC_LIKE, DOC_SAD,
	DOC_ANGRY, DOC_STAT, REG_ID, REG_DTM, SHOW_ID, SHOW_DTM, CANCEL_ID, CANCEL_DTM, DEL_ID, DEL_DTM)
VALUES('CARBONBANK', '페이징 테스트', '', '', 
'페이징 테스트
내용수정', '다음 2 naver', 'www.daum.net www.naver.com', 0, 0, 0, 0, 0, 'C', 'carbonbankadm', '2025-05-20 23:13:36', NULL, '2025-05-20 23:15:14', NULL, '2025-05-20 23:15:18', NULL, NULL);


핫뉴스 총건수 표시 (타임리프)
페이징 오류 (타임리프)



----------------------------------------------------------------------------------
----------------------------------------------------------------------------------


관리자   http://www.carbonbank.re.kr:8200/adm/login.do      carbonbankadm/carbonbankadm
앱      http://www.carbonbank.re.kr/home/cbLogin.do   01032842290 / 123456



----------------------------------------------------------------------------------
----------------------------------------------------------------------------------
