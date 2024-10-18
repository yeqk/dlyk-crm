package io.github.yeqk97.dlykserver.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtils {

    public static void write(HttpServletResponse response, String result) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        try(PrintWriter writer = response.getWriter()) {
            writer.write(result);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
