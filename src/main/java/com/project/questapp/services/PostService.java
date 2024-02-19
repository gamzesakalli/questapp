package com.project.questapp.services;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.PostRepository;
import com.project.questapp.requests.PostCreateRequest;
import com.project.questapp.requests.PostUpdateRequest;
import com.project.questapp.responses.LikeResponse;
import com.project.questapp.responses.PostResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private LikeService likeService;
    private UserService userService;


    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService=userService;

    }
    public void setLikeService(LikeService likeService){
        this.likeService=likeService;
    }

    //RequestParam gelen parametreleri parse et ve Optional<Long>userId değişkenine at
    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()){
            list=postRepository.findByUserId(userId.get());
        }else{
            list=postRepository.findAll();
        }
        return list.stream()//listi stream'e çevirir
                .map(p->{
                 List<LikeResponse> likes=  likeService.getAllLikes(Optional.ofNullable(null),Optional.of(p.getId()));
                //stream'deki her p elemanı için yeni PostResponse objesi oluşturur
                return new PostResponse(p,likes);}).collect(Collectors.toList());

    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user=userService.getOneUser(newPostRequest.getUserId());
        if(user==null){
            return null;
        }
        else{
            Post toSave=new Post();
            toSave.setText(newPostRequest.getText());
            toSave.setTitle(newPostRequest.getTitle());
            toSave.setUser(user);
            return postRepository.save(toSave);
        }

    }

    public Post getOnePostById(Long userId) {
        return postRepository.findById(userId).orElse(null);
    }



    public Post updateOnePostById(Long postId, PostUpdateRequest updatePost){
        Optional<Post> post=postRepository.findById(postId);
        if(post.isPresent()){
            Post foundPost = post.get();
            foundPost.setTitle(updatePost.getTitle());
            foundPost.setText(updatePost.getText());
            postRepository.save(foundPost);
            return foundPost;
        }else{
            return null;
        }
    }

    public void deletePostById(Long postId) {
         postRepository.deleteById(postId);
    }
}
