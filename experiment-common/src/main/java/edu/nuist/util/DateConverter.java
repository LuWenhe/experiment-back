package edu.nuist.util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter implements Converter<Date> {

    @Override
    public Class supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    // 将Excel数据转化为Java数据时需要执行的转化操作
    @Override
    public Date convertToJavaData(CellData cellData,
                                  ExcelContentProperty excelContentProperty,
                                  GlobalConfiguration globalConfiguration) throws ParseException {
        CellDataTypeEnum cellDataType = cellData.getType();

        // 如果读取的是数字类型
        if (cellDataType.equals(CellDataTypeEnum.NUMBER)) {
            LocalDate localDate = LocalDate.of(1900, 1, 1);
            //excel 有些奇怪的bug, 导致日期数差2
            localDate = localDate.plusDays(cellData.getNumberValue().longValue() - 2);
            return Date.valueOf(localDate);
        } else if (cellDataType.equals(CellDataTypeEnum.STRING)) {
            String dateString = cellData.getStringValue();
            LocalDate parse = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            return Date.valueOf(parse);
        }

        return null;
    }

    // 将Java数据写入Excel时需要执行的转化操作
    @Override
    public CellData convertToExcelData(Date date,
                                       ExcelContentProperty excelContentProperty,
                                       GlobalConfiguration globalConfiguration) {
        return new CellData<>(date.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }

}
