package com.nrc.excelreader.service;

import com.nrc.excelreader.repository.DataCollection;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ProcessExcelFileImplTest {

   private static final String testFilePath = "src\\test\\resources\\project_participants.xlsx";

   private static final DataCollection dataCollectionTest = new DataCollection();


    ProcessExcelFileImpl processExcelFile = new ProcessExcelFileImpl(dataCollectionTest);

    @Test()
    public void verify_invalid_xlsx_sheet_input(){
        Assertions.assertThrows(InvalidOperationException.class, () -> processExcelFile.readData("ABC.xlsx"));
        Assertions.assertThrows(InvalidFormatException.class, () -> processExcelFile.readData("ABC.xls"));
    }

    @Test()
    public void verify_mapping_of_DataToObject() throws IOException, InvalidFormatException {
        processExcelFile.readData(testFilePath);
        Assertions.assertEquals(dataCollectionTest.nameColumnIndexList.size(),3);
        Assertions.assertEquals(dataCollectionTest.phoneNumberColumnIndexList.size(),3);
        Assertions.assertEquals(dataCollectionTest.dateOfBirthColumnIndexList.size(),2);
        Assertions.assertEquals(dataCollectionTest.getParticipantList().size(), 50);
    }


}
