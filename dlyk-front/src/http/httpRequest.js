import axios from 'axios';

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
    });
}