# API 명세

---

## 1. 인증(Auth) API

### 1.1 로그인

| 항목                | 내용                                                                            |
| ----------------- | ----------------------------------------------------------------------------- |
| **URL**           | `POST /login`                                                                 |
| **Request Body**  | `email` (String, 필수)<br>`password` (String, 필수)                               |
| **Response**      | `200 OK`                                                                      |
| **Response Body** | 없음                                                                            |
| **동작**            | 이메일과 비밀번호가 일치하면 서버 세션에 로그인 사용자 정보를 저장                                         |
| **Error**         | `400 Bad Request` - 필수값 누락, 이메일 형식 오류<br>`401 Unauthorized` - 이메일 또는 비밀번호 불일치 |

### 1.2 로그아웃

| 항목                | 내용                                |
| ----------------- | --------------------------------- |
| **URL**           | `POST /logout`                    |
| **Request Body**  | 없음                                |
| **Response**      | `200 OK`                          |
| **Response Body** | 없음                                |
| **동작**            | 현재 로그인된 사용자의 세션을 만료시킴             |
| **Error**         | `401 Unauthorized` - 로그인하지 않은 사용자 |


## 2. 일정(Schedule) API

### 2.1 일정 생성

| 항목                | 내용                                                                        |
| ----------------- | ------------------------------------------------------------------------- |
| **URL**           | `POST /schedules`                                                         |
| **Request Body**  | `title` (String, 필수, 최대 30자)<br>`content` (String, 필수, 최대 200자)           |
| **Response**      | `201 Created`                                                             |
| **Response Body** | `id`, `title`, `content`, `userId`, `username`, `createdAt`, `modifiedAt` |
| **Error**         | `400 Bad Request` - 필수값 누락, 글자 수 초과<br>`401 Unauthorized` - 로그인하지 않은 사용자  |
| **비고**            | 로그인 세션에 저장된 사용자를 일정 작성자로 등록                                               |

### 2.2 일정 목록 조회

| 항목                  | 내용                                                                                   |
| ------------------- | ------------------------------------------------------------------------------------ |
| **URL**             | `GET /schedules`                                                                     |
| **Query Parameter** | 없음                                                                                   |
| **Response**        | `200 OK`                                                                             |
| **Response Body**   | 일정 목록 배열 (`id`, `title`, `content`, `userId`, `username`, `createdAt`, `modifiedAt`) |

### 2.3 일정 단건 조회

| 항목                | 내용                                                                        |
| ----------------- | ------------------------------------------------------------------------- |
| **URL**           | `GET /schedules/{scheduleId}`                                             |
| **Path Variable** | `scheduleId` (Long, 필수)                                                   |
| **Response**      | `200 OK`                                                                  |
| **Response Body** | `id`, `title`, `content`, `userId`, `username`, `createdAt`, `modifiedAt` |
| **Error**         | `404 Not Found` - 존재하지 않는 일정                                              |

### 2.4 일정 수정

| 항목                | 내용                                                                                                                                           |
| ----------------- | -------------------------------------------------------------------------------------------------------------------------------------------- |
| **URL**           | `PUT /schedules/{scheduleId}`                                                                                                                |
| **Path Variable** | `scheduleId` (Long, 필수)                                                                                                                      |
| **Request Body**  | `title` (String, 필수, 최대 30자)<br>`content` (String, 필수, 최대 200자)                                                                              |
| **Response**      | `200 OK`                                                                                                                                     |
| **Response Body** | 수정된 일정 정보 (`id`, `title`, `content`, `userId`, `username`, `createdAt`, `modifiedAt`)                                                        |
| **Error**         | `400 Bad Request` - 필수값 누락, 글자 수 초과<br>`401 Unauthorized` - 로그인하지 않은 사용자<br>`403 Forbidden` - 본인이 작성한 일정이 아님<br>`404 Not Found` - 존재하지 않는 일정 |
| **비고**            | 작성자는 수정하지 않고, 제목과 내용만 수정 가능                                                                                                                  |

### 2.5 일정 삭제

| 항목                | 내용                                                                                                    |
| ----------------- | ----------------------------------------------------------------------------------------------------- |
| **URL**           | `DELETE /schedules/{scheduleId}`                                                                      |
| **Path Variable** | `scheduleId` (Long, 필수)                                                                               |
| **Response**      | `204 No Content`                                                                                      |
| **Error**         | `401 Unauthorized` - 로그인하지 않은 사용자<br>`403 Forbidden` - 본인이 작성한 일정이 아님<br>`404 Not Found` - 존재하지 않는 일정 |

---

## 3. 유저(User) API

### 3.1 유저 생성

| 항목                | 내용                                                                                         |
| ----------------- | ------------------------------------------------------------------------------------------ |
| **URL**           | `POST /users`                                                                              |
| **Request Body**  | `username` (String, 필수, 최대 10자)<br>`email` (String, 필수, 최대 50자)<br>`password` (String, 필수) |
| **Response**      | `201 Created`                                                                              |
| **Response Body** | `id`, `username`, `email`, `createdAt`, `modifiedAt`                                       |
| **Error**         | `400 Bad Request` - 필수값 누락, 글자 수 초과, 이메일 형식 오류                                             |
| **비고**            | 비밀번호는 암호화하여 저장                                                                             |

### 3.2 유저 목록 조회

| 항목                | 내용                                                              |
| ----------------- | --------------------------------------------------------------- |
| **URL**           | `GET /users`                                                    |
| **Response**      | `200 OK`                                                        |
| **Response Body** | 유저 목록 배열 (`id`, `username`, `email`, `createdAt`, `modifiedAt`) |

### 3.3 유저 단건 조회

| 항목                | 내용                                                   |
| ----------------- | ---------------------------------------------------- |
| **URL**           | `GET /users/{userId}`                                |
| **Path Variable** | `userId` (Long, 필수)                                  |
| **Response**      | `200 OK`                                             |
| **Response Body** | `id`, `username`, `email`, `createdAt`, `modifiedAt` |
| **Error**         | `404 Not Found` - 존재하지 않는 유저                         |

### 3.4 유저 수정

| 항목                | 내용                                                                                         |
| ----------------- | ------------------------------------------------------------------------------------------ |
| **URL**           | `PUT /users/{userId}`                                                                      |
| **Path Variable** | `userId` (Long, 필수)                                                                        |
| **Request Body**  | `username` (String, 필수, 최대 10자)<br>`email` (String, 필수, 최대 50자)<br>`password` (String, 필수) |
| **Response**      | `200 OK`                                                                                   |
| **Response Body** | 수정된 유저 정보 (`id`, `username`, `email`, `createdAt`, `modifiedAt`)                           |
| **Error**         | `400 Bad Request` - 필수값 누락, 글자 수 초과, 이메일 형식 오류<br>`404 Not Found` - 존재하지 않는 유저             |
| **비고**            | 비밀번호는 암호화하여 저장                                                                             |

### 3.5 유저 삭제

| 항목                | 내용                           |
| ----------------- | ---------------------------- |
| **URL**           | `DELETE /users/{userId}`     |
| **Path Variable** | `userId` (Long, 필수)          |
| **Response**      | `204 No Content`             |
| **Error**         | `404 Not Found` - 존재하지 않는 유저 |
