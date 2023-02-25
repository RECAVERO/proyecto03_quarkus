package com.nttdata.infraestructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
  private int typeCard;
  private String numberCard;
  private String numberAccountAssociated;
  private String pin;
  private String dueDate;
  private String codeValidation;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
