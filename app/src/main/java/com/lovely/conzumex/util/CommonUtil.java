package com.lovely.conzumex.util;

import com.google.gson.Gson;
import com.lovely.conzumex.MyApplication;
import com.lovely.conzumex.model.CommonResponse;

import java.io.InputStream;

public class CommonUtil {

    /**
     * Gets mock response.
     *
     * @param fileName the file name
     * @return the mock response
     */
    public static CommonResponse getMockResponse(final String fileName) {

        // TODO :- usage -> while calling this static mockResponse
        try {
            InputStream is = MyApplication.getAppContext().getAssets().open(fileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String bufferString = new String(buffer);
            Gson gson = new Gson();
            return gson.fromJson(bufferString, CommonResponse.class);
        } catch (final Exception e) {
            e.printStackTrace();
            return new CommonResponse();
        }
    }
}
