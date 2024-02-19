package com.project.questapp.services;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.LikeRepository;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(UserService userService, PostService postService, LikeRepository likeRepository) {
        this.userService = userService;
        this.postService=postService;
        this.likeRepository=likeRepository;
    }
    public List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent()&& postId.isEmpty()){
            list= likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()&& userId.isEmpty()){
            list= likeRepository.findByPostId(postId.get());
        } else if (userId.isPresent()&&postId.isPresent()){
            list= likeRepository.findByPostIdAndUserId(postId.get(), userId.get());
        }else  {
            list= likeRepository.findAll();
        }
        return list.stream().map(like->new LikeResponse(like)).collect(Collectors.toList());
    }
    public Like getOneLike(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest newLikeRequest) {
        User user=userService.getOneUser(newLikeRequest.getUserId());
        Post post= postService.getOnePostById(newLikeRequest.getPostId());
        if(user==null|| post==null){
            return null;
        }
        else{
            Like toSave=new Like();
            toSave.setPost(post);
            toSave.setUser(user);
            return likeRepository.save(toSave);
        }
    }

    public void deleteLikeById(Long likeId) {
         likeRepository.deleteById(likeId);
    }
}
