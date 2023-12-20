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
    sendFriendRequest(FriendRequest){
        return apiClient.post(`/sendFriendRequest`, FriendRequest)
    }, 
    acceptFriendRequest(userRequestorId){
        return apiClient.put(`/acceptFriendRequest/${userRequestorId}`)
    },
    deleteFriendRequest(requestee_id){
        return apiClient.delete(`/deleteFriendRequest/${requestee_id}`)
    },
    listFriendRequsts(){
        return apiClient.get('/friendRequests')
    },
    listPendingFriendRequests(){
        return apiClient.get('/pendingFriendRequests')
    },
    listFriends(){
        return apiClient.get('/friends')
    },
    getAllUsers(){
        return apiClient.get('/getAllUsers')
    },
    //FOLLOWING
    followSomeone(follow){
        return apiClient.post('/followSomeone', follow)
    },
    getPeopleFollowing(){
        return apiClient.get('/getPeopleFollowing')
    },
    unFollowSomeone(userFollowedId){
        return apiClient.post(`/followSomeone/${userFollowedId}`)
    }
}