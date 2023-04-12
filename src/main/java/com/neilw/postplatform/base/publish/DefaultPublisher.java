package com.neilw.postplatform.base.publish;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DefaultPublisher extends Publisher {

    @Override
    public void publish(InputStream inputStream, String fileName) throws IOException {
        FileUtil.writeFromStream(inputStream, new File("outputs", fileName));
        inputStream.close();
    }
}
