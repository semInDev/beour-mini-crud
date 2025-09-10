참고 강의: [개발자 유미 - Swagger 강의](https://www.youtube.com/@xxxjjhhh)

# 1. API 명세와 Swagger

## 강의 목표

 스프링 부트에서 작성한 엔드포인트를 OpenAPI 3.0 스펙으로 명세서를 생성하고, Swagger UI를 통해 대시보드화 하는 방법 학습

---

 API를 사용하기 위해서는 “기능, 요청 방법, 응답 형식”을 파악해야 한다.

전세계에는 수많은 API가 존재하고 각각의 개발자들이 자신만의 형태로 명세서를 작성하게 된다면 새로운 API를 사용할 때마다 명세서의 형태를 파악해야한다. 따라서 국제적으로 API 명세 표준을 몇 가지 만들어 두었다.

- OpenAPI (OAS) → 내부적으로 JSON/YAML 형태의 포맷이 있음
- RAML
- API Blueprint

강의에서 사용할 방법은 OpenAPI 방법!

OpenAPI는 버전이 존재하며 현대 3.0이라 불리는 3.X번대의 버전이 stable하고 많이 사용된다.

엔드포인트에 대한 설명을 OAS JSON 구조로 작성하면 아래와 같습니다.

- **JSON 구조(예시)**

```
{
  "openapi": "3.0.1",
  "info": {
    "title": "User API",
    "version": "1.0.0",
    "description": "API for managing users"
  },
  "paths": {
    "/users/{id}": {
      "get": {
        "summary": "Get user by ID",
        "parameters": [
          {
            "name": "id",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer"
            },
            "description": "ID of the user to return"
          }
        ],
        "responses": {
          "200": {
            "description": "User found",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/User"
                }
              }
            }
          },
          "404": {
            "description": "User not found"
          }
        }
      }
    },
    "/users": {
      "post": {
        "summary": "Create a new user",
        "requestBody": {
          "required": true,
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/UserInput"
              }
            }
          }
        },
        "responses": {
          "201": {
            "description": "User created successfully",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/User"
                }
              }
            }
          },
          "400": {
            "description": "Invalid input"
          }
        }
      }
    }
  },
  "components": {
    "schemas": {
      "User": {
        "type": "object",
        "properties": {
          "id": { "type": "integer", "example": 1 },
          "name": { "type": "string", "example": "John Doe" },
          "email": { "type": "string", "example": "john@example.com" }
        },
        "required": ["id", "name", "email"]
      },
      "UserInput": {
        "type": "object",
        "properties": {
          "name": { "type": "string", "example": "Jane Doe" },
          "email": { "type": "string", "example": "jane@example.com" }
        },
        "required": ["name", "email"]
      }
    }
  }
}
```

하지만, 이러한 형태로 작성하여 프론트에게 전달하는 것이 아니라 Swagger UI에 넣어 가독성있게 시각화할 수 있다.

# 2. 엔드포인트 OAS 생성

## 의존성 추가

https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui

주로 사용되는 버전을 복사해서 의존성 추가하기

- build.gradle

```
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8'
```

## Config 추가

```
package com.beour.beourminicrud.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
       return new OpenAPI()
               .info(new Info()
                       .title("beour-mini-crud API Document")
                       .version("v1.0.0")
                       .description("환영합니다! 이 API 문서는 beour-mini-crud의 API를 사용하는 방법을 설명합니다."))
               .servers(List.of(
                       new Server()
                               .url("http://localhost:8080")
                               .description("개발용 서버")
               ));
    }
}

```
<img width="2045" height="1512" alt="image" src="https://github.com/user-attachments/assets/12cbc636-21f6-41fb-8d88-33cc736bad95" />


-> 현재 프로젝트에서는 Springsecurity가 설정되어 있어서 security config 파일에도 추가적인 설정이 필요하다. 해당 내용은 다른 파일에 작성해두겠다.



# 3. 엔드포인트 그룹화

## 클래스별 그룹화
: 게시글 CRUD, 댓글 CRUD별 그룹화

```java
@Tag(name = "space API", description = "공간 도메인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceController {
```
<img width="1403" height="631" alt="image" src="https://github.com/user-attachments/assets/d608e27e-f40d-4f50-9a52-52d876103cf2" />


## 대그룹화: 버전별 그룹화

- beour-mini-crud 프로젝트는 버전별로 나눈 api가 없기때문에 주석처리
```java
/*    // 버전별로 그룹화
    @Bean
    public GroupedOpenApi groupedOpenApiV1() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV2() {
        return GroupedOpenApi.builder()
                .group("v2")
                .pathsToMatch("/api/v2/**")
                .build();
    }*/
```

<img width="1921" height="591" alt="image" src="https://github.com/user-attachments/assets/1fd0b714-4bf7-4844-9e8a-8063e827aea0" />




# 4. 엔드포인트 명세

: 명세는 “엔드포인트 역할”, “요청 방법”, “응답 종류”에 대해 애노테이션을 기반으로 작성해주면 된다.

## 요청에 대한 명세

### Query 파라미터 변수/Path 파라미터 변수

```java
    @Operation(
            summary = "공간 정보 Read",
            description = "공간의 ID를 Path 파라미터로 보내면 해당하는 공간 조회",
            parameters = {
                    @Parameter(
                            name = "spaceId",
                            description = "조회할 공간 ID",
                            required = true,
                            in = ParameterIn.PATH // ParameterIn.QUERY라고 쓰면 쿼리 파라미터 변수
                    )
            }
    )
    @GetMapping("/{spaceId}")
    public ResponseEntity<SpaceResponseDto> getSpace(@PathVariable Long spaceId) {
        return ResponseEntity.ok(spaceService.getSpace(spaceId));
    }
```
<img width="1428" height="1522" alt="image" src="https://github.com/user-attachments/assets/36545986-53d5-496f-b605-67e2c8662255" />



### JSON Body

```java
    @Operation(
            summary = "공간 등록 API",
            description = "공간 등록폼 데이터를 보내면 공간 정보가 DB에 저장됨.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "공간 등록폼 JSON Body 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SpaceRequestDto.class)
                    )
            )
    )
    @PostMapping
    public ResponseEntity<SpaceResponseDto> createSpace(@Validated @RequestBody SpaceRequestDto spaceRequestDto) {
        SpaceResponseDto spaceResponseDto = spaceService.createSpace(spaceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED) // REST 관례상 201 Created가 더 적절. GET은 200 OK!
                .body(spaceResponseDto);
    }
```
<img width="1412" height="915" alt="image" src="https://github.com/user-attachments/assets/20196d8f-dd94-4b1f-bfde-551e0f13ef7f" />



## 응답에 대한 명세

```java
    @Operation(
            summary = "공간 등록 API",
            description = "공간 등록폼 데이터를 보내면 공간 정보가 DB에 저장됨.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "공간 등록폼 JSON Body 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SpaceRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "공간 등록 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SpaceResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "실패"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<SpaceResponseDto> createSpace(@Validated @RequestBody SpaceRequestDto spaceRequestDto) {
        SpaceResponseDto spaceResponseDto = spaceService.createSpace(spaceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED) // REST 관례상 201 Created가 더 적절. GET은 200 OK!
                .body(spaceResponseDto);
    }
```
<img width="1412" height="1197" alt="image" src="https://github.com/user-attachments/assets/830cb79b-ec12-4263-bcac-07fcd3b23585" />





