package aston.repository;

import aston.entity.Topic;
import aston.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TopicRepository {

    public List<Topic> findAll() {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT * FROM topics";

        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getLong("id"));
                topic.setTitle(rs.getString("title"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topics;
    }

    public Optional<Topic> findById(Long id) {
        String sql = "SELECT * FROM topics WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setId(rs.getLong("id"));
                    topic.setTitle(rs.getString("title"));
                    return Optional.of(topic);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Topic save(Topic topic) {
        String sql = topic.getId() == null ? "INSERT INTO topics (title) VALUES (?)"
                : "UPDATE topics SET title = ? WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, topic.getTitle());

            if (topic.getId() != null) {
                stmt.setLong(2, topic.getId());
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0 && topic.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        topic.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topic;
    }

    public void delete(Topic topic) {
        String sql = "DELETE FROM topics WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, topic.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}