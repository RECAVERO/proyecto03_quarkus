package com.nttdata.infraestructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credit {
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
