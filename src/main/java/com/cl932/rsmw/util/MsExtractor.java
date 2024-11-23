package com.cl932.rsmw.util;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class MsExtractor {
    String[] keysPeriod = {"concentration_CO",
            "concentration_CO2",
            "concentration_NO",
            "concentration_NO2",
            "concentration_NOx",
            "concentration_SO2",
            "concentration_CH4",
            "concentration_O2",
            "waste_Gas_Temperature",
            "concentration_dust",
            "emission_CO",
            "emission_CO2",
            "emission_NO",
            "emission_NO2",
            "emission_NOx",
            "emission_SO2",
            "emission_CH4",
            "emission_dust",
            "PDK_CO",
            "PDK_CO2",
            "PDK_NO",
            "PDK_NO2",
            "PDK_NOx",
            "PDK_SO2",
            "PDK_CH4",
            "PDK_dust",
            "PDV_CO",
            "PDV_CO2",
            "PDV_NO",
            "PDV_NO2",
            "PDV_NOx",
            "PDV_SO2",
            "PDV_CH4",
            "PDV_dust",
            "flue_Gas_Temperature",
            "flue_Gas_Pressure",
            "flue_Gas_Speed",
            "flue_Gas_consumptionNU",
            "flue_Gas_consumptionRU",
            "fuel_consumption"};
    String[] keysDay = {
            "concentration_CO",
            "concentration_CO2",
            "concentration_NO",
            "concentration_NO2",
            "concentration_NOx",
            "concentration_SO2",
            "concentration_CH4",
            "concentration_O2",
            "concentration_dust",
            "waste_Gas_Temperature",
            "flue_Gas_consumptionRU"
    };
    final
    DataSource ms;
    DataSource pg;
    final String yesterday = "WHERE CAST(Date AS DATE) = CAST(DATEADD(day, -100, GETDATE()) AS DATE);";
    Connection pgConnection;
    Connection msConnection;

    public MsExtractor(@Qualifier("msDataSource") DataSource ms, @Qualifier("defaultDataSource") DataSource pg) {
        this.ms = ms;
        this.pg = pg;
        try {
            this.pgConnection = pg.getConnection();
            this.msConnection = ms.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Map<String, String> getStringStringMap(Map<String, String> res, Connection connection, String query) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(query);
        pr.execute();
        ResultSet result = pr.getResultSet();
        ResultSetMetaData rsmd = result.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (result.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                res.put(rsmd.getColumnName(i), result.getString(i));
            }
        }

        return res;
    }

    private Map<String, String> getStringStringMapDouble(Map<String, String> res, Connection connection, String query) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(query);
        pr.execute();
        ResultSet result = pr.getResultSet();
        ResultSetMetaData rsmd = result.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (result.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                res.put(rsmd.getColumnName(i), String.valueOf(result.getDouble(i)));
            }
        }

        return res;
    }

    public Map<String, String> extractFromReports() throws SQLException {
        String query = "select * from dbo.\"Current\"";
        Connection connection = msConnection;
        Map<String, String> res = new HashMap<>();
        return getStringStringMap(res, connection, query);
    }

    public Map<String, String> extractFromCurrent() throws SQLException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(keysPeriod).forEach(el -> sb.append("avg(cast(replace(" + el + ", ',', '.') as DECIMAL(9,2))) as " + el + ","));
        String str = sb.toString();
        String result = str.substring(0, str.length() - 1);
        String query = "select " + result + " from current_value";
        Connection connection = pgConnection;
        Map<String, String> res = new HashMap<>();
        return getStringStringMapDouble(res, connection, query);
    }

    public Map<String, String> extractFromPeriod() throws SQLException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(keysDay).forEach(el -> sb.append("avg(cast(replace(" + el + ", ',', '.') as DECIMAL(9,2))) as " + el + ","));
        String str = sb.toString();

        String result = str.substring(0, str.length() - 1);
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String timestamp = df.format(new Date(calendar.getTimeInMillis()));
        String query = "select " + result + " from period_record where timestamp like '" + timestamp + "%'";
        Connection connection = pgConnection;
        Map<String, String> res = new HashMap<>();
        return getStringStringMapDouble(res, connection, query);
    }

    public void clearOldPeriod() throws SQLException {
        Connection connection = pgConnection;
        String query = "delete from period_record WHERE convert(DATE, timestamp,104) < DATEADD(day, -7, GETDATE())";
        PreparedStatement pr = connection.prepareStatement(query);
        pr.execute();
    }
}
