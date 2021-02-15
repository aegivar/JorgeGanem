
import com.mongodb.reactivestreams.client.*;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Sorts.descending;

public class MongoReactiveTest {
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> collection;
    CountDownLatch latch= new CountDownLatch(100);
    @BeforeEach
    public void before(){
        mongoClient = MongoClients.create("mongodb://localhost");
        mongoDatabase= mongoClient.getDatabase("mydb");
        collection = mongoDatabase.getCollection("test");
    }

    @Test
    public void insertOneDocument(){
        Document document = new Document("name","mongodb")
                .append("type","database")
                .append("cuont",1)
                .append("info",new Document("x",200)
                        .append("y",102));
        Publisher<Success>  publisher =collection.insertOne(document);
        publisher.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Insert");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Failed");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
       // latch.await();
    }

    @Test
    public void insertManyDocumentTest() throws InterruptedException {
        List<Document> documentList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            documentList.add(new Document("i",i));
        }
        Publisher<Success> publisher =  collection.insertMany(documentList);
        publisher.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(200);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Insert");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Failed");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
        latch.await();
    }

    @Test
    public void findManyDocumentTest() throws InterruptedException {
        List<Document> documentList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            documentList.add(new Document("i",i));
        }
        Publisher<Success> publisher =  collection.insertMany(documentList);
        publisher.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Insert");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Failed");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
        latch.await();
    }

    @Test
    public void findcollectionwithfiltereq() throws InterruptedException {
        Publisher<Document> publisher = collection.find(eq("i",71)).first();
        publisher.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Insert");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Failed");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
        latch.await();
    }

    @Test
    public void findgtandlte() throws InterruptedException {
        Publisher<Document> publisher = collection.find(and(gt("i",50),lte("i",53)) );
        publisher.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Insert");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Failed");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
        latch.await();
    }

    @Test
    public void sort() {
        Publisher<Document> documentPublisher = collection.find(exists("i")).sort(descending("i"));
        documentPublisher.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(100);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Insert");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Failed");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
    }
    @Test
    public void findWithProyectingFieldCollections() throws InterruptedException {
        Publisher<Document> publisher = collection.find().projection(excludeId());
        publisher.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(120);
            }

            @Override
            public void onNext(Object o) {
                System.out.println(o.toString());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Failed");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
        latch.await();
    }
}
