package com.project.questapp.requests;

import lombok.Data;

@Data
public class PostCreateRequest {

String text;
String title;
Long userId;
}
