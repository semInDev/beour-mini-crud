package com.beour.beourminicrud.space;

import com.beour.beourminicrud.space.dto.SpaceRequestDto;
import com.beour.beourminicrud.space.dto.SpaceResponseDto;
import com.beour.beourminicrud.space.service.SpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "space API", description = "공간 도메인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceController {

    /*    // Spring이 Bean을 등록하는 순간을 로그로 확인해보기
    public SpaceController() {
        System.out.println("✅ SpaceController 생성됨!");
    }*/

    private final SpaceService spaceService;

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

    @PutMapping("/{spaceId}")
    public ResponseEntity<SpaceResponseDto> updateSpace(@PathVariable Long spaceId, @Validated @RequestBody SpaceRequestDto spaceRequestDto) {
        return ResponseEntity.ok(spaceService.updateSpace(spaceId, spaceRequestDto));
    }

    @DeleteMapping("/{spaceId}")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long spaceId) {
        spaceService.deleteSpace(spaceId);
        return ResponseEntity.ok().build();
    }
}
