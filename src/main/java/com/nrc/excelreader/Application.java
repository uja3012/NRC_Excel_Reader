package com.nrc.excelreader;

import com.nrc.excelreader.service.ProcessExcelFileImpl;
import com.nrc.excelreader.repository.DataCollection;
import com.nrc.excelreader.util.CustomConfig;

public class Application {

    public static void main(String[] args) {

        /* Application reads System property i.e. Provide command as below when running from CLI
        java -Dpath.properties=<fullyQualifiedPathOfPropertyFile> -jar <jarfileName>*/

            new ProcessExcelFileImpl(new DataCollection()).processFile(CustomConfig.properties.getProperty("xlsx.location"));

        }
}
