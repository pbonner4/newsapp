<template>
  <div class="status-sidebar">
    <SearchForFriends/>
    <p>Friend Count: {{ friends.length }}</p>
    <h1>Friends Thoughts:</h1>
    <div class="status-container" v-for="status in listFriendsStatuses" v-bind:key="status.id">
            <div class = "status">
                <div class="username-date">
                    <h4>@{{ status.username }}</h4> 
                    <h6>{{ status.date }}</h6>
                </div>
                <h6 class="status-text">{{ status.status }}</h6>
                <div class="status-buttons"> 
                  <i class="fa fa-regular fa-thumbs-up" v-bind:class="{'fa fa-solid fa-thumbs-up i-changed': checkIfLiked(status.id)}" v-on:click="status_id = status.id; likeAPost()"> {{mapLikeCount[status.id]}}</i>
                  <i class="fa fa-regular fa-thumbs-down" v-bind:class="{'fa fa-solid fa-thumbs-down i-changed': checkIfDisLiked(status.id)}" v-on:click="status_id = status.id; dislikeAPost()"> {{mapDislikeCount[status.id]}}</i>
                </div>
            </div>
    </div>
  </div>
</template>

<script>
import SearchForFriends from './SearchForFriends.vue';
import FriendRequestService from '@/services/FriendRequestService';
import StatusService from '../services/StatusService';
import ArticleDataService from '@/services/ArticleDataService';


export default {
name: "statusSidebar",
data() {
          return {
              friends: [],
              friendsStatuses: [],
              currentUsername: '',
              currentUserId: 0,
              status_id: 0,
              liked: true,
              likedStatuses: [],
              likeCount: {},
              dislikedStatuses: [],
              disliked: true,
              dislikeCount: {}
          }
      },
components: {
  SearchForFriends
},
async mounted() {
  FriendRequestService.listFriends().then(response => {
    this.friends = response.data
  }),
  StatusService.listfriendsStatus().then(response => {
    this.friendsStatuses = response.data
  }),
  StatusService.listLikedStatuses().then(response => {
    this.likedStatuses = response.data
  }),
  StatusService.getLikeCount().then(response => {
    this.likeCount = response.data
  }),
  StatusService.getDislikeCount().then(response => {
    this.dislikeCount = response.data
  }),
  StatusService.listDislikedStatuses().then(response => {
    this.dislikedStatuses = response.data
  }),
  ArticleDataService.getUsername().then(response => {
    this.currentUsername = response.data
  }),
  ArticleDataService.getUser().then(response => {
    this.currentUserId = response.data
  })
},
computed: {
      listFriendsStatuses(){
        if (!this.currentUsername) {
      return []
      }
        return this.friendsStatuses.filter(status => status.username !== this.currentUsername)
      },
      checkIfLiked(){
      return id => this.likedStatuses.some(like => like.status_id === id);
      },
      checkIfDisLiked(){
      return id => this.dislikedStatuses.some(dislike => dislike.status_id === id);
      },
      mapLikeCount(){
    return this.likeCount.reduce((acc, curr) => {
      acc[curr.status_id] = curr.count;
      return acc;
    }, {});
  },
  mapDislikeCount(){
    return this.dislikeCount.reduce((acc, curr) => {
      acc[curr.status_id] = curr.count;
      return acc;
    }, {});
  },
},
methods: {
  likeAPost(){

    if(this.likedStatuses.some(status => status.status_id === this.status_id)){
          StatusService.unlikeAStatus(this.status_id).then(response=> {
            if(response.status === 200 ){
              this.likedStatuses = this.likedStatuses.filter(status => status.status_id !== this.status_id);
            }
          })
        } if(this.dislikedStatuses.some(status => status.status_id === this.status_id)){
      StatusService.undislikeAPost(this.status_id).then(response=> {
        if(response.status === 200 ){
          this.dislikedStatuses = this.dislikedStatuses.filter(status => status.status_id !== this.status_id);
        }
      })
      let like = {
      "user_id": this.currentUserId,
      "status_id": this.status_id,
      "liked": this.liked
    }
    StatusService.likeAStatus(like).then(response =>{
      if(response.status == 200){
        this.likedStatuses.push(like)
        console.log(JSON.stringify(this.likedStatuses))
      }
    })
    } else {

    let like = {
      "user_id": this.currentUserId,
      "status_id": this.status_id,
      "liked": this.liked
    }
    StatusService.likeAStatus(like).then(response =>{
      if(response.status == 200){
        this.likedStatuses.push(like)
        console.log(JSON.stringify(this.likedStatuses))
      }
    })
  }
  },
  dislikeAPost(){

    if(this.dislikedStatuses.some(status => status.status_id === this.status_id)){
      StatusService.undislikeAPost(this.status_id).then(response=> {
        if(response.status === 200 ){
          this.dislikedStatuses = this.dislikedStatuses.filter(status => status.status_id !== this.status_id);
        }
      })
    } if(this.likedStatuses.some(status => status.status_id === this.status_id)){
          StatusService.unlikeAStatus(this.status_id).then(response=> {
            if(response.status === 200 ){
              this.likedStatuses = this.likedStatuses.filter(status => status.status_id !== this.status_id);
            }
          })
          let dislike = {
            "user_id": this.currentUserId,
            "status_id": this.status_id,
            "liked": this.disliked
          }
          StatusService.dislikedAStatus(dislike).then(response =>{
          if(response.status == 200){
              this.dislikedStatuses.push(dislike)
          }
        })
        }
     else {

let dislike = {
  "user_id": this.currentUserId,
  "status_id": this.status_id,
  "liked": this.disliked
}
StatusService.dislikedAStatus(dislike).then(response =>{
  if(response.status == 200){
    this.dislikedStatuses.push(dislike)
  }
})
}
},
}
}
</script>


<style scoped>
i {
  cursor: pointer;
  margin-right: 3px;
}
    i:hover{
  color: #087bdf;
  cursor: pointer;
  margin-right: 1px;
}

.i-changed{
  color: #087bdf;
  cursor: pointer;
  margin-right: 1px;
}


    /* STATUSES */
    .status-sidebar{
        width: 30%;
        border: solid 1px #a4b0bb;
        margin-bottom: 20px
    }
    .status-container{
        margin: .5em;
        gap: 1em;
    }
    


    .status{
        background-color: #ffcccc;
        padding: 1em;
        animation: fadeInBottom 0.5s ease-in;
  }

  @keyframes fadeInBottom {
    from {
        transform: translateY(100%);
        opacity: 0;
    }
    to {
        transform: translateY(0);
        opacity: 1;
    }
  }

    .status:hover,
  .status.active{
    transition: border-bottom 0.3s ease, color 0.3s ease;
    transform: scale(1.01);
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    opacity: 1;
  }
    .username-date{
        display: flex;
        justify-content: space-around;
    }

    .status-text{
        font-size: 18px;
        margin-top: 0em;
    }

    .status-buttons{
  display: flex;
  justify-content: space-evenly;
  cursor: pointer;
}


</style>