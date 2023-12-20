import axios from 'axios';
const currentToken = localStorage.getItem('token')
const apiClient =axios.create({
  baseURL: "http://localhost:9000",
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${currentToken}`
  }
});

export default {
    logger(event){
        return apiClient.post(`/collect/log`, event)
    }, 
    getCrsf(){
        return apiClient.get('/crsf')
    }
}