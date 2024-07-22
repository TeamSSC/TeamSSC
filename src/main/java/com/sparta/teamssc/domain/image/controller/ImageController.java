package com.sparta.teamssc.domain.image.controller;

import com.sparta.teamssc.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

}
