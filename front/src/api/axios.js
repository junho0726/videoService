import axios from "axios";
import store from "@/store";
import router from "@/router";

let instance = axios.create({
    headers: {
        'Content-Type' : 'application/json; charset=UTF-8'
    }
});

instance.interceptors.request.use(config => {
    if(localStorage.getItem('token') != null) {
       config.headers['Access_Token'] = localStorage.getItem('token');
    }
    return config;
});

export default instance;