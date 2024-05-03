package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.file.CloudinaryService;
import com.hcmut.travogue.model.dto.Post.PostCommentDTO;
import com.hcmut.travogue.model.dto.Post.PostCreateDTO;
import com.hcmut.travogue.model.dto.Post.PostResponseDTO;
import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.dto.User.UserShortProfileDTO;
import com.hcmut.travogue.model.entity.Post.Post;
import com.hcmut.travogue.model.entity.Post.PostComment;
import com.hcmut.travogue.model.entity.Post.PostLike;
import com.hcmut.travogue.model.entity.Post.PostUserTagged;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.Post.PostCommentRepository;
import com.hcmut.travogue.repository.Post.PostLikeRepository;
import com.hcmut.travogue.repository.Post.PostRepository;
import com.hcmut.travogue.repository.Post.PostUserTaggedRepository;
import com.hcmut.travogue.repository.TravelActivity.TravelActivityRepository;
import com.hcmut.travogue.repository.UserFollowRepository;
import com.hcmut.travogue.repository.UserRepository;
import com.hcmut.travogue.service.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.loading.PrivateClassLoader;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostUserTaggedRepository postUserTaggedRepository;

    @Autowired
    private TravelActivityRepository travelActivityRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostResponseDTO createPost(Principal principal, UUID activityId, PostCreateDTO postCreateDTO) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        TravelActivity activity = null;
        if (activityId != null)
            activity = travelActivityRepository.findById(activityId).orElseThrow();

        Post newPost = postRepository.save(
                Post.builder()
                .caption(postCreateDTO.getCaption())
                .user(user)
                .images("")
                .travelActivity(activity)
                .build()
        );

        List<PostUserTagged> userTaggedList = new ArrayList<>();
        postCreateDTO.getUsersTagged()
                .forEach(userId -> {
                    User userTagged = userRepository.findById(userId).orElseThrow();
                    userTaggedList.add(postUserTaggedRepository.save(
                            PostUserTagged.builder()
                                    .user(userTagged)
                                    .post(newPost)
                                    .build()
                    ));
                });

        PostResponseDTO res = modelMapper.map(newPost, PostResponseDTO.class);
        res.setNumOfLikes(0);
        res.setNumOfComments(0);
        res.setLiked(false);
        res.setLatestComment(null);
        res.setTaggedList(userTaggedList);
        return res;
    }

    @Override
    public PostResponseDTO addImage(Principal principal, UUID postId, MultipartFile image) throws IOException {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        Post post = postRepository.findById(postId).orElseThrow();
        String cur = post.getImages();
        if (Objects.equals(cur, ""))
            post.setImages(cloudinaryService.uploadFile("travel_activity", image));
        else
            post.setImages(cur + ";" + cloudinaryService.uploadFile("travel_activity", image));
        PostResponseDTO p = modelMapper.map(postRepository.save(post), PostResponseDTO.class);
        p.setNumOfComments(post.getPostComments().size());
        p.setNumOfLikes(post.getPostLikes().size());
        p.setLiked(postLikeRepository.existsByUser_IdAndPost_Id(user.getId(), post.getId()));
        p.setLatestComment(postCommentRepository.findFirstByPost_IdOrderByUpdatedAtDesc(post.getId()).orElse(null));
        p.setTaggedList(postUserTaggedRepository.findByPost_Id(post.getId()));
        return p;
    }

    @Override
    public List<PostResponseDTO> getPostsByUser(UUID userId) {
        return postRepository.findAllByUser_IdOrderByUpdatedAtDesc(userId)
                .stream().map(post -> {
                    PostResponseDTO p = modelMapper.map(post, PostResponseDTO.class);
                    p.setNumOfComments(post.getPostComments().size());
                    p.setNumOfLikes(post.getPostLikes().size());
                    p.setLiked(postLikeRepository.existsByUser_IdAndPost_Id(userId, post.getId()));
                    p.setLatestComment(postCommentRepository.findFirstByPost_IdOrderByUpdatedAtDesc(post.getId()).orElse(null));
                    p.setTaggedList(postUserTaggedRepository.findByPost_Id(post.getId()));
                    return p;
                }).toList();
    }

    @Override
    public List<PostComment> getCommentsByPost(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return post.getPostComments()
                .stream().sorted((comment1, comment2) -> comment2.getUpdatedAt().compareTo(comment1.getUpdatedAt()))
                .toList();
    }

    @Override
    public List<PostLike> getLikesByPost(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return post.getPostLikes()
                .stream().sorted((like1, like2) -> like2.getUpdatedAt().compareTo(like1.getUpdatedAt()))
                .toList();
    }

    @Override
    public PostComment addComment(Principal principal, UUID postId, PostCommentDTO postCommentDTO) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        Post post = postRepository.findById(postId).orElseThrow();

        PostComment newComment = PostComment.builder()
                .comment(postCommentDTO.getComment())
                .user(user)
                .post(post)
                .build();

        return postCommentRepository.save(newComment);
    }

    @Override
    public PostLike addLike(Principal principal, UUID postId) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        Post post = postRepository.findById(postId).orElseThrow();

        PostLike newLike = PostLike.builder()
                .user(user)
                .post(post)
                .build();

        return postLikeRepository.save(newLike);
    }

    @Override
    public void unLike(Principal principal, UUID postId) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        Post post = postRepository.findById(postId).orElseThrow();

        PostLike postLike = postLikeRepository.findByUser_IdAndPost_Id(user.getId(), post.getId())
                .orElseThrow();

        postLikeRepository.delete(postLike);
    }

    @Override
    public PageResponse<PostResponseDTO> getPostsOfFriends(Principal principal, int pageNumber, int pageSize) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        Collection<UUID> followingList = new ArrayList<>(userFollowRepository.findAllByFrom_Id(user.getId())
                .stream().map(userFollow -> userFollow.getTo().getId())
                .toList());
        followingList.add(user.getId());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("updatedAt").descending());

        return new PageResponse<>(
                postRepository.findAllByUser_IdIn(followingList, pageable)
                .map(post -> {
                    PostResponseDTO p = modelMapper.map(post, PostResponseDTO.class);
                    p.setNumOfComments(post.getPostComments().size());
                    p.setNumOfLikes(post.getPostLikes().size());
                    p.setLiked(postLikeRepository.existsByUser_IdAndPost_Id(user.getId(), post.getId()));
                    p.setLatestComment(postCommentRepository.findFirstByPost_IdOrderByUpdatedAtDesc(post.getId()).orElse(null));
                    p.setTaggedList(postUserTaggedRepository.findByPost_Id(post.getId()));
                    return p;
                }));
    }
}
