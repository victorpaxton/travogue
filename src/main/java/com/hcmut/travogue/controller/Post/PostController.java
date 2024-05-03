package com.hcmut.travogue.controller.Post;

import com.hcmut.travogue.model.dto.Post.PostCommentDTO;
import com.hcmut.travogue.model.dto.Post.PostCreateDTO;
import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.service.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private IPostService postService;

    @PostMapping
    @Operation(summary = "Create new post")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> createPost(Principal principal, @RequestParam(required = false) UUID travelActivityId, @RequestBody PostCreateDTO postCreateDTO) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(postService.createPost(principal, travelActivityId, postCreateDTO))
                .errors(null)
                .build();
    }

    @PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Add image to post")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> addImage(Principal principal, @PathVariable("id") UUID postId, @RequestPart MultipartFile image) throws IOException {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(postService.addImage(principal, postId, image))
                .errors(null)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get posts by user")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> getPostsByUser(@RequestParam UUID userId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(postService.getPostsByUser(userId))
                .errors(null)
                .build();
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Get comments by post")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> getCommentsByPost(@PathVariable("id") UUID postId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(postService.getCommentsByPost(postId))
                .errors(null)
                .build();
    }

    @GetMapping("/{id}/likes")
    @Operation(summary = "Get likes by post")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> getLikesByPost(@PathVariable("id") UUID postId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(postService.getLikesByPost(postId))
                .errors(null)
                .build();
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Add comment to a post")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> addComment(Principal principal, @PathVariable("id") UUID postId, @RequestBody PostCommentDTO postCommentDTO) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(postService.addComment(principal, postId, postCommentDTO))
                .errors(null)
                .build();
    }

    @PostMapping("/{id}/likes")
    @Operation(summary = "Like a post")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> addLike(Principal principal, @PathVariable("id") UUID postId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(postService.addLike(principal, postId))
                .errors(null)
                .build();
    }

    @DeleteMapping("/{id}/likes")
    @Operation(summary = "Unlike a post")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> unLike(Principal principal, @PathVariable("id") UUID postId) {
        postService.unLike(principal, postId);
        return ResponseModel.builder()
                .isSuccess(true)
                .data("Deleted")
                .errors(null)
                .build();
    }

    @GetMapping("/feeds")
    @Operation(summary = "Feed")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_HOST')")
    public ResponseModel<Object> getPostsOfFriends(Principal principal,
                                        @RequestParam int pageNumber,
                                        @RequestParam int pageSize) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(postService.getPostsOfFriends(principal, pageNumber, pageSize))
                .errors(null)
                .build();
    }
}
