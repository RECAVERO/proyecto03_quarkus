package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDto {
  private String numberDocument;
  private String dateStart;
  private double share;
  private String datePay;
  private double balanceStart;
  private double amount;
  private double balanceEnd;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
