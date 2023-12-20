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

    getUser(){
      return apiClient.get('/whoami')
    },

    getUsername(){
      return apiClient.get('/whoami/name')
    },

    likeAPost(like, likedPost){
        return apiClient.post(`/userpreferences/likePost/${likedPost}`, like)
    },
    checkPostLike(){
      return apiClient.get(`/userpreferences/likePostCheck`)
    },
    unlikeAPost(likedPost){
      return apiClient.post(`/userpreferences/unlikePost/${likedPost}`)
    },


    dislikeAPost(dislike, dislikedPost){
      return apiClient.post(`/userpreferences/dislikePost/${dislikedPost}`, dislike)
    },
    undislikeAPost(dislikedPost){
      return apiClient.post(`/userpreferences/undislikePost/${dislikedPost}`)
    },
    listDislikes(){
      return apiClient.get(`/userpreferences/dislikes`)
    },

    saveForLater(saveForLater, saved){
      return apiClient.post(`/userpreferences/saveForLater/${saved}`, saveForLater)
    },
    removeSaveForLater(saved){
      return apiClient.post(`/userpreferences/removeFromSaveForLater/${saved}`)
    },
    listSaveForLaters(){
      return apiClient.get(`/userpreferences/saveForLaters`)
    }
  }