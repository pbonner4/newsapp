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
    getStatuses(){
        return apiClient.get(`/status`)
    }, 
    getAllStatuses(){
      return apiClient.get('/allStatuses')
    },
    postStatus(status){
        return apiClient.post('/status', status)
    },
    deleteStatus(id){
      return apiClient.delete(`/status/${id}`)
    },
    listfriendsStatus(){
      return apiClient.get(`/friendsStatuses`)
    },
    listLikedStatuses(){
      return apiClient.get('/listLikedStatuses')
    },
    listDislikedStatuses(){
      return apiClient.get('/listDislikedStatuses')
    },
    likeAStatus(likedStatus){
      return apiClient.post('/likeStatus', likedStatus)
    },
    dislikedAStatus(dislikedStatus){
      return apiClient.post('/dislikeStatus', dislikedStatus)
    },
    unlikeAStatus(id){
      return apiClient.post(`/unlikeAStatus/${id}`)
    },
    undislikeAPost(id){
      return apiClient.post(`/undislikeAStatus/${id}`)
    },
    getLikeCount(){
      return apiClient.get('/statusLikeCount')
    },
    getDislikeCount(){
      return apiClient.get('/statusDislikeCount')
    },

    //GETIING STATUSES BY SUBJECT/FOLLOWING
    getBusinessStatuses(){
      return apiClient.get('/businessStatuses')
    },
    getNbaStatuses(){
      return apiClient.get('/nbaStatuses')
    },
    getNflStatuses(){
      return apiClient.get('/nflStatuses')
    },
    getNhlStatuses(){
      return apiClient.get('/nhlStatuses')
    },
    getMmaStatuses(){
      return apiClient.get('/mmaStatuses')
    },
    getPoliticsStatuses(){
      return apiClient.get('/politicsStatuses')
    },
    getInTheatersStatuses(){
      return apiClient.get('/inTheatersStatuses')
    },
    getMusicStatuses(){
      return apiClient.get('/musicStatuses')
    },
    getMoviesStatuses(){
      return apiClient.get('/moviesStatuses')
    },
    getTvStatuses(){
      return apiClient.get('/tvStatuses')
    },
    getPeopleFollowing(){
      return apiClient.get('/peopleFollowingStatuses')
    }
}