package com.project.questapp.controllers;

import com.project.questapp.entities.Comment;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.services.CommentService;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@Transactional
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }
    @GetMapping
    public List<Comment> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return commentService.getAllComments(userId,postId);
    }
    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneComment(commentId);
    }
    @PostMapping
    public Comment createComment(@RequestBody CommentCreateRequest newCommentRequest){
        return commentService.createOneComment(newCommentRequest);
    }
    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){

        commentService.deleteCommentById(commentId);
    }
    @PutMapping("/{commentId}")
    public Comment updateOnePost(@PathVariable Long commentId, @RequestBody CommentUpdateRequest updateComment){
        return commentService.updateOnePostById(commentId, updateComment);
    }
}
