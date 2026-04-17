package com.example.havenhub.nettest.request;


public class ApiClient {
    private static ServiceAPI apiService = null;

    public static ServiceAPI getApiService() {
        if (apiService == null) {
            apiService = RetrofitClient.getClient().create(ServiceAPI.class);
        }
        return apiService;
    }
}