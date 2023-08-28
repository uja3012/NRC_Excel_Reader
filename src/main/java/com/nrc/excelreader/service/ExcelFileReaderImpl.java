package com.nrc.excelreader.service;

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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Read XLSX sheet from given location & process it, uSed Apache POI java library
 * Date : 27.08.2023
 * Version : 1.0 (Initial Version)
 * @author Ujwala Vanve
 */

public class ExcelFileReaderImpl {

    private final String pathToInputFile;
    private final DataCollection dataCollection;
    private static int rowIndex= -1;
    public Participant currentParticipant;

    public ExcelFileReaderImpl(String pathToInputFile, DataCollection dataCollection) {
        this.pathToInputFile = pathToInputFile;
        this.dataCollection =  dataCollection;
        this.readData();
    }

    private void readData(){
        String fileExtension = FilenameUtils.getExtension(pathToInputFile);

        if(fileExtension.equalsIgnoreCase(String.valueOf(FileExtensionEnum.XLSX))){
            try {
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
                                saveData (rowIndex, cell, dataCollection);
                            }
                        }
                    }
                    rowIndex=-1;
                }
                // upload data on server
                postDataToServer();

            } catch (InvalidFormatException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData(Integer rowIndex, Cell cell, DataCollection dataCollection){

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


    private void postDataToServer(){

        List<Participant> participantsList = dataCollection.getParticipantList();
        JSONArray arrayOfJson = new JSONArray();

        // code to call api
        for(Participant participant : participantsList){

            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject(participant);

            arrayOfJson.put(jsonObject);
        }
        //calling API to upload data
        ApiService.sendingDataToApi(arrayOfJson.toString());
    }

}
