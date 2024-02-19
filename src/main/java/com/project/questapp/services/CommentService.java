package com.project.questapp.services;


import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService=userService;
        this.postService=postService;
    }

    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent()&& postId.isEmpty()){
            return commentRepository.findByUserId(userId.get());
        }else if(postId.isPresent()&& userId.isEmpty()){
            return commentRepository.findByPostId(postId.get());
        } else if (userId.isPresent()&&postId.isPresent()){
            return commentRepository.findByPostIdAndUserId(postId.get(), userId.get());
        }else  {
            return commentRepository.findAll();
        }
    }

    public Comment createOneComment(CommentCreateRequest newCommentRequest) {
        User user=userService.getOneUser(newCommentRequest.getUserId());
        Post post= postService.getOnePostById(newCommentRequest.getPostId());
        if(user==null|| post==null){
            return null;
        }
        else{
            Comment toSave=new Comment();
            toSave.setText(newCommentRequest.getText());
            toSave.setPost(post);
            toSave.setUser(user);
            return commentRepository.save(toSave);
        }
    }

    public Comment getOneComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Comment updateOnePostById(Long commentId, CommentUpdateRequest updateComment) {
        Optional<Comment> comment=commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment foundComment = comment.get();
            foundComment.setText(updateComment.getText());
            commentRepository.save(foundComment);
            return foundComment;
        }else{
            return null;
        }
    }
}
