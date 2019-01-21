package com.example.tools.importExcel.SQL;

import com.example.tools.utils.ImportExcelUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JinXing
 * @date 2018/12/27 18:22
 */
public class ProduceSql {


    public static void main(String[] args) throws Exception {

        //读取文件的路径
        String readeFilePath = "E:\\excelTest\\sql\\db_aries_questionnaire\\dim_dim_rela_factor.xls";
        //写出文件的路径
        String writeFilePath = "E:\\excelTest\\sql\\db_aries_questionnaire\\dim_dim_rela_factor.sql";
        //表名称
        String tableName="DIM_DIM_RELA_FACTOR";

        //单元格存在的列与列对应的下标 (下标从0开始)
        List<Column> columnEnumList=new ArrayList<>();
        columnEnumList.add(new Column("DIMENSION_ID",Integer.class, 0));
        columnEnumList.add(new Column("FACTOR_ID",Integer.class, 1));


        //虚拟列设置 (单元格只有两列数据,此时需要插入三列数据，第三列数据值可以设置默认值)
        columnEnumList.add(new Column("ACTIVITY_ID", Integer.class, 5,30));

        //生产sql
        produceInsertSql(readeFilePath, writeFilePath,columnEnumList,tableName);

    }





    /**
     *
     * @author JinXing
     * @date 2018/12/29 18:51
     */
    private static void produceInsertSql(String readeFilePath, String writeFilePath, List<Column> columnEnumList, String tableName) throws Exception {
        String separator = ",";

        //创建输入流
        FileInputStream fis = new FileInputStream(new File(readeFilePath));
        List<List<Object>> allRows = new ImportExcelUtil().getBankListByExcel(fis, "test.xls", 6);

        //sql表名与插入的列名拼接
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(tableName);
        sqlBuilder.append("(");
        for (int i = 0; i < columnEnumList.size(); i++) {
            if(i >0){
                sqlBuilder.append(separator);
            }
            sqlBuilder.append(columnEnumList.get(i).columnName);
        }
        sqlBuilder.append(") values");

        for (List<Object> objects : allRows) {
            if (objects == null) {
                continue;
            }

            sqlBuilder.append("( ");
            int startIndex=0;
            for (Column column : columnEnumList) {
                if (column != null) {

                    Object obj = objects.get(column.index);
                    if(obj ==null || obj ==""){
                         obj =column.defaultValue;
                    }

                    if(startIndex !=0){
                        sqlBuilder.append(separator);
                    }

                    if(column.classType == String.class){
                        sqlBuilder.append("'").append(obj).append("'");
                    }else {
                        sqlBuilder.append(obj);
                    }
                    startIndex ++;
                }
            }

            sqlBuilder.append(" ) ").append(separator);
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

    /**
     * 数据表列定义
     * @author JinXing
     * @date 2018/12/29 18:52
     */
    private static class Column {

        /**数据表列名称*/
        private String columnName;
        /**数据表列名称类型*/
        private Class<?> classType;
        /**数据表列名称对应的单元格下标*/
        private Integer index;

        /**
         * 列的默认值
         * 使用场景：表格只有两列数据，想要设置三列数据可以使用
         */
        private Object defaultValue;

        Column(String columnName, Class<?> classType, Integer index) {
            this.columnName = columnName;
            this.classType = classType;
            this.index = index;
        }

        Column(String columnName, Class<?> classType, Integer index, Object defaultValue) {
            this.columnName = columnName;
            this.classType = classType;
            this.index = index;
            this.defaultValue = defaultValue;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getColumnName() {
            return columnName;
        }

        public Integer getIndex() {
            return index;
        }

        public Class<?> getClassType() {
            return classType;
        }
    }

}
