import axios from 'axios';

const api = axios.create({
    baseURL: '/api/v1',
    headers: {
        'Content-Type': 'application/json'
    }
});

// Interceptor para adicionar o Token JWT em todas as chamadas
api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    
    // Verifica se o token existe e não é a string "null" ou "undefined"
    if (token && token !== 'null' && token !== 'undefined') {
        config.headers.Authorization = `Bearer ${token}`;
    } else {
        // Remove o header caso exista algum lixo de sessões anteriores
        delete config.headers.Authorization;
    }
    
    return config;
});

api.interceptors.response.use(
    response => response,
    error => {
        console.group("Erro na Chamada API (Tratado pelo Modo Simulação)");
        if (error.response) {
            console.error("Status:", error.response.status);
            console.error("Dados:", error.response.data);
        } else {
            console.error("Erro de Rede ou Servidor Offline");
        }
        console.groupEnd();
        
        // Retornamos o erro para que o 'withMock' no appService possa capturá-lo
        return Promise.reject(error);
    }
);

export default api;
