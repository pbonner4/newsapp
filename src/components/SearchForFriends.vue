<template>
  <div class="search-bar">
    <input class="search-input" type="text" v-model="userSearchedFor" placeholder="Search here for friends... " > <i class="fa fa-solid fa-user-magnifying-glass"></i>
    <div class="search-results" v-if="userSearchedFor.length > 0">
      <div class="search-bar-container" v-for="user in userExists" v-bind:key="user.id">
        <p>{{ user.username }}</p>
        <button v-if="!pendingRequests.some(request => request.usernameRequestee === user.username)" v-on:click="requesteeUserId = user.id; requesteeUsername = user.username; sendFriendRequest()">add Friend</button>
        <button v-if="pendingRequests.some(request => request.usernameRequestee === user.username)" v-on:click="requesteeUserId = user.id; requesteeUsername = user.username; deletePendingRequest(user.id)">Pending</button>
      </div>
    </div>
  </div>
</template>

<script>
import FriendRequestService from '@/services/FriendRequestService';
import ArticleDataService from '@/services/ArticleDataService';

export default {
  name: "searchForFriends",
  data() {
    return {
      allUsers: [],
      pendingRequests: [],
      userSearchedFor: '',
      requesteeUserId: 0,
      requesteeUsername: '',
      pending: false,
      currentUserId: 0,
      currentUsername:'',
      pendingButton: true
    }
  },
  async mounted() {
    FriendRequestService.getAllUsers().then(response => {
      this.allUsers = response.data;
    }),
    FriendRequestService.listPendingFriendRequests().then(response => {
      this.pendingRequests = response.data;
    }),
    //calls for current user
    ArticleDataService.getUser().then(response => {
            this.currentUserId = response.data
      }),
      ArticleDataService.getUsername().then(response => {
            this.currentUsername = response.data
      })
  },
  computed: {
    userExists() {
      return this.allUsers.filter(user => user.username.toLowerCase().includes(this.userSearchedFor.toLowerCase()));
    },
  },
  methods: {
    sendFriendRequest(){
      let friendRequest = {
        "userIdReqestor": this.currentUserId,
        "usernameRequestor": this.currentUsername,
        "userIdRequestee": this.requesteeUserId,
        "usernameRequestee": this.requesteeUsername,
        "requestPendingOrAccepted": this.pending
      }
      FriendRequestService.sendFriendRequest(friendRequest).then(response => {
        if(response.status == 200){
            this.pendingRequests.push(friendRequest)
          }
      })
  },
  deletePendingRequest(requestee_id){
    FriendRequestService.deleteFriendRequest(Number(requestee_id)).then(response => {
      if(response.status == 200){
      window.location.reload()
      }
    })
  }
}
}
</script>

<style scoped>

.search-input{
  margin-top: 10px;
  background: #ffffff;
border: none;
border: 1px solid #e5e5e5;
color: #444444;
width: calc(100% - 100px);
padding: 8px 35px 8px 23px;
font-size: 13px;
background-position: right 8px;
background-repeat: no-repeat;
border-radius: 60px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

.search-bar{
  display: flex;
  justify-content: center;
}
.search-results {
  position: absolute;
  z-index: 9999;
  background-color: #fff;
  border: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  margin-top: 35px;
  width: max-content;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.search-bar-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px;
  cursor: pointer;
}

button {
  background-color: #99cbf7;
  border-radius: 20%;
  border: none;
  margin-left: 1em;
  cursor: pointer;
  height: fit-content;
  width: max-content;
  margin-right: 5px;
}
</style>
