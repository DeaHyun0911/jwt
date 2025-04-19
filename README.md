# Spring Boot 기반 JWT 인증/인가
Spring Security와 JWT를 활용해 구현한 사용자 인증/인가 시스템입니다.

## 제공 기능
자세한 API 명세는 Swagger UI를 통해 확인할 수 있습니다.

| 기능        | method  | url                         |
|-----------|---------|-----------------------------|
| 회원가입      | `POST`  | /auth/signup                |
| 로그인       | `POST`  | /auth/login                 | 
| 관리자 관한 부여 | `PATCH` | /admin/users/{userId}/roles |
| 관리자 계정 생성 | `POST`   | /create-admin               |

## 회원가입
일반회원으로 유저정보를 생성합니다.
#### Request Body 파라미터
- username `필수` • string
- password `필수` • string
- nickname `필수` • string

#### Response Body
```json
{
  "id": 1,
  "username": "string",
  "nickname": "string",
  "roles": [
    {
      "role": "USER"
    }
  ]
}
```

 
