package com.example.tools.importExcel.SQL;

import com.example.tools.utils.ImportExcelUtil;

import java.io.*;
import java.util.List;

/**
 * @author JinXing
 * @date 2018/12/27 18:22
 */
public class ProduceSql {


    public static void main(String[] args) throws Exception {

        String readeFilePath = "E:\\excelTest\\sql\\couponsCode.xls";
        String writeFilePath = "E:\\excelTest\\sql\\couponsCode_code.sql";

        //生产sql
        produceSql(readeFilePath, writeFilePath);

    }

    private static void produceSql(String readeFilePath, String writeFilePath) throws Exception {
        String separator = ",";

        //创建输入流
        FileInputStream fis = new FileInputStream(new File(readeFilePath));
        List<List<Object>> allRows = new ImportExcelUtil().getBankListByExcel(fis, "test.xls", 6);

        //sql拼接
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO TBL_COUPONS(COUPONS_CODE) values");
        for (List<Object> objects : allRows) {
            if (objects == null) {
                continue;
            }

            Object couponsCode = objects.get(1);
            if (couponsCode != null) {
                sqlBuilder.append("( ").append(couponsCode).append(" ) ").append(separator);
            }
        }

        //删除最后一个,
        if (sqlBuilder.toString().endsWith(separator)) {
            sqlBuilder = sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(separator));
        }
        String writeContent = sqlBuilder.toString();
        System.out.println(writeContent);

        //sql写入文件
        outputSql(writeFilePath, writeContent);
    }

    private static void outputSql(String path, String writeContent) throws IOException {
        try {

            File file = new File(path);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(writeContent);
            bw.close();

            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
