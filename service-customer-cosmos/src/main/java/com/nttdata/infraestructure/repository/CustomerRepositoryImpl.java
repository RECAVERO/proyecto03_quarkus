package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.CustomerRepository;
import com.nttdata.domain.models.CustomerDto;
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
public class CustomerRepositoryImpl implements CustomerRepository {
  private final ReactiveMongoClient reactiveMongoClient;

  public CustomerRepositoryImpl(ReactiveMongoClient reactiveMongoClient) {
    this.reactiveMongoClient = reactiveMongoClient;
  }

  @Override
  public Multi<CustomerDto> list() {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("customers");
    ReactiveMongoCollection<Document> collection = database.getCollection("customer");

    return collection.find().map(doc->{
      CustomerDto customer = new CustomerDto();
      customer.setName(doc.getString("name"));
      customer.setLastName(doc.getString("lastName"));
      customer.setNroDocument(doc.getLong("nroDocument"));
      customer.setTypeCustomer(doc.getInteger("typeCustomer"));
      customer.setTypeDocument(doc.getInteger("typeDocument"));
      customer.setNumberTelephone(doc.getString("numberTelephone"));
      customer.setCreated_datetime(doc.getString("created_datetime"));
      customer.setUpdated_datetime(doc.getString("updated_datetime"));
      customer.setActive(doc.getString("active"));
      return customer;
    }).filter(customer->{
      return customer.getActive().equals("S");
    });
  }

  @Override
  public Uni<CustomerDto> findByNroDocument(CustomerDto customerDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("customers");
    ReactiveMongoCollection<Document> collection = database.getCollection("customer");
    return collection
        .find(new Document("nroDocument", customerDto.getNroDocument())).map(doc->{
          CustomerDto customer = new CustomerDto();
          customer.setName(doc.getString("name"));
          customer.setLastName(doc.getString("lastName"));
          customer.setNroDocument(doc.getLong("nroDocument"));
          customer.setTypeCustomer(doc.getInteger("typeCustomer"));
          customer.setTypeDocument(doc.getInteger("typeDocument"));
          customer.setNumberTelephone(doc.getString("numberTelephone"));
          customer.setCreated_datetime(doc.getString("created_datetime"));
          customer.setUpdated_datetime(doc.getString("updated_datetime"));
          customer.setActive(doc.getString("active"));
          return customer;
        }).filter(s->s.getActive().equals("S")).toUni();
  }

  @Override
  public Uni<CustomerDto> addCustomer(CustomerDto customerDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("customers");
    ReactiveMongoCollection<Document> collection = database.getCollection("customer");
    Document document = new Document()
        .append("name", customerDto.getName())
        .append("lastName", customerDto.getLastName())
        .append("nroDocument", customerDto.getNroDocument())
        .append("typeCustomer", customerDto.getTypeCustomer())
        .append("typeDocument", customerDto.getTypeDocument())
        .append("numberTelephone", customerDto.getNumberTelephone())
        .append("created_datetime", this.getDateNow())
        .append("updated_datetime", this.getDateNow())
        .append("active", "S");


    return collection.insertOne(document).replaceWith(customerDto);
  }

  @Override
  public Uni<CustomerDto> updateCustomer(CustomerDto customerDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("customers");
    ReactiveMongoCollection<Document> collection = database.getCollection("customer");
    /*Document document1 = new Document()
        .append("nroDocument", customerDto.getNroDocument());
    Document document = new Document()
        .append("name", customerDto.getName())
        .append("lastName", customerDto.getLastName())
        .append("nroDocument", customerDto.getNroDocument())
        .append("typeCustomer", customerDto.getTypeCustomer())
        .append("typeDocument", customerDto.getTypeDocument())
        .append("numberTelephone", customerDto.getNumberTelephone())
        .append("updated_datetime", this.getDateNow());*/

    Bson filter = eq("nroDocument", customerDto.getNroDocument());
    Bson update = combine(
        set("name", customerDto.getName()),
        set("lastName", customerDto.getLastName()),
        set("nroDocument", customerDto.getNroDocument()),
        set("typeCustomer", customerDto.getTypeCustomer()),
        set("typeDocument", customerDto.getTypeDocument()),
        set("numberTelephone", customerDto.getNumberTelephone()),
        set("updated_datetime", this.getDateNow())
    );


    return collection.findOneAndUpdate(filter, update)
        .onItem().transform(doc -> {
          CustomerDto customer = new CustomerDto();
          customer.setName(doc.getString("name"));
          customer.setLastName(doc.getString("lastName"));
          customer.setNroDocument(doc.getLong("nroDocument"));
          customer.setTypeCustomer(doc.getInteger("typeCustomer"));
          customer.setTypeDocument(doc.getInteger("typeDocument"));
          customer.setNumberTelephone(doc.getString("numberTelephone"));
          customer.setCreated_datetime(doc.getString("created_datetime"));
          customer.setUpdated_datetime(doc.getString("updated_datetime"));
          customer.setActive(doc.getString("active"));
          return customer;
        });
  }

  @Override
  public Uni<CustomerDto> deleteCustomer(CustomerDto customerDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("customers");
    ReactiveMongoCollection<Document> collection = database.getCollection("customer");
    Bson filter = eq("nroDocument", customerDto.getNroDocument());
    Bson update = set("active", "N");
    return collection.updateMany(filter,update).replaceWith(customerDto);
  }

  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }
}
