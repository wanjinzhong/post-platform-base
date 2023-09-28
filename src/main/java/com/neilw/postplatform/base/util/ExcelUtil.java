package com.neilw.postplatform.base.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author neilwan
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExcelUtil {
    public static WriteCellStyle defaultHeaderStyle() {
        WriteCellStyle style = new WriteCellStyle();
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setBold(true);
        headWriteFont.setFontHeightInPoints((short)12);
        style.setWriteFont(headWriteFont);
        return style;
    }
    public static WriteCellStyle defaultContentStyle() {
        return new WriteCellStyle();
    }

    public static <T> List<T> read(InputStream inputStream, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        EasyExcel.read(inputStream, clazz, new AnalysisEventListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext context) {
                result.add(data);
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet().doRead();
        return result;
    }

    public static Workbook read(File file) {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (Exception e) {
            try {
                workbook = new HSSFWorkbook(Files.newInputStream(file.toPath()));
            } catch (NotOLE2FileException e1) {
                throw new RuntimeException("Please open the excel file and do \"Save as\". Then re-upload to the tool");
            } catch (Exception e2) {
                throw new RuntimeException("Cannot open excel file ", e2);
            }
        }
        return workbook;
    }
    public static Workbook read(InputStream stream) {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(stream);
        } catch (Exception e) {
            try {
                workbook = new HSSFWorkbook(stream);
            } catch (NotOLE2FileException e1) {
                throw new RuntimeException("Please open the excel file and do \"Save as\". Then re-upload to the tool");
            } catch (Exception e2) {
                throw new RuntimeException("Cannot open excel file ", e2);
            }
        }
        return workbook;
    }
    public static String getCellStringValue(Row row, int cellIdx) {
        if (row == null || row.getCell(cellIdx) == null) {
            return null;
        }
        Cell cell = row.getCell(cellIdx);
//        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    public static Double getCellNumberValue(Row row, int cellIdx) {
        if (row == null || row.getCell(cellIdx) == null) {
            return null;
        }
        Cell cell = row.getCell(cellIdx);
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else {
            String cellValue = cell.getStringCellValue();
            return StringUtils.isBlank(cellValue) ? null : Double.valueOf(cellValue);
        }
    }
}
