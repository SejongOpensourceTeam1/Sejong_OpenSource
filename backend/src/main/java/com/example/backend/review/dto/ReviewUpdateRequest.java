package com.example.backend.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRequest{
    String content;
    Long rating;
}
