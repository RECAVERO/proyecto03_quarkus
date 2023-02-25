package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.CreditRepository;
import com.nttdata.domain.models.CreditDto;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
@ApplicationScoped
public class CreditRepositoryImpl implements CreditRepository {
  private final ReactiveMongoClient reactiveMongoClient;

  public CreditRepositoryImpl(ReactiveMongoClient reactiveMongoClient) {
    this.reactiveMongoClient = reactiveMongoClient;
  }
  @Override
  public Multi<CreditDto> list() {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("credits");
    ReactiveMongoCollection<Document> collection = database.getCollection("credit");

    return collection.find().map(doc->{
      CreditDto creditDto = new CreditDto();
      creditDto.setNumberDocument(doc.getString("numberDocument"));
      creditDto.setDateStart(doc.getString("dateStart"));
      creditDto.setShare(doc.getDouble("share"));
      creditDto.setDatePay(doc.getString("datePay"));
      creditDto.setBalanceStart(doc.getDouble("balanceStart"));
      creditDto.setAmount(doc.getDouble("amount"));
      creditDto.setBalanceEnd(doc.getDouble("balanceEnd"));
      creditDto.setCreated_datetime(doc.getString("created_datetime"));
      creditDto.setUpdated_datetime(doc.getString("updated_datetime"));
      creditDto.setActive(doc.getString("active"));
      return creditDto;
    }).filter(account->{
      return account.getActive().equals("S");
    });
  }

  @Override
  public Uni<CreditDto> findByNroAccount(CreditDto creditDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("credits");
    ReactiveMongoCollection<Document> collection = database.getCollection("credit");

    return collection
        .find(new Document("numberDocument", creditDto.getNumberDocument())).map(doc->{
          CreditDto credit = new CreditDto();
          credit.setNumberDocument(doc.getString("numberDocument"));
          credit.setDateStart(doc.getString("dateStart"));
          credit.setShare(doc.getDouble("share"));
          credit.setDatePay(doc.getString("datePay"));
          credit.setBalanceStart(doc.getDouble("balanceStart"));
          credit.setAmount(doc.getInteger("amount"));
          credit.setBalanceEnd(doc.getDouble("balanceEnd"));
          credit.setCreated_datetime(doc.getString("created_datetime"));
          credit.setUpdated_datetime(doc.getString("updated_datetime"));
          credit.setActive(doc.getString("active"));
          return credit;
        }).filter(account->{
          return account.getActive().equals("S");
        }).toUni();
  }

  @Override
  public Uni<CreditDto> addCredit(CreditDto creditDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("credits");
    ReactiveMongoCollection<Document> collection = database.getCollection("credit");

    Document document = new Document()
        .append("numberDocument", creditDto.getNumberDocument())
        .append("dateStart", creditDto.getDateStart())
        .append("share", creditDto.getShare())
        .append("datePay", creditDto.getDatePay())
        .append("balanceStart", creditDto.getBalanceStart())
        .append("amount", creditDto.getAmount())
        .append("balanceEnd", creditDto.getBalanceEnd())
        .append("created_datetime", this.getDateNow())
        .append("updated_datetime", this.getDateNow())
        .append("active", "S");
    return collection.insertOne(document).replaceWith(creditDto);
  }

  @Override
  public Uni<CreditDto> updateCredit(CreditDto creditDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("credits");
    ReactiveMongoCollection<Document> collection = database.getCollection("credit");
    System.out.println(creditDto);
    Bson filter = eq("numberDocument", creditDto.getNumberDocument());
    Bson update = combine(
        set("dateStart", creditDto.getDateStart()),
        set("share", creditDto.getShare()),
        set("datePay", creditDto.getDatePay()),
        set("balanceStart", creditDto.getBalanceStart()),
        set("amount", creditDto.getAmount()),
        set("balanceEnd", creditDto.getBalanceEnd()),
        set("updated_datetime", this.getDateNow())
    );

    return collection.updateMany(filter,update).replaceWith(creditDto);
  }

  @Override
  public Uni<CreditDto> deleteCredit(CreditDto creditDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("credits");
    ReactiveMongoCollection<Document> collection = database.getCollection("credit");

    Bson filter = eq("numberDocument", creditDto.getNumberDocument());
    Bson update = set("active", "N");
    return collection.updateMany(filter,update).replaceWith(creditDto);
  }


  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }
}
