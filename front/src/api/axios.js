import axios from "axios";
import store from "@/store";

let instance = axios.create({
    headers: {
        'Content-Type' : 'application/json; charset=UTF-8'
    }
});

instance.interceptors.request.use(config => {
    if(store.getters['user/getToken'] != null) {
       config.headers['Access_Token'] = store.getters['user/getToken'];
    }
    return config;
});

export default instance;