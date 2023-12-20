<template>
    <div class="notifications-container">
      <div class="bell-and-number">
          <i class="fa fa-regular fa-bell fa-xl" v-on:click="handleBellClick()"></i>
          <div class="number-notifications-container">
              <h6 class="number-notifications" v-if="bellClicked == false">{{ this.friendRequests.length }}</h6>
          </div>
      </div>
      <div class="notifications" v-if="bellClicked == true">
        <p>Friend requests</p>
        <div class="request-container" v-for="request in friendRequests" v-bind:key="request.request_id"> 
            <p>{{request.usernameRequestor}}</p>
            <button v-on:click="acceptFriendRequest(request.userIdReqestor);">Accept?</button>
            <button v-if="!friendRequests.includes(request.usernameRequestor)" >Accepted</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>

  import FriendRequestService from '@/services/FriendRequestService';

  export default {
      name: "notification-bell",
      data() {
          return {
              bellClicked: false,
              friendRequests: [],
          }
      },
      beforeMount(){
        FriendRequestService.listFriendRequsts().then(response => {
            this.friendRequests = response.data
        })
      },
      methods: {
          handleBellClick() {
              if (this.bellClicked == false){
                this.bellClicked = true
              } else {
                this.bellClicked = false
              }
          },
          acceptFriendRequest(id){
            FriendRequestService.acceptFriendRequest(id).then(response => {
                if(response.status == 200){
                    let index = this.friendRequests.findIndex(friend => friend.id === id);
                  if (index > -1) { // only splice array when item is found
                    this.friendRequests.splice(index, 1); // 2nd parameter means remove one item only
                  }
                    console.log('friend Accepted')
                }
            })
          }
      }
  }
  </script>
  
  <style scoped>
  p {
      padding: 1px;
      margin: 0px;
      color: black;
      z-index: 2;
  }
  button{
    background-color: #99cbf7;
    border-radius: 20%;
    border: none;
    margin-left: 1em;
    cursor: pointer;
  }
  
  .notifications-container {
      display: flex;
      flex-direction: column;
      position: relative;
  }

  .request-container{
    display: flex;
    justify-content: space-around;
    padding: 5px;
  }
  
  .bell-and-number {
      display: flex;
      align-items: center;
  }
  
  .number-notifications-container {
      margin-top: 1.2em;
  }
  
  .number-notifications {
      height: max-content;
      width: max-content;
      padding: 10%;
      z-index: 2;
      background-color: red;
      color: #fff;
      border-radius: 30%;
  }
  
  .notifications {
      position: absolute;
      padding: 0px;
      top: 100%;
      left: 15;
      background: #fff;
      z-index: 1;
      box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
      max-height: 200px;
      overflow-y: auto;
  }
  </style>
  