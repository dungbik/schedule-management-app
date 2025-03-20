# 일정 관리 앱

일정을 관리할 수 있는 앱의 API 서버 개발

사용자를 등록하고 일정을 등록, 조회, 수정, 삭제 하는 기능들이 있다.

Rest Docs + Swagger 를 통해 API 를 테스트 할 수 있으며 API 문서를 확인 할 수 있다. 


## 개발 환경
- <img src="https://img.shields.io/badge/Java-17-blue" alt="Java"> <img src="https://img.shields.io/badge/Gradle-8.13-blue" alt="Gradle">
- <img src="https://img.shields.io/badge/Spring-6.2.3-blue" alt="Spring"> <img src="https://img.shields.io/badge/Spring%20Boot-3.4.3-blue" alt="Spring Boot">
- <img src="https://img.shields.io/badge/MySQL-8.0.41-blue" alt="MySQL">
- <img src="https://img.shields.io/badge/JUnit-5-blue" alt="JUnit">
- <img src="https://img.shields.io/badge/Rest Docs-gray" alt="Rest Docs"> <img src="https://img.shields.io/badge/Swagger-gray" alt="Swagger"> 
- <img src="https://img.shields.io/badge/IntelliJ IDEA Ultimate-gray" alt="IntelliJ IDEA Ultimate">

## 프로젝트 구조
```
📂 nbc
└── 📂 sma
    ├── 📂 controller                
    │   ├── 📄 ScheduleController    
    │   └── 📄 UserController  
    │     
    ├── 📂 dto    
    │   ├── 📂 request        
    │   │   ├── 📄 CreateScheduleRequest
    │   │   ├── 📄 EditScheduleRequest
    │   │   │── 📄 RegisterRequest
    │   │   └── 📄 ScheduleSearchCond
    │   │── 📂 response              
    │   │   ├── 📄 FindSchedulesResponse
    │   │   ├── 📄 ScheduleResponse
    │   │   └── 📄 UserResponse
    │   └── 📂 mapper                    
    │        ├── 📄 ScheduleMapper
    │        └── 📄 UserMapper
    │
    ├── 📂 entity                    
    │   ├── 📄 Schedule
    │   └── 📄 User
    │
    ├── 📂 exception                              
    │   ├── 📄 BizException
    │   ├── 📄 InvalidPasswordException
    │   ├── 📄 NotFoundException
    │   ├── 📄 ErrorResponse         
    │   └── 📄 GlobalControllerAdvice
    │
    ├── 📂 repository   
    │   │── 📂 jdbc      
    │   │    ├── 📄 JdbcScheduleRepository
    │   │    └── 📄 JdbcUserRepository
    │   ├── 📄 ScheduleRepository
    │   └── 📄 UserRepository
    │
    ├── 📂 service                    
    │   ├── 📄 ScheduleService
    │   ├── 📄 UserService
    │   └── 📄 ScheduleManagementApplication
    │
    └── 📂 resources                  
        ├── 📂 rest                   
        │   └── 📄 schedule.http
        ├── 📂 static                 
        │   └── 📂 docs
        │       └── 📄 open-api-3.0.1.json
        ├── 📂 templates              
        └── 📄 application.yml    
```

## 실행 방법
1. resources/application.yml 에서 데이터베이스 연결 정보를 수정
2. ./gradlew bootJar (Linux, Mac) or ./gradlew.bat bootJar (Windows)
3. http://localhost:8080/* (API 주소)
4. http://localhost:8080/swagger-ui/index.html (API 문서 주소)

## 설계

### ERD
![ERD](img/erd.png)

### API 명세서
[Link](http://test.yoonleeverse.com:7777/swagger-ui/index.html)

## 트러블 슈팅
[Link](https://dungbik.github.io/p/til-2/)
