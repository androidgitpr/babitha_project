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
import mmt.source.com.babitafuels.Model.Expenses;
import mmt.source.com.babitafuels.Model.FuelMnt;

import static mmt.source.com.babitafuels.Model.Constants.url_base;

public class GetAllExpenses extends AsyncTask<String, Void, Integer> {

    FuelMnt fm_inst = FuelMnt.getInstance();
    Integer result = 500;
    @Override
    protected Integer doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            // Create Apache HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_base+"Expenses/create");

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            //System.out.println("shiva exp api ");
            StringEntity stringEntity = new StringEntity(gson.toJson(fm_inst.getExpenses()));
            postRequest.setEntity(stringEntity);
            postRequest.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpclient.execute(postRequest);


            String line;
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            if((line = reader.readLine())!=null){
                System.out.println("shiva get expenses list response "+line);
            }

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                parseResult(line);
                result = 200;
            } else {
                System.out.println("shiva failure response received");
            }

        } catch (Exception e) {
            System.out.println("shiva "+e.getCause());
            e.printStackTrace();
            System.out.println("shiva  exception occurred ");
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
            fm_inst.getGetMyExpenses().clear();
            for(int i = 0; i < post.length(); i++) {
                JSONObject obj = post.getJSONObject(i);
                Expenses item = new Expenses();
                item.setBunkId(obj.getInt("bunkId"));
                item.setEmpId(obj.getString("empId"));
                item.setDescription(obj.getString("description"));
                item.setAmount(obj.getString("amount"));
                item.setExpType(obj.getString("expType"));
                item.setExpDate(obj.getString("expDate"));
                item.setCreateDate(obj.getString("createDate"));
                fm_inst.getGetMyExpenses().add(item);
            }
        }catch (Exception e) {
            System.out.println("shiva failed get expesnes list parse");
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
