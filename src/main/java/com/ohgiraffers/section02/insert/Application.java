package com.ohgiraffers.section02.insert;

import com.ohgiraffers.model.dto.CategoryDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("카테고리 이름 입력 : ");
        String categoryName = sc.nextLine();
        System.out.print("상위 카테고리 코드 입력 : ");
        int refCategoryCode = sc.nextInt();

        CategoryDTO newCate = new CategoryDTO();
        newCate.setCategoryName(categoryName);
        newCate.setRefCategoryCode(refCategoryCode);

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/cate-query.xml"));
            String query = prop.getProperty("insertCategory");

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, newCate.getCategoryName());
            pstmt.setInt(2, newCate.getRefCategoryCode());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }

        if(result > 0) {
            System.out.println("카테고리 등록이 완료 되었습니다.");
        } else {
            System.out.println("카테고리 등록이 실패 하였습니다.");
        }

    }


}
