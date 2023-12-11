package com.neilw.postplatform.base.publish;

import cn.hutool.db.Entity;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.neilw.postplatform.base.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Publisher {
    public void publishString(String content, String fileName) throws IOException {
        if (content == null) {
            publish(new ByteArrayInputStream("".getBytes()), fileName);
        } else {
            publish(new ByteArrayInputStream(content.getBytes()), fileName);
        }
    }

    public void publishString(Collection<String> content, String fileName) throws IOException {
        if (content == null) {
            publish(new ByteArrayInputStream("".getBytes()), fileName);
        } else {
            publish(new ByteArrayInputStream(String.join("\r\n", content).getBytes()), fileName);
        }
    }

    public <T> void publishExcel(Collection<T> data, Class<T> dataClass, String fileName, String sheetName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, dataClass).sheet(sheetName)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(new HorizontalCellStyleStrategy(ExcelUtil.defaultHeaderStyle(), ExcelUtil.defaultContentStyle()))
                .doWrite(data);
        publish(new ByteArrayInputStream(outputStream.toByteArray()), fileName);
    }

    public <T> void publishExcel(Collection<Entity> entities, String fileName, String sheetName) throws IOException {
        Map<String, Integer> columns = getColumns(entities);
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(sheetName);
            buildHeader(columns, sheet);
            buildData(columns, entities, sheet);
            workbook.write(outputStream);
            publish(new ByteArrayInputStream(outputStream.toByteArray()), fileName);
        }
    }

    private void buildData(Map<String, Integer> columns, Collection<Entity> entities, Sheet sheet) {
        AtomicInteger rowIdx = new AtomicInteger(1);
        entities.forEach(e -> {
            Row row = sheet.createRow(rowIdx.getAndIncrement());
            e.forEach((column, value) -> {
                if (columns.containsKey(column)) {
                    row.createCell(columns.get(column)).setCellValue(value == null ? "" : value.toString());
                }
            });
        });
    }

    private void buildHeader(Map<String, Integer> columns, Sheet sheet) {
        Row header = sheet.createRow(0);
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFont(font);
        columns.forEach((name, idx) -> {
            Cell cell = header.createCell(idx);
            cell.setCellValue(name);
            cell.setCellStyle(cellStyle);
        });
    }

    private Map<String, Integer> getColumns(Collection<Entity> entities) {
        Map<String, Integer> columns = new LinkedHashMap<>();
        entities.forEach(e -> {
            List<String> fieldNames = new ArrayList<>(e.getFieldNames());
            for (int i = 0; i < fieldNames.size(); i++) {
                if (!columns.containsKey(fieldNames.get(i))) {
                    columns.put(fieldNames.get(i), i);
                }
            }
        });
        return columns;
    }

    public <T> void publishExcel(Workbook workbook, String fileName) throws IOException {
        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        workbook.write(dstStream);
        byte[] bytes = dstStream.toByteArray();
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        publish(byteStream, fileName);
    }

    public abstract void publish(InputStream inputStream, String fileName) throws IOException;
}
