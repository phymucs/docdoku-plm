/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2014 DocDoku SARL
 *
 * This file is part of DocDokuPLM.
 *
 * DocDokuPLM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DocDokuPLM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with DocDokuPLM.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.docdoku.server.converters.utils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RadiusCalculator {

    private static final String CONF_PROPERTIES="/com/docdoku/server/converters/utils/conf.properties";
    private static final Properties CONF = new Properties();

    public RadiusCalculator() {
    }

    public static double calculateRadius(File file){
        InputStream inputStream = null;
        try{
            inputStream = RadiusCalculator.class.getResourceAsStream(CONF_PROPERTIES);
            CONF.load(inputStream);
            String nodeServerUrl = CONF.getProperty("nodeServerUrl");

            URL url = new URL(nodeServerUrl+"/radius");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            JsonObjectBuilder body = Json.createObjectBuilder().add("filename",file.getAbsolutePath());
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            wr.write(body.build().toString());
            wr.flush();
            wr.close();

            //int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String result = response.toString();

            JsonObject jsonResponse = Json.createReader(new StringReader(result)).readObject();
            return Double.parseDouble(jsonResponse.getString("radius"));
        } catch (IOException e) {
            Logger.getLogger(RadiusCalculator.class.getName()).log(Level.INFO, null, e);
        }finally {
            try{if(inputStream!=null){
                    inputStream.close();
            }}catch (IOException ignored){}
        }

        return 0;
    }
}