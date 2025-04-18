package aston.servlet;

import aston.controller.UserController;
import aston.dto.UserDto;
import aston.repository.UserRepository;
import aston.service.impl.UserServiceImpl;
import aston.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/users")
public class UserServlet extends HttpServlet {

    private UserController userController;

    public UserServlet() {
    }

    @Override
    public void init() {
        this.userController = new UserController(new UserServiceImpl(new UserRepository()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");

        try {
            if (userId != null) {
                Long userIdLong = Long.parseLong(userId);
                UserDto user = userController.getUser(userIdLong);
                JsonUtil.write(resp, user);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID must be provided");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = JsonUtil.read(req, UserDto.class);
        UserDto createdUser = userController.createUser(userDto);
        JsonUtil.write(resp, createdUser);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        try {
            if (userId != null) {
                Long userIdLong = Long.parseLong(userId);
                UserDto userDto = JsonUtil.read(req, UserDto.class);
                UserDto updatedUser = userController.updateUser(userIdLong, userDto);
                JsonUtil.write(resp, updatedUser);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID must be provided");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        try {
            if (userId != null) {
                Long userIdLong = Long.parseLong(userId);
                userController.deleteUser(userIdLong);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID must be provided");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
        }
    }
}