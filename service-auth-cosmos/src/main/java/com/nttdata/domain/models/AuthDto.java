package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
  private String numberCard;
  private int password;
  private String numberTelephone;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
