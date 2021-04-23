    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*

    import com.amazonaws.AmazonServiceException;
    import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
    import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
    import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
    import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

    @RestController
    @RequestMapping(value = "/tables")
    @CrossOrigin(origins = "http://localhost:8080")
    public class AccountDBController {

        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
        ListTablesRequest request;
        boolean more_tables = true;
        String last_name = null;

        @RequestMapping(value = "/get-ddb-table", method = RequestMethod.GET)
        public void getTables() {
            while(more_tables) {
                if (last_name == null) {
                    request = new ListTablesRequest().withLimit(10);
                }
                else {
                    request = new ListTablesRequest()
                            .withLimit(10)
                            .withExclusiveStartTableName(last_name);
                }

                ListTablesResult table_list = ddb.listTables(request);
                List<String> table_names = table_list.getTableNames();

                if (table_names.size() > 0) {
                    for (String cur_name : table_names) {
                        System.out.format("* %s\n", cur_name);
                    }
                } else {
                    System.out.println("No tables found!");
                }

                last_name = table_list.getLastEvaluatedTableName();
                if (last_name == null) {
                    more_tables = false;
                }
            }
        }
    }

package com.test.HelloWorldMaven;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
