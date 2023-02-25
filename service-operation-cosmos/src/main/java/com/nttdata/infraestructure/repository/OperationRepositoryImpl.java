package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.OperationRepository;
import com.nttdata.domain.models.OperationDto;
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
@ApplicationScoped
public class OperationRepositoryImpl implements OperationRepository {
  private final ReactiveMongoClient reactiveMongoClient;

  public OperationRepositoryImpl(ReactiveMongoClient reactiveMongoClient) {
    this.reactiveMongoClient = reactiveMongoClient;
  }

  @Override
  public Uni<OperationDto> addOperation(OperationDto operationDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("operations");
    ReactiveMongoCollection<Document> collection = database.getCollection("operation");

    Document document = new Document()
        .append("typeCard", operationDto.getTypeCard())
        .append("numberCard", "xxx")
        .append("numberAccountAssociated", operationDto.getNumberAccountAssociated())
        .append("pin", "xxx")
        .append("dueDate", "xxx")
        .append("codeValidation", "xxx")
        .append("nroDocument", 33333333L)

        .append("typeOperation", operationDto.getTypeOperation())
        .append("typeDocument", operationDto.getTypeDocument())
        .append("amount", operationDto.getAmount())
        .append("created_datetime", this.getDateNow())
        .append("updated_datetime", this.getDateNow())
        .append("active", "S");

    System.out.println("OK_OK");

    return collection.insertOne(document).replaceWith(operationDto);
  }

  @Override
  public Multi<OperationDto> getListOperation(OperationDto operationDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("operations");
    ReactiveMongoCollection<Document> collection = database.getCollection("operation");
    System.out.println(operationDto.getNumberAccountAssociated());
    Bson filter = eq("numberAccountAssociated", operationDto.getNumberAccountAssociated());

    return collection
        .find(filter).map(doc->{
          OperationDto operationDto1 = new OperationDto();
          operationDto1.setTypeCard(doc.getInteger("typeCard"));
          operationDto1.setNumberCard(doc.getString("numberCard"));
          operationDto1.setNumberAccountAssociated(doc.getString("numberAccountAssociated"));
          operationDto1.setPin(doc.getString("pin"));
          operationDto1.setDueDate(doc.getString("dueDate"));
          operationDto1.setCodeValidation(doc.getString("codeValidation"));
          operationDto1.setNroDocument(doc.getLong("nroDocument"));
          operationDto1.setTypeDocument(doc.getInteger("typeDocument"));

          if(operationDto1.getTypeOperation() == 2){
            operationDto1.setTypeOperation(-1 + doc.getInteger("typeOperation"));
          }else{
            operationDto1.setTypeOperation(doc.getInteger("typeOperation"));
          }
          operationDto1.setAmount(doc.getDouble("amount"));
          operationDto1.setActive(doc.getString("active"));

          return operationDto1;
        }).filter(s->s.getActive().equals("S"));
  }


  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }
}
