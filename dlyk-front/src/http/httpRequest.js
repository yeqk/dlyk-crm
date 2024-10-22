import axios from 'axios';
import {getTokenName} from "../util/util.js";

axios.defaults.baseURL = "http://localhost:8080"

export function doGet(url, params) {
    return axios({
        method: 'get',
        url: url,
        params: params,
        dataType: 'json'
    });
}

export function doPost(url, data) {
    return axios({
        method: 'post',
        url: url,
        data: data,
        dataType: 'json'
    });
}

export function doPut(url, data) {
   return axios({
        method: 'put',
        url: url,
        data: data,
        dataType: 'json'
    });
}

export function doDelete(url, params) {
    return axios({
        method: 'delete',
        url: url,
        params: params,
        dataType: 'json'
    })
}

// Add a request interceptor
axios.interceptors.request.use(function (config) {
    // Add jwt token to the header
    let token = window.sessionStorage.getItem(getTokenName());
    if (!token) {
        token = window.localStorage.getItem(getTokenName());
        if (token) {
            config.headers['rememberMe'] = true;
        }
    }

    if (token) {
        config.headers['Authorization'] = token;
    }

    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
}, function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
});
