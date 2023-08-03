package com.ohgiraffers.section01.select;

import com.ohgiraffers.model.dto.CategoryDTO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        CategoryDTO row = null;
        List<CategoryDTO> cateList = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 카테고리 번호 입력 : ");
        String categoryCode = sc.nextLine();

        String query = "SELECT * FROM TBL_CATEGORY WHERE CATEGORY_CODE = ? ";

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, categoryCode);

            rset = pstmt.executeQuery();

            cateList = new ArrayList<>();

            while (rset.next()) {
                row = new CategoryDTO();

                row.setCategoryCode(rset.getInt("CATEGORY_CODE"));
                row.setCategoryName(rset.getString("CATEGORY_NAME"));
                row.setRefCategoryCode(rset.getInt("REF_CATEGORY_CODE"));

                cateList.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }

        for (CategoryDTO cate : cateList) {
            System.out.println(cate);
        }

    }

}
