import axios from "axios";


axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.headers.post["Content-Type"] = "application/json";


export const applHeaders = {
    "Accept": "application/json",
    "Content-Type": "application/json"
};


export function request(method, url, data, headers = applHeaders, responseType = "") {
    if (responseType !== "") {
        return axios({
            method: method,
            headers: headers,
            url: url,
            data: data,
            responseType: 'blob'
        });
    }

    return axios({
        method: method,
        headers: headers,
        url: url,
        data: data
    });
};

export function getBackgroundImage(base64){
    return `data:image/${getMimeType(base64)};base64,${base64}`;
};

function getMimeType(base64){
    if(base64.charAt(0) === 'i'){
        return "png";
    }
    if(base64.charAt(0) === '/'){
        return "jpg";
    }
    if(base64.charAt(0) === 'R'){
        return "gif";
    }
    return "svg+xml";
};