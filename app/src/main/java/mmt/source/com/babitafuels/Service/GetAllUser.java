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

import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.Model.User;

import static mmt.source.com.babitafuels.Model.Constants.url_base;

public class GetAllUser extends AsyncTask<String, Void, Integer> {

    FuelMnt fm_inst = FuelMnt.getInstance();
    Integer result = 500;
    @Override
    protected Integer doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            // Create Apache HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_base+"customer/register");

            System.out.println("shiva calling register  ");
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();

            StringEntity stringEntity = new StringEntity(gson.toJson(fm_inst.getUsrInfo()));
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

            JSONArray post = new JSONArray(inparam);
            FuelMnt fm_inst = FuelMnt.getInstance();
            fm_inst.getAllUsrInfo().clear();
            for(int i = 0; i < post.length(); i++) {
                JSONObject obj = post.getJSONObject(i);
                User item = new User();
                //System.out.println("shvia parsing 1");
                item.setUsrId(obj.getString("usrId"));
                item.setUsrName(obj.getString("usrName"));
                item.setDoJ(obj.getString("doJ"));
                item.setDoL(obj.getString("doL"));
                item.setGrade(obj.getString("grade"));
                item.setEmailId(obj.getString("emailId"));
                //System.out.println("shvia parsing 2");
                item.setMobileNum(obj.getString("mobileNum"));
                item.setAddr(obj.getString("addr"));
                item.setEmgEmailId(obj.getString("emgEmailId"));
                item.setEmgMobileNum(obj.getString("emgMobileNum"));
                item.setEmgAddr(obj.getString("emgAddr"));
                item.setBloodG(obj.getString("bloodG"));
                item.setIdProof(obj.getString("idProof"));
                //System.out.println("shvia parsing 3");
                item.setRegType(obj.getString("regType"));
                item.setBusName(obj.getString("busName"));
                item.setNotes(obj.getString("notes"));
                item.setPay(obj.getString("pay"));
                //System.out.println("shvia parsing 4");
                item.setPassword(obj.getString("password"));
                item.setBunkId(obj.getString("bunkId"));
                item.setStatus(obj.getBoolean("status"));
                item.setStartDate(obj.getString("startDate"));
                item.setCreditLmt(obj.getString("creditLmt"));
                item.setFuelType(obj.getString("fuelType"));
                item.setAdvPaid(obj.getString("advPaid"));
                item.setEndDate(obj.getString("endDate"));
                item.setEmpType(obj.getString("empType"));
                item.setDesignation(obj.getString("designation"));
                //System.out.println("shvia parsing 5");
                fm_inst.getAllUsrInfo().add(item);
            }
        }catch (Exception e) {
            System.out.println("shiva failed apply parse");
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
