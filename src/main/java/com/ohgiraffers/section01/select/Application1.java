package com.ohgiraffers.section01.select;

import com.ohgiraffers.model.dto.CategoryDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

// TBL_CATEGORY 테이블에 있는 모든 행 조회 =>
// List<CategoryDTO> 타입의 객체에 담고 전체 출력 확인
public class Application1 {
    public static void main(String[] args) {
        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;

        CategoryDTO row = null;

        List<CategoryDTO> cateList = null;

        String query = "SELECT * FROM TBL_CATEGORY";

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

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
            close(stmt);
            close(con);
        }

        for(CategoryDTO cate : cateList) {
            System.out.println(cate);
        }
    }

}
