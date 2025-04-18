package aston.repository;

import aston.entity.Tag;
import aston.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagRepository {

    public List<Tag> findAll() {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT * FROM tags";

        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tag tag = new Tag();
                tag.setId(rs.getLong("id"));
                tag.setName(rs.getString("name"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

    public Optional<Tag> findById(Long id) {
        String sql = "SELECT * FROM tags WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Tag tag = new Tag();
                    tag.setId(rs.getLong("id"));
                    tag.setName(rs.getString("name"));
                    return Optional.of(tag);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Tag save(Tag tag) {
        String sql = tag.getId() == null
                ? "INSERT INTO tags (name) VALUES (?) RETURNING id"
                : "UPDATE tags SET name = ? WHERE id = ?";

        try (Connection conn = DbUtil.getConnection()) {
            if (tag.getId() == null) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, tag.getName());

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            tag.setId(rs.getLong("id"));
                        }
                    }
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, tag.getName());
                    stmt.setLong(2, tag.getId());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }

    public void delete(Tag tag) {
        String sql = "DELETE FROM tags WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, tag.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
