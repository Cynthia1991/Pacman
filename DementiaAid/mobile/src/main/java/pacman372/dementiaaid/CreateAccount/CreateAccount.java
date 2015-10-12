package pacman372.dementiaaid.CreateAccount;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

// Include the following imports to use table APIs
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.table.*;
import com.microsoft.azure.storage.table.TableQuery.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import pacman372.dementiaaid.MyApplication;

/**
 * Created by jieliang on 8/10/2015.
 */
public class CreateAccount
{
    // Define the connection-string with your values.
    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=milabmlstorage;" +
                    "AccountKey=lHwkIRv8Odjsf5+DtWpCzcSN9WEneDFUUTzuWQ3bG1tw8sbkhj/uEg98oDjDjwCQ64wnZlT9TQQAqbYGDiAa5g==";

public CreateAccount()
{



    }

    public Boolean MakeRequest (String username,String password)
    {
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString);

            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

            // Create a cloud table object for the table.
            CloudTable cloudTable = tableClient.getTableReference("Pacman_carer_db");

            // Create a new customer entity.
            CareEntity carer1 = new CareEntity(username);
            carer1.setPhone(Integer.parseInt(password));

            // Create an operation to add the new customer to the people table.
            TableOperation insertCarer = TableOperation.insertOrReplace(carer1);

            // Submit the operation to the table service.
            cloudTable.execute(insertCarer);
            return  true;
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
            return  false;
        }

    }
 /*
    public static String makeRequest(String uri, String json) {
        HttpURLConnection urlConnection;
        String url;
        String data = json;
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(data);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

*/
    public Void Create(String password, String username) {
        return null;
    }
}

