package mmt.source.com.babitafuels.Service;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mmt.source.com.babitafuels.Model.FuelMnt;

import static mmt.source.com.babitafuels.Model.Constants.url_base;

public class CreditSetup extends AsyncTask<String, Void, Integer> {

    FuelMnt fm_inst = FuelMnt.getInstance();
    Integer result = 500;
    @Override
    protected Integer doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            // Create Apache HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_base+"customer/register");

            System.out.println("shiva calling credit setup  ");
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();

            StringEntity stringEntity = new StringEntity(gson.toJson(fm_inst.getCreditUsr()));
            postRequest.setEntity(stringEntity);
            postRequest.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpclient.execute(postRequest);


            String line;
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            if((line = reader.readLine())!=null){
                System.out.println("shiva get register response "+line);
            }
            System.out.println("Status code "+httpResponse.getStatusLine().getStatusCode());

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                System.out.println("shiva successful register ");
                parseResult(line);
                result = 200;
            } else {
                System.out.println("shiva failure response received");
            }

        } catch (Exception e) {
            System.out.println("brc"+e.getCause());
            e.printStackTrace();
            System.out.println("brc exception occurred ");
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private void parseResult(String inparam) {
        try {
            System.out.println("kk verfied calling parse");
            JSONObject post = new JSONObject(inparam);
            result = post.getInt("statuscode");
            /*brc_inst.getUser().setApplId(post.getInt("id"));
            brc_inst.getUser().setResidence(post.getString("resKar"));
            brc_inst.getUser().setFirstName(post.getString("firstname"));
            brc_inst.getUser().setLastName(post.getString("lastname"));
            brc_inst.getUser().setMiddleName(post.getString("middlename"));
            brc_inst.getUser().setEmail(post.getString("useremail"));
            brc_inst.getUser().setMobileno(post.getString("usermobile"));
            brc_inst.getUser().setCorresp_address(post.getString("coraddress"));
            brc_inst.getUser().setPermanent_address(post.getString("peraddress"));
            brc_inst.getUser().setDob(post.getString("userdob"));
*/
        }catch (Exception e) {
            System.out.println("kumar failed apply parse");
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == 1) {
            //mGridAdapter.setGridData(mGridData);
        } else {
            // Toast.makeText(LocationViewActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
