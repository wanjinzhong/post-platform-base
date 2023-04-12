package com.neilw.postplatform.base.publish;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.neilw.postplatform.base.util.ExcelUtil;

import java.io.*;
import java.util.List;

public abstract class Publisher {
    public void publishString(String content, String fileName) throws IOException {
        if (content == null) {
            publish(new ByteArrayInputStream("".getBytes()), fileName);
        } else {
            publish(new ByteArrayInputStream(content.getBytes()), fileName);
        }
    }

    public void publishString(List<String> content, String fileName) throws IOException {
        if (content == null) {
            publish(new ByteArrayInputStream("".getBytes()), fileName);
        } else {
            publish(new ByteArrayInputStream(String.join("\r\n", content).getBytes()), fileName);
        }
    }

    public <T> void publishExcel(List<T> data, Class<T> dataClass, String fileName, String sheetName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, dataClass).sheet(sheetName)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(new HorizontalCellStyleStrategy(ExcelUtil.defaultHeaderStyle(), ExcelUtil.defaultContentStyle()))
                .doWrite(data);
        publish(new ByteArrayInputStream(outputStream.toByteArray()), fileName);
    }

    public abstract void publish(InputStream inputStream, String fileName) throws IOException;
}
