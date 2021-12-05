/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.github.ailtonbsj.internshipmanager.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author WindowsSeven
 */
@SuppressWarnings("serial")
public class ResultSetSQL extends AbstractTableModel {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;

    private boolean connectedToDatabase = false;

    public ResultSetSQL(String url, String username, String password, String query) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        connectedToDatabase = true;

        setQuery(query);

    }

    @Override
    public int getRowCount() {
        if(!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");
        return numberOfRows;
    }

    @Override
    public int getColumnCount() {
        if(!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");
        //determina numero de colunas
        try {
            return metaData.getColumnCount();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }

    @Override
	public String getColumnName(int column) throws IllegalStateException
    {
        if(!connectedToDatabase)
            throw new IllegalStateException("Not Conneced to Database");
        try {
            return metaData.getColumnName(column + 1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return "";
    }

    @Override
    public Object getValueAt(int row, int column) {
        if(!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");
        try {
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return "";
    }

    public void disconnectFromDatabase(){
        if(connectedToDatabase){
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally{
                connectedToDatabase = false;
            }
        }
    }

    public void setQuery(String query) throws SQLException, IllegalStateException{
        if(!connectedToDatabase)
            throw new IllegalStateException("Not Connected to Database");
        resultSet = statement.executeQuery(query);
        metaData = resultSet.getMetaData();
        //determina numero de linha do resultSet
        resultSet.last();
        numberOfRows = resultSet.getRow();

        //notifica a JTable de o modelo foi alterado
        fireTableStructureChanged();
    }

}