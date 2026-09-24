// import axios from 'axios';
// const api=axios.create({baseURL:'http://localhost:8080/api'});
// api.interceptors.request.use(c=>{const token=localStorage.getItem('token');if(token)c.headers.Authorization=`Bearer ${token}`;return c;});
// export default api;

//
// import axios from 'axios';
//
// const api = axios.create({
//     //baseURL: 'http://localhost:8080/api'
//    baseURL = "https://projectmonitor-2.onrender.com/api";
// });
//
// api.interceptors.request.use(config => {
//     const token = localStorage.getItem('token');
//
//     if (token) {
//         config.headers.Authorization = `Bearer ${token}`;
//     }
//
//     return config;
// });
//
// export default api;

import axios from 'axios';

const api = axios.create({
    baseURL: 'https://projectmonitor-2.onrender.com/api'
});

api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default api;