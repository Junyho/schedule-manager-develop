# API 명세

---
### 1. 일정(Schedule) API

#### 1.1 일정 생성

| 항목                | 내용                                                                                     |
| ----------------- | -------------------------------------------------------------------------------------- |
| **URL**           | `POST /schedules`                                                                      |
| **Request Body**  | `title` (String, 필수, 최대 30자)<br>`content` (String, 필수, 최대 200자)<br>`userId` (Long, 필수) |
| **Response**      | `201 Created`                                                                          |
| **Response Body** | `id`, `title`, `content`, `userId`, `username`, `createdAt`, `modifiedAt`              |
| **Error**         | `400 Bad Request` - 필수값 누락, 글자 수 초과<br>`404 Not Found` - 존재하지 않는 유저                    |

#### 1.2 일정 목록 조회

| 항목                  | 내용                                                                                   |
| ------------------- | ------------------------------------------------------------------------------------ |
| **URL**             | `GET /schedules`                                                                     |
| **Query Parameter** | `userId` (Long, 선택) - 특정 유저가 작성한 일정만 조회                                              |
| **Response**        | `200 OK`                                                                             |
| **Response Body**   | 일정 목록 배열 (`id`, `title`, `content`, `userId`, `username`, `createdAt`, `modifiedAt`) |

#### 1.3 일정 단건 조회

| 항목                | 내용                                                                                       |
| ----------------- | ---------------------------------------------------------------------------------------- |
| **URL**           | `GET /schedules/{scheduleId}`                                                            |
| **Path Variable** | `scheduleId` (Long, 필수)                                                                  |
| **Response**      | `200 OK`                                                                                 |
| **Response Body** | `id`, `title`, `content`, `userId`, `username`, `createdAt`, `modifiedAt`, `comments` 배열 |
| **Error**         | `404 Not Found` - 존재하지 않는 일정                                                             |

#### 1.4 일정 수정

| 항목                | 내용                                                                                    |
| ----------------- | ------------------------------------------------------------------------------------- |
| **URL**           | `PUT /schedules/{scheduleId}`                                                         |
| **Path Variable** | `scheduleId` (Long, 필수)                                                               |
| **Request Body**  | `title` (String, 필수)<br>`content` (String, 필수)                                        |
| **Response**      | `200 OK`                                                                              |
| **Response Body** | 수정된 일정 정보 (`id`, `title`, `content`, `userId`, `username`, `createdAt`, `modifiedAt`) |
| **Error**         | `400 Bad Request` - 필수값 누락, 글자 수 초과<br>`404 Not Found` - 존재하지 않는 일정                   |
| **비고**            | 작성자는 수정하지 않고, 제목과 내용만 수정 가능                                                           |

#### 1.5 일정 삭제

| 항목                | 내용                               |
| ----------------- | -------------------------------- |
| **URL**           | `DELETE /schedules/{scheduleId}` |
| **Path Variable** | `scheduleId` (Long, 필수)          |
| **Response**      | `204 No Content`                 |
| **Error**         | `404 Not Found` - 존재하지 않는 일정     |


### 2. 유저(User) API

#### 2.1 유저 생성

| 항목                | 내용                                                                                         |
| ----------------- |--------------------------------------------------------------------------------------------|
| **URL**           | `POST /users`                                                                              |
| **Request Body**  | `username` (String, 필수, 최대 10자)<br>`email` (String, 필수, 최대 50자)<br>`password` (String, 필수) |
| **Response**      | `201 Created`                                                                              |
| **Response Body** | `id`, `username`, `email`, `createdAt`, `modifiedAt`                                       |
| **Error**         | `400 Bad Request` - 필수값 누락, 글자 수 초과, 이메일 형식 오류                                             |

#### 2.2 유저 목록 조회

| 항목                  | 내용                                                              |
| ------------------- | --------------------------------------------------------------- |
| **URL**             | `GET /users`                                                    |
| **Response**        | `200 OK`                                                        |
| **Response Body**   | 유저 목록 배열 (`id`, `username`, `email`, `createdAt`, `modifiedAt`) |

#### 2.3 유저 단건 조회

| 항목                | 내용                                                   |
| ----------------- | ---------------------------------------------------- |
| **URL**           | `GET /users/{userId}`                                |
| **Path Variable** | `userId` (Long, 필수)                                  |
| **Response**      | `200 OK`                                             |
| **Response Body** | `id`, `username`, `email`, `createdAt`, `modifiedAt` |
| **Error**         | `404 Not Found` - 존재하지 않는 유저                         |

#### 2.4 유저 수정

| 항목                | 내용                                                                                 |
| ----------------- | ---------------------------------------------------------------------------------- |
| **URL**           | `PUT /users/{userId}`                                                              |
| **Path Variable** | `userId` (Long, 필수)                                                                |
| **Request Body**  | `username` (String, 필수, 최대 30자)<br>`email` (String, 필수)<br>`password` (String, 필수) |
| **Response**      | `200 OK`                                                                           |
| **Response Body** | 수정된 유저 정보 (`id`, `username`, `email`, `createdAt`, `modifiedAt`)                   |
| **Error**         | `400 Bad Request` - 필수값 누락, 이메일 형식 오류, 비밀번호 불일치<br>`404 Not Found` - 존재하지 않는 유저    |
| **비고**            | 유저명과 이메일 수정 가능                                                                     |

#### 2.5 유저 삭제

| 항목                | 내용                            |
| ----------------- |-------------------------------|
| **URL**           | `DELETE /users/{userId}`      |
| **Path Variable** | `userId` (Long, 필수)           |
| **Response**      | `204 No Content`              |
| **Error**         | `404 Not Found` - 존재하지 않는 유저  |

