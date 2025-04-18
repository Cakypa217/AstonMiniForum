package aston.servlet;

import aston.controller.TagController;
import aston.dto.TagDto;
import aston.repository.TagRepository;
import aston.service.impl.TagServiceImpl;
import aston.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/tags")
public class TagServlet extends HttpServlet {

    private TagController tagController;

    public TagServlet() {
    }

    @Override
    public void init() {
        System.out.println("TagServlet инициализирован!");
        this.tagController = new TagController(new TagServiceImpl(new TagRepository()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TagDto tagDto = JsonUtil.read(req, TagDto.class);
        TagDto createdTag = tagController.createTag(tagDto);
        JsonUtil.write(resp, createdTag);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tagIdStr = req.getParameter("id");
        Long tagId = Long.parseLong(tagIdStr);
        tagController.deleteTag(tagId);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TagDto> tags = tagController.getAllTags();
        JsonUtil.write(resp, tags);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
