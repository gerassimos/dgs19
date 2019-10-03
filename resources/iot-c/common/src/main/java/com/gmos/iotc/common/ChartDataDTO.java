package com.gmos.iotc.common;

import java.util.List;

public class ChartDataDTO {

  // var data = google.visualization.arrayToDataTable([
  //     ['Year', 'Sales', 'Expenses'],
  //     ['2004',  1000,      400],
  //     ['2005',  1170,      460],
  //     ['2006',  660,       1120],
  //     ['2007',  1030,      540]
  // ]);

  private String year;
  private Integer sales;
  private Integer expenses;

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public Integer getSales() {
    return sales;
  }

  public void setSales(Integer sales) {
    this.sales = sales;
  }

  public Integer getExpenses() {
    return expenses;
  }

  public void setExpenses(Integer expenses) {
    this.expenses = expenses;
  }

  @Override
  public String toString() {
    return "ChartDataDTO{" +
            "year='" + year + '\'' +
            ", sales=" + sales +
            ", expenses=" + expenses +
            '}';
  }
}
