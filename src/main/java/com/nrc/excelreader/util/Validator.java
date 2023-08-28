package com.nrc.excelreader.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validator {


    public static boolean isCellEmpty(final Cell cell) {
        if (cell == null) {
            return true;
        }
        if (cell.getCellType() == CellType.BLANK) {
            return true;
        }
        return cell.getCellType() == CellType.STRING && cell.getStringCellValue().trim().isEmpty();
    }


    public static String setISOFormatDate(String date){

        String datePattern="dd-MMM-yyyy";

        if(date.contains(".") && date.length()==8){
            datePattern="MM.dd.yy";
        }else if(date.contains("/") && date.length()==8){
            datePattern="MM/dd/yy";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate localDate = LocalDate.parse(date, formatter);

        date = DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);

        return date;
    }


}
