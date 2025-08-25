package com.beour.beourminicrud.domain.space.repository;

import com.beour.beourminicrud.domain.space.enums.SpaceCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.beour.beourminicrud.domain.space.entity.Space;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // SpaceRepositoryTest가 스프링 부트의 테스트 클래스라고 알려줌
class SpaceRepositoryTest {

    @Autowired
    SpaceRepository spaceRepository;

    @AfterEach
    void tearDown() {
        spaceRepository.deleteAll();
    }

    @Test
    @DisplayName("findAll() 테스트")
    void testJpa1(){
        Space space1 = new Space();
        space1.builder()
                .name("test1")
                .spaceCategory(SpaceCategory.CAFE)
                .address("address1")
                .description("description1")
                .build();
        spaceRepository.save(space1);

        Space space2 = new Space();
        space2.builder()
                .name("test2")
                .spaceCategory(SpaceCategory.CAFE)
                .address("address2")
                .description("description2")
                .build();
        spaceRepository.save(space2);

        List<Space> all = spaceRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("findById() 테스트")
    void testJpa2(){
        Space space1 = new Space();
        space1.builder()
                .name("test1")
                .spaceCategory(SpaceCategory.CAFE)
                .address("address1")
                .description("description1")
                .build();
        spaceRepository.save(space1);

        Optional<Space> os = spaceRepository.findById(space1.getId());
        if(os.isPresent()){
            assertEquals(space1.getName(), os.get().getName());
        }
    }

    @Test
    @DisplayName("findByName() 테스트")
    void testJpa3(){
        Space space1 = new Space();
        space1.builder()
                .name("test1")
                .spaceCategory(SpaceCategory.CAFE)
                .address("address1")
                .description("description1")
                .build();
        spaceRepository.save(space1);

        Optional<Space> os = spaceRepository.findByName(space1.getName());
        if(os.isPresent()){
            assertEquals(space1.getId(), os.get().getId());
        }
    }

    @Test
    @DisplayName("findByNameAndSpaceCategory() 테스트")
    void testJpa4(){
        Space space1 = new Space();
        space1.builder()
                .name("test1")
                .spaceCategory(SpaceCategory.CAFE)
                .address("address1")
                .description("description1")
                .build();
        spaceRepository.save(space1);

        Optional<Space> os = spaceRepository.findByNameAndSpaceCategory(space1.getName(), space1.getSpaceCategory());
        if(os.isPresent()){
            assertEquals(space1.getId(), os.get().getId());
        }
    }

}
