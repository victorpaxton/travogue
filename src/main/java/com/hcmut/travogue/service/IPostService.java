package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Post.PostCommentDTO;
import com.hcmut.travogue.model.dto.Post.PostCreateDTO;
import com.hcmut.travogue.model.dto.Post.PostResponseDTO;
import com.hcmut.travogue.model.dto.Response.PageResponse;
import com.hcmut.travogue.model.entity.Post.Post;
import com.hcmut.travogue.model.entity.Post.PostComment;
import com.hcmut.travogue.model.entity.Post.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IPostService {

    public PostResponseDTO createPost(Principal principal, UUID activityId, PostCreateDTO postCreateDTO);
    public PostResponseDTO addImage(Principal principal, UUID postId, MultipartFile image) throws IOException;
    public List<PostResponseDTO> getPostsByUser(UUID userId);
    public List<PostComment> getCommentsByPost(UUID postId);
    public List<PostLike> getLikesByPost(UUID postId);
    public PostComment addComment(Principal principal, UUID postId, PostCommentDTO postCommentDTO);
    public PostLike addLike(Principal principal, UUID postId);
    public void unLike(Principal principal, UUID postId);
    public PageResponse<PostResponseDTO> getPostsOfFriends(Principal principal, int pageNumber, int pageSize);
}
