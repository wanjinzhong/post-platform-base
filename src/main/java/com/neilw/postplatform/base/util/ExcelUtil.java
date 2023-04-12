package com.neilw.postplatform.base.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.InputStream;
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
}
