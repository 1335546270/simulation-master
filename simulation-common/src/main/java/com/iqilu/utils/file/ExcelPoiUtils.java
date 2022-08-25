package com.iqilu.utils.file;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * excel处理工具
 *
 * @author zhangyc
 * @date 2021-11-23 9:55
 */
public class ExcelPoiUtils {

    /**
     * 导出excel格式模板
     *
     * @param arr          数据
     * @param headTitle    头标题
     * @param titleNameArr 标记集合 如 姓名, 性别
     * @param fileName     文件名
     * @author zhangyicheng
     * @date 2021/08/26
     */
    public static ResponseEntity<byte[]> excelTemplates(HttpServletResponse response, ArrayList<ArrayList<String>> arr, String headTitle,
                                                        String[] titleNameArr, String fileName) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell one = row.createCell(0);
        one.setCellValue(headTitle);

        // 列大小
        int lineLength = titleNameArr.length;
        // 设置列宽
        for (int i = 0; i < lineLength; i++) {
            sheet.setColumnWidth(i, 100 * 50);
        }

        // 设置合并单元格
        // 参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
        CellRangeAddress region1 = new CellRangeAddress(0, 0, (short) 0, (short) lineLength - 1);
        sheet.addMergedRegion(region1);

        // 设置居中
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        one.setCellStyle(cellStyle);

        font.setFontName("仿宋_GB2312");
        //粗体显示
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //字体大小
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        one.setCellStyle(cellStyle);

        // 第三行, 副标题
        HSSFRow row3 = sheet.createRow(2);
        for (int i = 0; i < lineLength; i++) {
            HSSFCell name = row3.createCell(i);
            name.setCellValue(titleNameArr[i]);
            // 设置自动换行
            cellStyle = workbook.createCellStyle();
            cellStyle.setWrapText(true);
            name.setCellStyle(cellStyle);
        }
        // 数据
        for (int i = 0; i < arr.size(); i++) {
            HSSFRow nextRow = sheet.createRow(i + 3);
            for (int j = 0; j < arr.get(i).size(); j++) {
                HSSFCell cell2 = nextRow.createCell(j);
                cell2.setCellValue(arr.get(i).get(j));
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentDispositionFormData("attachment", fileName + ".xls");
            return new ResponseEntity<>(out.toByteArray(), httpHeaders, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
