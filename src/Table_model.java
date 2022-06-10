import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class Table_model extends AbstractTableModel{

    private static final long serialVersionUID = 1L;

    public int columnCount = 6; //Кол-во столбцов
    private ArrayList<String []> dataArrayList; //Сюда будет собирать инфу из таблицы

    public Table_model() {
        dataArrayList = new ArrayList<String []>();
        for(int i = 0; i < dataArrayList.size(); i++) {
            dataArrayList.add(new String[getColumnCount()]);
        }
    }

    public void get_row_sel_del(int row_sel) {
        if(dataArrayList.size() > row_sel) dataArrayList.remove(row_sel);
    }

    public void get_row_sel_change(int row_sel, String []row) {
        dataArrayList.set(row_sel, row);
    }

    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return dataArrayList.size();
    }

    @Override
    public String getColumnName(int columnIndex) { //Заголовки таблицы
        if(columnCount == 6) {
            switch(columnIndex) {
                case 0: return "ФИО";
                case 1: return "Паспорт";
                case 2: return "Гос номер";
                case 3: return "Марка машины";
                case 4: return "Дата последнего тех осмотра";
                case 5 : return "количество штрафов";
            }
            return "";
        }
        else if(columnCount == 4) {
            switch(columnIndex) {
                case 0: return "ФИО";
                case 1: return "Гос номер";
                case 2: return "тип нарушения";
                case 3: return "Дата нарушения";

            }
            return "";
        }
        return "";
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        String []rows = dataArrayList.get(rowIndex);
        return rows[columnIndex];
    }

    public void addDate(String []row) { //Метод, который будет добавлять данные в таблицу
        String[] rowTable = new String[getColumnCount()];
        rowTable = row;
        dataArrayList.add(rowTable);
    }

}