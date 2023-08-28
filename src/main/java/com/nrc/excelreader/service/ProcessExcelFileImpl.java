package com.nrc.excelreader.service;

import com.google.gson.Gson;
import com.nrc.excelreader.enums.FileExtensionEnum;
import com.nrc.excelreader.model.Participant;
import com.nrc.excelreader.repository.DataCollection;
import com.nrc.excelreader.util.Validator;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.IOException;

/**
 * Read XLSX sheet from given location & process it, uSed Apache POI java library
 * Date : 27.08.2023
 * Version : 1.0 (Initial Version)
 * @author Ujwala Vanve
 */

public class ProcessExcelFileImpl {

    private final DataCollection dataCollection;
    private static int rowIndex= -1;
    public Participant currentParticipant;

    public ProcessExcelFileImpl(DataCollection dataCollection) {
        this.dataCollection =  dataCollection;
    }

    public void readData(String pathToInputFile) throws InvalidFormatException, IOException {

            String fileExtension = FilenameUtils.getExtension(pathToInputFile);

            if(!fileExtension.equalsIgnoreCase(String.valueOf(FileExtensionEnum.XLSX))){
                throw new InvalidFormatException("file is not in xslx format.");
            }

            OPCPackage fileOpcPkg = OPCPackage.open(new File(pathToInputFile));

            XSSFWorkbook workbookToRead = new XSSFWorkbook(fileOpcPkg);

            // access each sheet from workbook(xlsx file)
            for(Sheet currentSheet : workbookToRead){

                // read every row from sheet
                for(Row currentRow : currentSheet){
                    rowIndex++;
                    // read cell value from each row
                    for(Cell cell : currentRow) {
                        if (!Validator.isCellEmpty(cell)) {
                            mapDataToObject (rowIndex, cell, dataCollection);
                        }
                    }
                }
                rowIndex=-1;
            }


    }

    public void mapDataToObject(Integer rowIndex, Cell cell, DataCollection dataCollection){

        if (rowIndex==0){
            switch (String.valueOf(cell)) {
                case "name" -> dataCollection.nameColumnIndexList.add(cell.getColumnIndex());
                case "date_of_birth" -> dataCollection.dateOfBirthColumnIndexList.add(cell.getColumnIndex());
                case "address" -> dataCollection.addressColumnIndexList.add(cell.getColumnIndex());
                case "phone_number" -> dataCollection.phoneNumberColumnIndexList.add(cell.getColumnIndex());
            }
        }else {
            if(dataCollection.nameColumnIndexList.contains(cell.getColumnIndex())){
                //Create participant considering name as mandatory field
                currentParticipant= dataCollection.addParticipant(cell.getStringCellValue());
            }else if(dataCollection.dateOfBirthColumnIndexList.contains(cell.getColumnIndex())){
                if((currentParticipant.getDateOfBirth().isEmpty())) // to avoid override of the set value
                    currentParticipant.setDateOfBirth(Validator.setISOFormatDate(cell.toString()));
            }else if(dataCollection.addressColumnIndexList.contains(cell.getColumnIndex())){
                if((currentParticipant.getAddress().isEmpty()))     // to avoid override of the set value
                    currentParticipant.setAddress(cell.getStringCellValue());
            }else if(dataCollection.phoneNumberColumnIndexList.contains(cell.getColumnIndex())){
                if((currentParticipant.getPhoneNumber().isEmpty()))  // to avoid override of the set value
                    currentParticipant.setPhoneNumber(cell.getStringCellValue());
            }

        }

    }


    public void postDataToServer() {
        //calling API to upload data & send json formatted data
        ApiService.sendingDataToApi(new Gson().toJson(dataCollection.getParticipantList()));
    }

    public void processFile(String pathToInputFile) {

        try {
            readData(pathToInputFile);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }

        postDataToServer();
    }


}
