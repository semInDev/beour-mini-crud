package com.beour.beourminicrud.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaruUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String clerkId;   // ✅ Clerk이 발급한 user_id

    private String nickname;
    private String nationality;
}
