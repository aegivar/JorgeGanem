import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.dns.DnsClient;
import io.vertx.core.file.FileProps;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.vertx.core.file.FileSystem;

import java.util.List;

public class VertexTest {

    Vertx vertx = Vertx.vertx();
    @Test
    public void vertxReactiveDNStest(){
        DnsClient client = Vertx.vertx().createDnsClient(53,"9.9.9.9");
        client.resolveA("vertx.io",ar->{
            if(ar.succeeded()){
                List<String> records = ar.result();
                for (String record:records) {
                    System.out.println("Creation  dns"+ record);
                }
            }else {
                System.out.println("fail to result" + ar.cause());
            }
        });
        assertThat(client.resolveA("vertx.io").succeeded());
    }

    @Test
    public  void vertxSendEventBusTest(){
        String message = "hello world";
        vertx.eventBus().send("addres",message);
        vertx.eventBus().consumer("addres");
        System.out.println(message);
        assertThat(message).contains("hello world");
    }

    @Test
    public void vetxFileTest(){
        FileSystem fs = vertx.fileSystem();
        Future<FileProps> future = fs.props("/my_file.txt");
        future.onComplete((AsyncResult<FileProps> ar)->{
            if(ar.succeeded()){
                FileProps props = ar.result();
                System.out.println("File size"+props.size());
            }else {
                System.out.println("failed"+ ar.cause().getMessage());
            }
            assertThat(fs.props("my_file.txt"));
        });

    }
    @Test
    public void verTxTestRequest(){
        vertx.createHttpServer().requestHandler(req -> req.response().end("Hello")).listen(8080);

    }
}
