package com.beour.beourminicrud.space;

import com.beour.beourminicrud.space.dto.SpaceRequestDto;
import com.beour.beourminicrud.space.dto.SpaceResponseDto;
import com.beour.beourminicrud.space.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceController {

    /*    // Spring이 Bean을 등록하는 순간을 로그로 확인해보기
    public SpaceController() {
        System.out.println("✅ SpaceController 생성됨!");
    }*/

    private final SpaceService spaceService;

    @PostMapping
    public ResponseEntity<SpaceResponseDto> createSpace(@Validated @RequestBody SpaceRequestDto spaceRequestDto) {
        SpaceResponseDto spaceResponseDto = spaceService.createSpace(spaceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED) // REST 관례상 201 Created가 더 적절. GET은 200 OK!
                .body(spaceResponseDto);
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<SpaceResponseDto> getSpace(@PathVariable Long spaceId) {
        return ResponseEntity.ok(spaceService.getSpace(spaceId));
    }
}
