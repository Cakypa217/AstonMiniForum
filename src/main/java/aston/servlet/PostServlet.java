package aston.servlet;

import aston.controller.PostController;
import aston.dto.PostDto;
import aston.repository.PostRepository;
import aston.service.impl.PostServiceImpl;
import aston.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/posts")
public class PostServlet extends HttpServlet {

    private PostController postController;

    public PostServlet() {
    }

    @Override
    public void init() {
        this.postController = new PostController(new PostServiceImpl(new PostRepository()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        String postId = req.getParameter("id");

        if (postId != null) {
            try {
                Long postIdLong = Long.parseLong(postId);
                PostDto post = postController.getPostById(postIdLong);
                JsonUtil.write(resp, post);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid post ID format");
            }
        } else if (topicId != null) {
            try {
                Long topicIdLong = Long.parseLong(topicId);
                List<PostDto> posts = postController.getPostsByTopicId(topicIdLong);
                JsonUtil.write(resp, posts);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid topic ID format");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Either postId or topicId must be provided");
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostDto postDto = JsonUtil.read(req, PostDto.class);
        PostDto createdPost = postController.createPost(postDto);
        JsonUtil.write(resp, createdPost);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postId = req.getParameter("id");
        try {
            Long postIdLong = Long.parseLong(postId);
            postController.deletePost(postIdLong);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid post ID format");
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postId = req.getParameter("id");
        try {
            Long postIdLong = Long.parseLong(postId);
            PostDto postDto = JsonUtil.read(req, PostDto.class);
            PostDto updated = postController.updatePost(postIdLong, postDto);
            JsonUtil.write(resp, updated);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid post ID format");
        }
    }
}
