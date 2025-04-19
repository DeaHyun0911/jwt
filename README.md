# Spring Boot 기반 JWT 인증/인가
Spring Security와 JWT를 활용해 구현한 사용자 인증/인가 시스템입니다.
- JWT를 이용해 사용자를 인증하여 토큰을 발급합니다.
- Spring Security를 활용해 서비스별로 접근을 구분하고 인증된 회원의 JWT를 통해 권한을 확인하고 인가합니다.

## 제공 기능
자세한 API 명세는 Swagger UI를 통해 확인할 수 있습니다.
http://ec2-35-174-13-147.compute-1.amazonaws.com:8080/swagger-ui/index.html

| 기능        | method  | url                         |
|-----------|---------|-----------------------------|
| 회원가입      | `POST`  | /auth/signup                |
| 로그인       | `POST`  | /auth/login                 | 
| 관리자 권한 부여 | `PATCH` | /admin/users/{userId}/roles |
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
  "username": "user",
  "nickname": "1234",
  "roles": [
    {
      "role": "USER"
    }
  ]
}
```

## 로그인
회원정보를 검증하고 JWT 토큰을 발급합니다.
토큰은 응답헤더의 `authorization` 필드에 포함됩니다.
#### Request Body 파라미터
- username `필수` • string
- password `필수` • string

#### Response Body
```json
{
  "token": "token value"
}
```

## 관리자 권한 부여
`userId`의 회원권한을 `ADMIN`으로 변경합니다. </br>
해당 API를 호출하려면 `ADMIN` 권한이 필요합니다.
#### Path 파라미터
- userId `필수` • number

#### Response Body
```json
{
  "id": 1,
  "username": "adminUser",
  "nickname": "1234",
  "roles": [
    {
      "role": "ADMIN"
    }
  ]
}
```

## 관리자 계정 생성
`ADMIN` 권한으로 유저정보를 생성합니다. </br>

#### Request Body 파라미터
- username `필수` • string
- password `필수` • string
- nickname `필수` • string

#### Response Body
```json
{
  "id": 1,
  "username": "user",
  "nickname": "1234",
  "roles": [
    {
      "role": "ADMIN"
    }
  ]
}
```

## Swagger에서 테스트해보기
- 회원가입을 합니다.
- 로그인을 합니다.
- 응답된 토큰값을 복사합니다.
- 우측 상단 `Authorize` 버튼을 눌러 value에 토큰값을 입력하고 `Authorize` 버튼을 클릭합니다.
- `Authorize` 버튼이 `Logout` 버튼으로 변경되었다면 토큰값이 정상적으로 입력되어 로그인 상태로 변경됩니다.
- 관리자 전용 API를 호출하기 위해선 `관리자 계정 생성`을 통해 회원가입합니다.

## 에러 확인하기

| 상태 코드 | 에러 코드                 | 메시지              |
|-------|:----------------------|------------------|
| 400   | `USER_ALREADY_EXISTS` | 이미 가입된 사용자입니다.   |
| 400   | `PASSWORD_MISMATCH`   | 비밀번호가 일치하지 않습니다. |
| 400   | `NOT_VALIDATION`      | 형식이 올바르지 않습니다.   |
| 401   | `UNAUTHORIZED`        | 로그인이 필요합니다.      |
| 401   | `NOT_FOUND_TOKEN`     | 토큰을 찾을 수 없습니다.   |
| 401   | `EXPIRED_TOKEN`       | 만료된 토큰입니다.       |
| 401   | `INVALID_TOKEN`       | 유효하지 않은 토큰입니다.   |
| 403   | `FORBIDDEN`           | 접근권한이 없습니다.      |
| 404   | `NOT_FOUND_USER`      | 회원정보를 찾을 수 없습니다. |





 
