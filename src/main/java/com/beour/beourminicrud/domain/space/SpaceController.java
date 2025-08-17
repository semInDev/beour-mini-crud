package com.beour.beourminicrud.domain.space;

import com.beour.beourminicrud.domain.space.dto.SpaceRequestDto;
import com.beour.beourminicrud.domain.space.dto.SpaceResponseDto;
import com.beour.beourminicrud.domain.space.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceController {
    private final SpaceService spaceService;

    @PostMapping
    public ResponseEntity<SpaceResponseDto> createSpace(@RequestBody SpaceRequestDto spaceRequestDto) {
        return ResponseEntity.ok(spaceService.createSpace(spaceRequestDto));
    }

    @GetMapping
    public ResponseEntity<SpaceResponseDto> getSpace(@RequestParam Long spaceId) {
        return ResponseEntity.ok(spaceService.getSpace(spaceId));
    }
}
