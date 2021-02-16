package com.apsout.electronictestimony.api.util.excel;

import com.apsout.electronictestimony.api.entity.jasper.LayoutEntity;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static List<LayoutEntity> read(File file) {
        Sheet sheet = null;
        try (Workbook workbook = new XSSFWorkbook(file);) {
            sheet = workbook.getSheetAt(0);
        } catch (IOException | InvalidFormatException e) {
            logger.error(String.format("Reading excel file of: %s", file.getAbsoluteFile()));
        }
        Iterator<Row> rowIterator = sheet.iterator();
        int numCols = 0;
        List<LayoutEntity> entities = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isFirstRow(row)) {
                numCols = row.getLastCellNum();
            } else {
                List<String> values = new ArrayList<>();
                for (int index = 0; index < numCols; index++) {
                    Cell cell = row.getCell(index);
                    String cellValue = getValueOfCell(cell);
                    values.add(cellValue);
                }
                LayoutEntity layoutEntity = buildEntity(values);
                entities.add(layoutEntity);
            }
        }
        return entities;
    }

    private static String getValueOfCell(Cell cell) {
        if (cell == null) {
            return "-";
        } else if (CellType.NUMERIC == cell.getCellType()) {
            return ((XSSFCell) cell).getRawValue();
        } else {
            return cell.toString();
        }
    }

    private static boolean isFirstRow(Row row) {
        return row.getRowNum() == 0;
    }

    private static LayoutEntity buildEntity(List<String> values) {
        LayoutEntity layoutEntity = new LayoutEntity();
        IntStream.range(0, values.size()).forEach(idx -> {
            String value = values.get(idx);
            setUpValue(layoutEntity, value, idx);
        });
        return layoutEntity;
    }

    private static void setUpValue(LayoutEntity layoutEntity, String value, int index) {
        switch (index) {
            case 0:
                layoutEntity.setF0(value);
                break;
            case 1:
                layoutEntity.setF1(value);
                break;
            case 2:
                layoutEntity.setF2(value);
                break;
            case 3:
                layoutEntity.setF3(value);
                break;
            case 4:
                layoutEntity.setF4(value);
                break;
            case 5:
                layoutEntity.setF5(value);
                break;
            case 6:
                layoutEntity.setF6(value);
                break;
            case 7:
                layoutEntity.setF7(value);
                break;
            case 8:
                layoutEntity.setF8(value);
                break;
            case 9:
                layoutEntity.setF9(value);
                break;
            case 10:
                layoutEntity.setF10(value);
                break;
            case 11:
                layoutEntity.setF11(value);
                break;
            case 12:
                layoutEntity.setF12(value);
                break;
            case 13:
                layoutEntity.setF13(value);
                break;
            case 14:
                layoutEntity.setF14(value);
                break;
            case 15:
                layoutEntity.setF15(value);
                break;
            case 16:
                layoutEntity.setF16(value);
                break;
            case 17:
                layoutEntity.setF17(value);
                break;
            case 18:
                layoutEntity.setF18(value);
                break;
            case 19:
                layoutEntity.setF19(value);
                break;
            case 20:
                layoutEntity.setF20(value);
                break;
        }
    }
}
