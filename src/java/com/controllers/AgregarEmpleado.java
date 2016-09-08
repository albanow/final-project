package com.controllers;

import com.database.DBUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AgregarEmpleado extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String nombre = request.getParameter("nombre");
        System.out.println(nombre);
        String apellido = request.getParameter("apellido");
        System.out.println(apellido);
        String fechaContratacion = request.getParameter("fechaContratacion");
        System.out.println(fechaContratacion);
        String idPuesto = request.getParameter("idPuesto");
        System.out.println(idPuesto);
        String phone = request.getParameter("phone");
        System.out.println(phone);
        String email = request.getParameter("email");
        System.out.println(email);
        String idDepartamento = request.getParameter("idDepartamento");
        System.out.println(idDepartamento);

        Connection conn = null;

        String insertQuery = "INSERT INTO employees (employee_id, first_name, last_name, "
                + "email, phone_numeric, hire_date, job_id, department_id) "
                + "VALUES (?, ?, ?, ?, ?, TO_DATE(?, 'dd/mm/yyyy'), ?, ?)";
        String maxQuery = "SELECT max(employee_id) FROM employees";
        try {

            conn = DBUtil.getProxoolConexion();

            Statement sMaxId = conn.createStatement();
            ResultSet resMaxId = sMaxId.executeQuery(maxQuery);
            int maxId = 0;

            if (resMaxId.next()) {
                do {
                    maxId = Integer.parseInt(resMaxId.getString("max"));
                } while (resMaxId.next());

            }
            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, maxId + 1);
            statement.setString(2, nombre);
            statement.setString(3, apellido);
            statement.setString(4, email);
            statement.setString(5, phone);
            statement.setString(6, fechaContratacion);
            statement.setString(7, idPuesto);
            statement.setInt(8, Integer.parseInt(idDepartamento));
            System.out.println(statement);
            statement.executeUpdate();
            response.sendRedirect("Empleados");

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            DBUtil.cierraConexion(conn);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
