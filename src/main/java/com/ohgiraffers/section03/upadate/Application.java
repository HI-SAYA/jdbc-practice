package com.ohgiraffers.section03.upadate;

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
        System.out.print("변경할 카테고리 코드 입력 : ");
        int cateCode = sc.nextInt();
        sc.nextLine();
        System.out.print("변경할 카테고리 이름 입력 : ");
        String cateName = sc.nextLine();
        System.out.print("변경할 상위 카테고리 코드 입력 : ");
        int cateRefCategoryCode = sc.nextInt();


        CategoryDTO changedCategory = new CategoryDTO();
        changedCategory.setCategoryCode(cateCode);
        changedCategory.setCategoryName(cateName);
        changedCategory.setRefCategoryCode(cateRefCategoryCode);


        Connection con = getConnection();

        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/cate-query.xml"));

            pstmt = con.prepareStatement(prop.getProperty("updateCategory"));

            pstmt.setString(1, changedCategory.getCategoryName());
            pstmt.setInt(2, changedCategory.getRefCategoryCode());
            pstmt.setInt(3, changedCategory.getCategoryCode());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }

        if (result > 0) {
            System.out.println("카테고리 변경이 완료 되었습니다.");
        } else {
            System.out.println("카테고리 변경이 실패하였습니다.");
        }
    }
}
