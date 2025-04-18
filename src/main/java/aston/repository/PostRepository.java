package aston.repository;

import aston.entity.Post;
import aston.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepository {

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts";

        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setContent(rs.getString("content"));
                post.setTopicId(rs.getLong("topic_id"));
                post.setUserId(rs.getLong("user_id"));
                post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Post post = new Post();
                    post.setId(rs.getLong("id"));
                    post.setContent(rs.getString("content"));
                    post.setTopicId(rs.getLong("topic_id"));
                    post.setUserId(rs.getLong("user_id"));
                    post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return Optional.of(post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Post> findByTopicId(Long topicId) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts WHERE topic_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, topicId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post();
                    post.setId(rs.getLong("id"));
                    post.setContent(rs.getString("content"));
                    post.setTopicId(rs.getLong("topic_id"));
                    post.setUserId(rs.getLong("user_id"));
                    post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public Post save(Post post) {
        String sql = post.getId() == null
                ? "INSERT INTO posts (content, topic_id, user_id, created_at) VALUES (?, ?, ?, ?)"
                : "UPDATE posts SET content = ?, topic_id = ?, user_id = ? WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, post.getContent());
            stmt.setLong(2, post.getTopicId());
            stmt.setLong(3, post.getUserId());
            stmt.setTimestamp(4, Timestamp.valueOf(post.getCreatedAt()));

            if (post.getId() != null) {
                stmt.setLong(5, post.getId());
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0 && post.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        post.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public void delete(Post post) {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, post.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}