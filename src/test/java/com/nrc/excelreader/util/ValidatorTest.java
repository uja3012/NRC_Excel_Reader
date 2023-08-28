package com.nrc.excelreader.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidatorTest {


    @Test()
    public void verify_iso_date_format(){

        Assertions.assertEquals(Validator.setISOFormatDate("01-Jan-2023"),"2023-01-01");

        Assertions.assertEquals(Validator.setISOFormatDate("01.01.23"),"2023-01-01");

        Assertions.assertEquals(Validator.setISOFormatDate("01/01/23"),"2023-01-01");

    }


}
