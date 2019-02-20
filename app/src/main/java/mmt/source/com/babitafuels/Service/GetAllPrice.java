package mmt.source.com.babitafuels.Service;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mmt.source.com.babitafuels.Model.Bunk;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.Model.Price;

import static mmt.source.com.babitafuels.Model.Constants.url_base;

public class GetAllPrice extends AsyncTask<String, Void, Integer> {

    FuelMnt fm_inst = FuelMnt.getInstance();
    Integer result = 500;
    @Override
    protected Integer doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            // Create Apache HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_base+"Price/transaction");

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();

            StringEntity stringEntity = new StringEntity(gson.toJson(fm_inst.getTodayPrice()));
            postRequest.setEntity(stringEntity);
            postRequest.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpclient.execute(postRequest);


            String line;
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            if((line = reader.readLine())!=null){
                System.out.println("shiva get price list response "+line);
            }

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                parseResult(line);
                result = 200;
            } else {
                System.out.println("shiva failure response received");
            }

        } catch (Exception e) {
            System.out.println("shiva brc"+e.getCause());
            e.printStackTrace();
            System.out.println("shiva brc exception occurred ");
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

            JSONArray post = new JSONArray(inparam);
            FuelMnt fm_inst = FuelMnt.getInstance();
            fm_inst.getAllPrice().clear();
            for(int i = 0; i < post.length(); i++) {
                JSONObject obj = post.getJSONObject(i);
                Price item = new Price();
                item.setBunkId(obj.getString("bunkId"));
                item.setCreateDate(obj.getString("createDate"));
                item.setPetrolPrice(obj.getString("petrolPrice"));
                item.setDieselPrice(obj.getString("dieselPrice"));
                item.setOilPrice(obj.getString("oilPrice"));
                fm_inst.getAllPrice().add(item);
            }
        }catch (Exception e) {
            System.out.println("shiva failed get price list parse");
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
