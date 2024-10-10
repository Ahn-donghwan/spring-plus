package org.example.expert.domain.image.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.image.service.ProfileImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileImageController {
    private final ProfileImageService profileImageService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("image") MultipartFile image) {
        String imageUrl = profileImageService.upload(image);
        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProfileImage(@RequestParam("imageUrl") String imageUrl) {
        profileImageService.deleteImageFromS3(imageUrl);
        return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
    }
}
