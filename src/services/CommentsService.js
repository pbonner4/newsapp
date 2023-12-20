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
    getArticleComments(articleId){
        return apiClient.get(`/articles/${articleId}/getComments`)
    },
    postComment(articleId, comment){
        return apiClient.post(`/articles/${articleId}/postComment`, comment)
    },
}