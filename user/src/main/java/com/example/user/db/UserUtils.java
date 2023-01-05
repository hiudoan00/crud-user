package com.example.user.db;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserUtils {
    static String DB_INFO = "jdbc:postgresql://localhost:5432/hieudb";
    static String USER_NAME = "postgres";
    static String PASS = "Duchieu1908";

//    static String DB_INFO = "jdbc:mysql://localhost:3306/t3h_2208";
//    static String USER_NAME = "root";
//    static String PASS = "Duchieu1908";

    public static boolean checkExistByEmail(String email) throws SQLException {
        boolean result = false;
        List<Book> bookList = new ArrayList<>();
        // Bước 1: Tạo kết nối đến csdl
        Connection conn = DriverManager.getConnection(DB_INFO, USER_NAME, PASS);
        // Bước 2: Tạo đối Statement từ đối tượng Connection Để viết câu lệnh truy vấn
        // Câu lện tương tác với csdl
        Statement sqlStatement = conn.createStatement();
        String selectSql = "SELECT * FROM tb_user where user_name = '" + email + "'";
        // Bước 3: Thực thi câu lệnh lên sql server -> kết quả trả về ResultSet
        ResultSet ketQua = sqlStatement.executeQuery(selectSql);
        // Bước 4: Xử lý kết quả trả về (hiển thị lên màn hình)
        while (ketQua.next()) {
            result = true;
        }
        // Bước 5: đóng kết nối
        sqlStatement.close();
        conn.close();
        return result;
    }
}
