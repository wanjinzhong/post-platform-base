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
import java.util.Optional;

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
        headWriteFont.setFontHeightInPoints((short) 12);
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

    public static String getCellStringValue(Row row, String cellIdx) {
        return getCellStringValue(row, convertCellIndex(cellIdx));
    }

    public static String getCellStringValue(Row row, int cellIdx) {
        if (row == null || row.getCell(cellIdx) == null) {
            return null;
        }
        Cell cell = row.getCell(cellIdx);
        return cell == null ?  null : new DataFormatter().formatCellValue(cell);
    }

    public static Double getCellNumberValue(Row row, String cellIdx) {
        return getCellNumberValue(row, convertCellIndex(cellIdx));
    }

    public static Cell getCellAt(Row row, int cellIdx) {
        return row.getCell(cellIdx);
    }

    public static Cell getCellAt(Row row, String cellIdx) {
        return getCellAt(row, convertCellIndex(cellIdx));
    }

    public static Cell getNonNullCellAt(Row row, int cellIdx) {
        Cell cell = row.getCell(cellIdx);
        if (cell == null) {
            cell = row.createCell(cellIdx);
        }
        return cell;
    }

    public static Cell getNonNullCellAt(Row row, String cellIdx) {
        return getNonNullCellAt(row, convertCellIndex(cellIdx));
    }

    public static Double getCellNumberValue(Row row, int cellIdx) {
        if (row == null || row.getCell(cellIdx) == null) {
            return null;
        }
        return Optional.ofNullable(row.getCell(cellIdx)).map(cell -> new DataFormatter().formatCellValue(cell)).map(Double::valueOf).orElse(null);
    }

    public static int convertCellIndex(String cellIndex) {
        cellIndex = cellIndex.toUpperCase();
        int val = 0;
        char[] chars = cellIndex.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            val += charToIntOffset(chars[i]) * Double.valueOf(Math.pow(26, chars.length - i - 1)).intValue();
        }
        return val - 1;
    }

    private static int charToIntOffset(char c) {
        if (c < 65 || c > 90) {
            throw new RuntimeException("Invalid Cell index");
        }
        return c - 'A' + 1;
    }
}
