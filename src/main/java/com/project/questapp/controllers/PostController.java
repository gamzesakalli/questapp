package com.project.questapp.controllers;

import com.project.questapp.entities.Post;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.PostResponse;
import com.project.questapp.services.PostService;

import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@Transactional
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //RequestParam gelen parametreleri parse et ve Optional<Long>userId değişkenine at
    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }
    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createOnePost(newPostRequest);
    }

    @GetMapping("/{postId}")
    public Post getOnePost(@PathVariable Long postId){

        return postService.getOnePostById(postId);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost){
    return postService.updateOnePostById(postId, updatePost);
    }
    @DeleteMapping("/{postId}")
    public void deleteOnePost(@PathVariable Long postId){

        postService.deletePostById(postId);
    }

}
