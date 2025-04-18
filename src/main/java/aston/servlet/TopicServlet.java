package aston.servlet;

import aston.controller.TopicController;
import aston.dto.TopicDto;
import aston.repository.TopicRepository;
import aston.service.impl.TopicServiceImpl;
import aston.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/topics")
public class TopicServlet extends HttpServlet {

    private TopicController topicController;

    public TopicServlet() {
    }

    @Override
    public void init() {
        this.topicController = new TopicController(new TopicServiceImpl(new TopicRepository()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                TopicDto topic = topicController.getTopicById(id);
                JsonUtil.write(resp, topic);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат ID темы");
                return;
            }
        } else {
            List<TopicDto> topics = topicController.getAllTopics();
            JsonUtil.write(resp, topics);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TopicDto topicDto = JsonUtil.read(req, TopicDto.class);
        TopicDto createdTopic = topicController.createTopic(topicDto);
        JsonUtil.write(resp, createdTopic);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID темы не указан");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            boolean success = topicController.deleteTopic(id);

            if (success) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Тема не найдена");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат ID темы");
        }
    }
}
