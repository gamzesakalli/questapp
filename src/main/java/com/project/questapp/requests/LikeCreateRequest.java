package com.project.questapp.requests;

import lombok.Data;

@Data
public class LikeCreateRequest {

    Long postId;
    Long userId;
}
