package com.example.ss12_usermanager.controller;

import com.example.ss12_usermanager.model.User;
import com.example.ss12_usermanager.service.IUserDAO;
import com.example.ss12_usermanager.service.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "userServlet", value = "/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    createUser(req, resp);
                    break;
                case "edit":
                    updateUser(req, resp);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");

        User user = new User(id, name, email, country);
        userDAO.updateUser(user);

        RequestDispatcher dispatcher = req.getRequestDispatcher("user/edit.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
     try {
         switch (action) {
             case "create":
                 showCreateUser(req, resp);
                 break;
             case "edit":
                 showUpdateUser(req, resp);
                 break;
             case "delete":
                 deleteUser(req, resp);
                 break;
             case "search":
                 searchCountry(req,resp);
                 break;
             case "sort":
                 sortByName(req,resp);
                 break;
             default:
                 listUser(req, resp);
                 break;
         }
     }catch (SQLException e) {
         e.printStackTrace();
     }
    }

    private void sortByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> sortAllUsers = userDAO.sortAllUsers();

        req.setAttribute("sortAllUsers", sortAllUsers);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(req,resp);
    }

    private void searchCountry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String country = req.getParameter("search");
        List<User> listSearch = userDAO.searCountry(country);

        req.setAttribute("listSearch", listSearch);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/search.jsp");
        dispatcher.forward(req,resp);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        userDAO.deleteUser(id);

        List<User> listUser = userDAO.selectAllUsers();
        req.setAttribute("listUser", listUser);
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(req,resp);
    }

    private void showUpdateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User existingUser = userDAO.selectUser(id);

        req.setAttribute("user", existingUser);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/edit.jsp");
        dispatcher.forward(req, resp);
    }

    private void showCreateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/create.jsp");
        dispatcher.forward(req, resp);
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");

        User user = new User(name, email, country);
        userDAO.insertUser(user);

        RequestDispatcher dispatcher = req.getRequestDispatcher("user/create.jsp");
        dispatcher.forward(req, resp);
    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> listUser = userDAO.selectAllUsers();
        req.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(req, resp);
    }


}