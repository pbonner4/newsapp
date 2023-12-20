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
    listNbaScores(){
      return apiClient.get('/nbaScores')
    },
    listNbaBoxScore(id){
      return apiClient.get(`/nbaBoxScore/${id}`)
    },
    listNhlScores(){
      return apiClient.get('/nhlScores')
    },
    listMmaScores(){
      return apiClient.get('/mmaScores')
    },
    listNflScores(){
      return apiClient.get('/nflScores')
    }
}