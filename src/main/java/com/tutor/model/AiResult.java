package com.tutor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiResult {
    private int score;
    private String feedback;
}
