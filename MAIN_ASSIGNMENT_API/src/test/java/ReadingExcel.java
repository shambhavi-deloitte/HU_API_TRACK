import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;


public class ReadingExcel {

    static ArrayList<JSONObject> JsonArray = new ArrayList<>();
@Test
    public static Iterator<Row> rowIterator(String path) throws IOException {

        File Fi= new File(path);
        FileInputStream fiS = new FileInputStream(Fi);
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fiS);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);

        Iterator<Row> rowIterator = mySheet.iterator();

        return rowIterator;

    }

    public ArrayList<JSONObject> user_details(String path) throws IOException {

        Iterator<Row> rowIterator = rowIterator(path);

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            ArrayList<String> listOfStrings = new ArrayList<>();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        listOfStrings.add(Integer.toString((int) cell.getNumericCellValue()));
                        break;
                    case Cell.CELL_TYPE_STRING:
                        listOfStrings.add(cell.getStringCellValue());
                        break;
                }

            }

            JSONObject obj = new JSONObject();
            obj.put("name", listOfStrings.get(0));
            obj.put("email", listOfStrings.get(1));
            obj.put("password", Integer.parseInt(listOfStrings.get(2)));
            obj.put("age", Integer.parseInt(listOfStrings.get(3)));

            JsonArray.add(obj);
        }

        return JsonArray;
    }


    public String[] login_details(String path) throws IOException {

        Iterator<Row> rowIterator = rowIterator(path);
        String[] arr = new String[2];

        ArrayList<String> listOfStrings = null;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            listOfStrings = new ArrayList<>();

            listOfStrings.add(row.getCell(0).toString());
            listOfStrings.add(Integer.toString((int) row.getCell(1).getNumericCellValue()));

        }

        System.out.println(listOfStrings);

        arr[0] = listOfStrings.get(0);
        arr[1] = listOfStrings.get(1);

        return arr;
    }


    public static ArrayList<JSONObject> add_task(String path) throws IOException {

        Iterator<Row> rowIterator = rowIterator(path);

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            ArrayList<String> listOfStrings = new ArrayList<>();

            listOfStrings.add(row.getCell(0).toString());

            JSONObject obj = new JSONObject();
            obj.put("description", listOfStrings.get(0));

            JsonArray.add(obj);
        }
        return JsonArray;
    }


}