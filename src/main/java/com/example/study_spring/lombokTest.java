package com.example.study_spring;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class lombokTest {
    @NonNull
    private String id;
}
