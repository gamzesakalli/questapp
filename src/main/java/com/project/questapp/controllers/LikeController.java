package com.project.questapp.controllers;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Like;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponse;
import com.project.questapp.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/likes")
@Transactional
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    @GetMapping
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return likeService.getAllLikes(userId,postId);
    }
    @GetMapping("/{likeId}")
    public Like getOneLike(@PathVariable Long likeId){
        return likeService.getOneLike(likeId);
    }
    @PostMapping
    public Like createLike(@RequestBody LikeCreateRequest newLikeRequest){
        return likeService.createOneLike(newLikeRequest);
    }
    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId){

        likeService.deleteLikeById(likeId);
    }

}
