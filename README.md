<img src = "https://user-images.githubusercontent.com/117654450/223961253-bebdd6e8-e467-4c6f-884e-d7fe1e953712.png" height = "350px" width = "680px" allign = "left">

# 📝 소개
<details>
<summary>접기/펼치기</summary>

</br>

* **주제** : 개발자들을 위한 블로그, 벨로그를 클론한 개발자 커뮤니티 사이트입니다. 


* **기간** : 2023년 3월 3일 ~ 2023년 3월 9일


* **팀원**

  * **백엔드** : [강혜광](https://github.com/kingaser), [김우영](https://github.com/micheal1woo), [이상훈](https://github.com/strangehoon)
  * **프론트엔드** : [박정현](https://github.com/swing-park), [남궁윤서](https://github.com/lionloopy)  
  
* **프론트엔드 깃허브** : [Celog front](https://github.com/Celog-clone/Celog-front)

* **STACK** 
  * <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> <img src = "https://img.shields.io/badge/SpringBoot-6DB33F.svg?&style=for-the-badge&logo=SpringBoot&logoColor=white"> <img src = "https://img.shields.io/badge/Amazon%20S3-569A31.svg?&style=for-the-badge&logo=Amazon%20S3&logoColor=white"> <img src = "https://img.shields.io/badge/Amazon%20RDS-569A31.svg?&style=for-the-badge&logo=Amazon%20RDS&logoColor=white">
</details>

</br>

# 📈 와이어프레임

<details>
<summary>접기/펼치기</summary>

<img src = "https://user-images.githubusercontent.com/117654450/223966816-e62d740e-988f-4a54-82ac-9c2f74463894.png" height = "350px" width = "680px" allign = "left">


</details>

</br>

# 💡 기능

<details>
<summary>접기/펼치기</summary>

- 로그인 
    - Validation check (아이디, PW 최소 글자 수 등..)
    - 유저 여부 체크
    - Access-Token cookie 저장
- 회원가입
    - Validation check (아이디, PW 최소 글자 수 등…)
    - 유저 중복 체크
- 메인페이지 
    - 전체 게시글 조회
    - 게시글 검색 (keyword)
    - Infinite Scroll (only front)
    - 상세페이지,마이페이지,작성페이지 routing
- 상세페이지 
    - 게시글 조회 (이미지,타이틀,콘텐트,좋아요갯수,리뷰 …)
    - 좋아요 추가, 취소
    - 댓글 작성,수정,삭제
- 마이페이지 
    - 내가 쓴 글 조회
    - 상세페이지 routing
- 작성(수정)페이지 
    - 게시글 작성,수정,삭제
    - 이미지 미리보기

</details>

</br> 


# 📈 ERD 
<details>
<summary>접기/펼치기</summary>
<img src = "https://user-images.githubusercontent.com/117654450/223970363-c1fde9cd-b15a-4eb1-83d8-1b47c1d7a5e9.png" height = "350px" width = "680px" allign = "left"> 
</details>

</br> 

# 🧐 Convention
<details>
<summary>접기/펼치기</summary>

- ### URL 네이밍 규칙
   
    **1. 명사를 사용한다.**
    
    나쁜 예 : www.fomagran.com/get-users
    좋은 예 : www.fomagran.com/users
    
    **2. 소문자를 사용한다.**
    
    나쁜 예 : www.fomagran.com/Users
    좋은 예 : www.fomagran.com/users
    
    **3. 복수형을 사용한다.**
    
    나쁜 예 : www.fomagran.com/user
    좋은 예 : www.fomagran.com/users
    
    **4. 구분자는 "-"(하이픈)을 사용한다. (카멜 케이스도 허용되지 않음)**
    
    나쁜 예: [www.fomagran.com/](http://www.fomagran.com/)very_good_users
           ,[www.fomagran.com/](http://www.fomagran.com/)veryGoodUsers </br>
    좋은 예 : [www.fomagran.com/](http://www.fomagran.com/)very-good-users
    
    **5. url의 마지막엔 슬래쉬를 포함하지 않음**
    
    나쁜 예 : [www.fomagran.com/](http://www.fomagran.com/)very-good-users/ </br>
    좋은 예 : [www.fomagran.com/](http://www.fomagran.com/)very-good-users
    
    **6. 파일 확장자는 포함하지 않음**
    
    나쁜 예 : [www.fomagran.com/](http://www.fomagran.com/)photos/image.jpg </br>
    좋은 예 : [www.fomagran.com/](http://www.fomagran.com/)photos/image
    
 </br>
    
- ### Git Commit Convention
   
    **`태그 : 제목`의 형태이며, `:`뒤에만 space가 있음에 유의한다.**
    
    - `feat` : 새로운 기능 추가
    - `fix` : 버그 수정
    - `docs` : 문서 수정
    - `style` : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
    - `refactor` : 코드 리펙토링
    - `test` : 테스트 코드, 리펙토링 테스트 코드 추가
    - `chore` : 빌드 업무 수정, 패키지 매니저 수정

</br>

-  ### 스프링 네이밍 컨벤션
    - 서비스 클래스 안에서 메서드 명을 작성 할 때는 아래와 같은 접두사를 붙인다.
        
        findOrder() - 조회 유형의 service 메서드
        
        addOrder() - 등록 유형의 service 메서드
        
        modifyOrder() - 변경 유형의 service 메서드
        
        removeOrder() - 삭제 유형의 service 메서드
        
        saveOrder() – 등록/수정/삭제 가 동시에 일어나는 유형의 service 메서드
        
    - 컨트롤러 클래스 안에서 메서드 명을 작성 할 때는 아래와 같은 접미사를 붙인다.
        
        orderList() – 목록 조회 유형의 서비스
        
        orderDetails() – 단 건 상세 조회 유형의 controller 메서드
        
        orderSave() – 등록/수정/삭제 가 동시에 일어나는 유형의 controller 메서드
        
        orderAdd() – 등록만 하는 유형의 controller 메서드
        
        orderModify() – 수정만 하는 유형의 controller 메서드
        
        orderRemove() – 삭제만 하는 유형의 controller 메서드
</details>

</br>

# 🔨 트러블슈팅
